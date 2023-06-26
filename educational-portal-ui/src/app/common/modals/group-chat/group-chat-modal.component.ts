import {Component, OnInit} from "@angular/core";
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {WebSocketService} from "../../../service/api/websocket.service";
import {GroupService} from "../../../service/api/group.service";
import {Group} from "../../../modules/Group";
import {TokenStorageService} from "../../../service/token-storage.service";
import {UserService} from "../../../service/api/user.service";
import {CreateGroupMessageRequest} from "../../../modules/CreateGroupMessageRequest";
import {GroupMessageResponse} from "../../../modules/GroupMessageResponse";
import {Subscription} from "rxjs";
import {
  MembersOfTheGroupForChatComponent
} from "../members-of-the-group-for-chat/members-of-the-group-for-chat.component";
import * as Stomp from "stompjs";

@Component({
  selector: 'app-group-chat-modal',
  templateUrl: './group-chat-modal.component.html',
  styleUrls: ['./group-chat-modal.component.scss']
})
export class GroupChatModalComponent implements OnInit {
  message: string = '';
  group: Group;
  groupMessages: GroupMessageResponse[];
  private groupMessageSubscription: Subscription;
  page = 0;
  pageSize = 10;
  private stompClient: Stomp.Client;

  currentUserId: number;

  constructor(
    private modal: NgbActiveModal,
    private webSocketService: WebSocketService,
    private groupService: GroupService,
    private userService: UserService,
    private tokenStorageService: TokenStorageService,
    private modalService: NgbModal
  ) {
  }

  ngOnInit(): void {
    this.stompClient = this.webSocketService.connectWebSocket();
    this.stompClient.connect({}, (frame) => {
      console.log('WebSocket connected');
      this.stompClient.subscribe('/topic/groupMessages/' + this.group.id, (sdkEvent) => {
        const groupMessageResponse = JSON.parse(sdkEvent.body) as GroupMessageResponse;
        this.groupMessages.push(groupMessageResponse);
      });
    });
    this.currentUserId = this.tokenStorageService.getUser()?.id as number;
    this.groupService
      .getMessagesByGroupId(this.group.id, this.page, this.pageSize)
      .subscribe(
        (groupMessages) => {
          this.groupMessages = groupMessages;
          this.page = Math.ceil(groupMessages.length / this.pageSize);
        },
        (error) => console.log(error)
      )

    this.groupMessageSubscription = this.webSocketService.groupMessage$.subscribe(
      (groupMessage) => {
        this.groupMessages.push(groupMessage);
      }
    );
  }

  closeModal() {
    this.modal.dismiss();
  }

  sendMessage() {
    const groupId = this.group.id;
    const groupMessage = new CreateGroupMessageRequest(groupId, this.currentUserId, this.message);
    this.stompClient.send("/app/sendMessage/" + groupId, {}, JSON.stringify(groupMessage));
    this.message = '';

  }

  isMessageBelongsToCurrentUser(userId: number) {
    return userId === this.currentUserId;
  }

  openListOfUsersModal(group: Group) {
    const modal = this.modalService.open(MembersOfTheGroupForChatComponent, {
      backdrop: 'static',
      size: 's'
    })
    modal.componentInstance.group = group;
  }

  loadMoreMessages() {
    this.page++;
    this.groupService
      .getMessagesByGroupId(this.group.id, this.page, this.pageSize)
      .subscribe(
        (groupMessages) => {
          this.groupMessages = [...groupMessages, ...this.groupMessages];
        },
        (error) => console.log(error)
      );
  }

  onScroll(event: Event) {
    const target = event.target as HTMLElement;
    const scrollTop = target.scrollTop;
    if (scrollTop === 0) {
      this.loadMoreMessages();
    }
  }
}

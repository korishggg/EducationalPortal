import {Component, OnInit} from "@angular/core";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {WebSocketService} from "../../../service/api/websocket.service";
import {GroupService} from "../../../service/api/group.service";
import {Group} from "../../../modules/Group";
import {TokenStorageService} from "../../../service/token-storage.service";
import {User} from "../../../modules/User";
import {UserService} from "../../../service/api/user.service";
import {CreateGroupMessageRequest} from "../../../modules/CreateGroupMessageRequest";
import {GroupMessageResponse} from "../../../modules/GroupMessageResponse";

@Component({
  selector: 'group-chat-modal',
  templateUrl: './group-chat-modal.component.html',
  styleUrls: ['./group-chat-modal.component.scss']
})
export class GroupChatModalComponent implements OnInit {
  message: string;
  group: Group;
  groupMessages: GroupMessageResponse[];

  currentUserId: number;

  constructor(
    private modal: NgbActiveModal,
    private webSocketService: WebSocketService,
    private groupService: GroupService,
    private userService: UserService,
    private tokenStorageService: TokenStorageService
  ) {
  }

  ngOnInit(): void {
    this.webSocketService.connectWebSocket();
    this.currentUserId = this.tokenStorageService.getUser()?.id as number;
    this.groupService.getMessagesByGroupId(this.group.id).subscribe(
      groupMessages => {
        this.groupMessages = groupMessages;
        console.log(groupMessages)
      }, error => {
        console.log(error);
      }
    );
  }

  closeModal() {
    this.modal.dismiss();
  }

  sendMessage() {
    const groupId = this.group.id;
    const groupMessage = new CreateGroupMessageRequest(groupId, this.currentUserId, this.message);
    this.webSocketService.sendMessageToGroup(
      groupMessage
    );
    this.message = '';

  }

  isMessageBelongsToCurrentUser(userId: number) {
    console.log(userId, this.currentUserId);

    return userId === this.currentUserId;
  }

}

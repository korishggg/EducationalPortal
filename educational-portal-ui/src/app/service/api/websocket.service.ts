import {Injectable} from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {CreateGroupMessageRequest} from "../../modules/CreateGroupMessageRequest";
import {Subject} from "rxjs";
import {GroupMessageResponse} from "../../modules/GroupMessageResponse";

@Injectable()
export class WebSocketService {
  private stompClient: Stomp.Client;
  message: CreateGroupMessageRequest;
  groupMessageSubject: Subject<GroupMessageResponse> = new Subject<GroupMessageResponse>();
  groupMessage$ = this.groupMessageSubject.asObservable();

  constructor() {
  }

  connectWebSocket() {
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);
    return this.stompClient;
  }

}

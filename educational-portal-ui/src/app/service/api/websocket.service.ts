import {Injectable} from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {TokenStorageService} from "../token-storage.service";
import {User} from "../../modules/User";
import {CreateGroupMessageRequest} from "../../modules/CreateGroupMessageRequest";

@Injectable()
export class WebSocketService {
  private stompClient: Stomp.Client;

  private user: User

  constructor(private tokenStorageService: TokenStorageService) {

  }


  connectWebSocket() {
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, () => {
      console.log('WebSocket connected');
    });
  }

  sendMessageToGroup(groupMessage: CreateGroupMessageRequest) {

    this.stompClient.send("/app/sendMessage", {},JSON.stringify(groupMessage));
  }

}

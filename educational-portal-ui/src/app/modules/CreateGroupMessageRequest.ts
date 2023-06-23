export class CreateGroupMessageRequest {
  groupId: number;
  userId: number;
  message: string;

  constructor(groupId: number, userId: number, message: string) {
    this.groupId = groupId;
    this.userId = userId;
    this.message = message;
  }

}

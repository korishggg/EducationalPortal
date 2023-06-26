export class GroupMessageResponse {
  content: string;
  groupId: number;
  userId: number;
  userFirstName: string;
  userLastName: string;
  timeStamp: string;

  constructor(content: string, groupId: number, userId: number, userFirstName: string, userLastName: string , timeStamp: string) {
    this.content = content;
    this.groupId = groupId;
    this.userId = userId;
    this.userFirstName = userFirstName;
    this.userLastName = userLastName;
    this.timeStamp = timeStamp;
  }
}

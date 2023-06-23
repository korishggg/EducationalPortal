export class GroupMessageResponse {
  content: string;
  groupId: number;
  userId: number;
  userFirstName: string;
  userLastName: string;


  constructor(content: string, groupId: number, userId: number, userFirstName: string, userLastName: string) {
    this.content = content;
    this.groupId = groupId;
    this.userId = userId;
    this.userFirstName = userFirstName;
    this.userLastName = userLastName;
  }
}

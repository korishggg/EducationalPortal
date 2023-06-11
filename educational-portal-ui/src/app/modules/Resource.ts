import {User} from "./User";

export class Resource {
  id: number;
  fileName: string;
  type: string;

  user: User;

  constructor(id: number, fileName: string, type: string, user: User) {
    this.id = id;
    this.fileName = fileName;
    this.type = type;
    this.user = user;
  }
}

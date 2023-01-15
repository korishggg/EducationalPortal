import {Category} from "./Category";
import {User} from "./User";

export class Group {
  id: number;
  name: string;
  category: Category;
  users: User[];
  instructor: User;

  constructor(id: number, name: string, category: Category, users: User[], instructor: User) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.users = users;
    this.instructor = instructor;
  }
}

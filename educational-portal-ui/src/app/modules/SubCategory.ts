import {Category} from "./Category";

export class SubCategory {
  id: number;
  name: string;
  parentName: string;

  constructor(id: number, name: string, parentName: string) {
    this.id = id;
    this.name = name;
    this.parentName = parentName;
  }
}

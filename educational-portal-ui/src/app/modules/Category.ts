export class Category {
  id: number;
  name: string;
  subcategories: Category [];
  constructor(id: number, name: string, subcategories: Category[]) {
    this.id = id;
    this.name = name;
    this.subcategories = subcategories;
  }

}

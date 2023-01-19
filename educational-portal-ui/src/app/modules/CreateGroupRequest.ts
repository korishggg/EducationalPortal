export class CreateGroupRequest {
  name: string;
  categoryId: number;
  instructorId: number;

  constructor(name: string, categoryId: number, instructorId: number) {
    this.name = name;
    this.categoryId = categoryId;
    this.instructorId = instructorId;
  }
}

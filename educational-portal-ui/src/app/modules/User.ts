import {Resource} from "./Resource";

export class User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  isApproved: boolean;
  resources: Resource[];

  constructor(id: number,
              firstName: string,
              lastName: string,
              email: string,
              phone: string,
              isApproved: boolean,
              resources: Resource[]) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.isApproved = isApproved;
    this.resources = resources;
  }
}

export class User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  isApproved: boolean;

  constructor(id: number,
              firstName: string,
              lastName: string,
              email: string,
              phone: string,
              isApproved: boolean,) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.isApproved = isApproved;
  }
}

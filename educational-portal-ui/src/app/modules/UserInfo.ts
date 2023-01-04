export class UserInfo {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  isApproved: boolean;
  role: string;
  iban: string;

  constructor(id: number,
              firstName: string,
              lastName: string,
              email: string,
              phone: string,
              isApproved: boolean,
              role: string,
              iban: string) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.isApproved = isApproved;
    this.role = role;
    this.iban = iban;
  }
}

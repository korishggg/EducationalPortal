export class RegistrationRequest {
  firstName: string;
  lastName: string;
  password: string;
  email: string;
  phone: string;

  constructor(firstName: string, lastName: string, password: string, email: string, phone: string) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.email = email;
    this.phone = phone;
  }
}

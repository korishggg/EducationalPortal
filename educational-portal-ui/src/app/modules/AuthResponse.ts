export class AuthResponse {
  token: string;
  refreshToken: string;

  constructor(token: string, refreshToken: string) {
    this.token = token;
    this.refreshToken = refreshToken;
  }
}

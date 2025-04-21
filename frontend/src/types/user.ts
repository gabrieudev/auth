export interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  isActive: boolean;
  createdAt: Date;
  updatedAt: Date;
}

export interface Signup {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

export interface ResetPassword {
  code: string | null;
  password: string;
}

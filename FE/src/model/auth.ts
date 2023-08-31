export interface Email {
  email: string;
  setEmail: React.Dispatch<React.SetStateAction<string>>;
  isEmailValid?: boolean;
}

export interface password {
  password: string;
  setPassword: React.Dispatch<React.SetStateAction<string>>;
  isPasswordValid?: boolean;
}

export interface passwordConfirm extends Pick<password, 'password'> {
  passwordConfirm: string;
  setPasswordConfirm: React.Dispatch<React.SetStateAction<string>>;
}

export interface Username {
  username: string;
  setUsername: React.Dispatch<React.SetStateAction<string>>;
  isUserNameValid?: boolean;
}

export interface Nickname {
  nickname: string;
  setNickname: React.Dispatch<React.SetStateAction<string>>;
  isNicknameValid?: boolean;
}

export interface Phone {
  phone: string;
  setPhone: React.Dispatch<React.SetStateAction<string>>;
  isPhoneValid?: boolean;
}

export interface Profile {
  userId: number;
  email: string;
  phone: string;
  nickname: string;
  profileImage: string | null;
  username: string;
}

export interface Address {
  zipCode: string;
  addressDetail: string;
  addressSimple: string;
}

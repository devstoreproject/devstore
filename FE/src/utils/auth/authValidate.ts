import {
  emailRegEx,
  nicknameRegEx,
  passwordRegEx,
  phoneRegEx,
} from './signUpRegEx';

export const ValidateEmail = (
  email: string,
  setFn: React.Dispatch<React.SetStateAction<boolean>>
) => {
  if (emailRegEx.test(email)) {
    setFn(true);
    return true;
  } else {
    setFn(false);
    return false;
  }
};

export const ValidatePassword = (
  password: string,
  setFn: React.Dispatch<React.SetStateAction<boolean>>
) => {
  if (passwordRegEx.test(password)) {
    setFn(true);
    return true;
  } else {
    setFn(false);
    return false;
  }
};

export const ValidatePhone = (
  phone: string,
  setFn: React.Dispatch<React.SetStateAction<boolean>>
) => {
  if (phoneRegEx.test(phone)) {
    setFn(true);
    return true;
  } else {
    setFn(false);
    return false;
  }
};

export const ValidateUserName = (
  userName: string,
  setFn: React.Dispatch<React.SetStateAction<boolean>>
) => {
  if (userName.length > 0) {
    setFn(true);
    return true;
  } else {
    setFn(false);
    return false;
  }
};

export const ValidateNickname = (
  nickname: string,
  setFn: React.Dispatch<React.SetStateAction<boolean>>
) => {
  if (nicknameRegEx.test(nickname)) {
    setFn(true);
    return true;
  } else {
    setFn(false);
    return false;
  }
};

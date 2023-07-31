import { emailRegEx, passwordRegEx, phoneRegEx } from './signUpRegEx';

export const validateEmail = (email) => {
  if (emailRegEx.test(email)) {
    return true;
  } else {
    return false;
  }
};

export const validatePassword = (password) => {
  if (passwordRegEx.test(password)) {
    return true;
  } else {
    return false;
  }
};

export const validatePhone = (phone) => {
  if (phoneRegEx.test(phone)) {
    return true;
  } else {
    return false;
  }
};

export const validateUserName = (userName) => {
  if (userName.length > 0) {
    return true;
  } else {
    return false;
  }
};

export const validateNickname = (nickname) => {
  if (nickname.length > 0) {
    return true;
  } else {
    return false;
  }
};

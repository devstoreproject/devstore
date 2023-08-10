export const emailRegEx =
  /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/;

export const passwordRegEx = /^(?=.*[a-zA-Z])(?=.*\d).{8,16}$/;

export const phoneRegEx = /^\d{11}$/;

export const nicknameRegEx = /^[a-zA-Z0-9가-힣]{1,8}$/;

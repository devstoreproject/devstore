import { useState } from 'react';

import EmailContainer from './EmailContainer';
import NicknameContainer from './NicknameContainer';
import PasswordCheckContainer from './PasswordCheckContainer';
import PasswordContainer from './PasswordContainer';
import PhoneContainer from './PhoneContainer';
import UserNameContainer from './UserNameContainer';
import { submitUserData } from 'utils/auth/submitUserData';

export default function SignUpForm() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [passwordCheck, setPasswordCheck] = useState('');
  const [userName, setUserName] = useState('');
  const [nickname, setNickname] = useState('');
  const [phone, setPhone] = useState('');

  const [isEmailValid, setIsEmailValid] = useState(false);
  const [isPasswordValid, setIsPasswordValid] = useState(false);
  const [isUserNameValid, setIsUserNameValid] = useState(false);
  const [isNicknameValid, setIsNicknameValid] = useState(false);
  const [isPhoneValid, setIsPhoneValid] = useState(false);

  const submitHandler = (e: React.FormEvent): void => {
    e.preventDefault();
    const userInfo = {
      email,
      password,
      userName,
      nickname,
      phone,
    };

    if (
      !isEmailValid ||
      !isPasswordValid ||
      password !== passwordCheck ||
      !isUserNameValid ||
      !isNicknameValid ||
      !isPhoneValid
    )
      return;

    submitUserData(userInfo);
  };
  return (
    <form
      className="relative flex flex-col p-5 mt-40 bg-white rounded-xl w-96 shadow-signBox h-200"
      onSubmit={submitHandler}
    >
      <span className="text-xl font-bold text-center">회원가입</span>
      <EmailContainer
        email={email}
        setEmail={setEmail}
        isEmailValid={isEmailValid}
        setIsEmailValid={setIsEmailValid}
      />
      <PasswordContainer
        password={password}
        setPassword={setPassword}
        isPasswordValid={isPasswordValid}
        setIsPasswordValid={setIsPasswordValid}
      />
      <PasswordCheckContainer
        password={password}
        passwordCheck={passwordCheck}
        setPasswordCheck={setPasswordCheck}
      />
      <UserNameContainer
        userName={userName}
        setUserName={setUserName}
        isUserNameValid={isUserNameValid}
        setIsUserNameValid={setIsUserNameValid}
      />
      <NicknameContainer
        nickname={nickname}
        setNickname={setNickname}
        isNicknameValid={isNicknameValid}
        setIsNicknameValid={setIsNicknameValid}
      />
      <PhoneContainer
        phone={phone}
        setPhone={setPhone}
        isPhoneValid={isPhoneValid}
        setIsPhoneValid={setIsPhoneValid}
      />
      <button
        className="mt-10 w-full text-white bg-black h-11 rounded-xl shadow-btn active:shadow-none active:ml-0.5 active:mt-4.5 duration-100"
        type="submit"
      >
        회원가입
      </button>
    </form>
  );
}

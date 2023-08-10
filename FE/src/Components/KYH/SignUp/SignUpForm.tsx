import { useState } from 'react';

import EmailContainer from './EmailContainer';
import NicknameContainer from './NicknameContainer';
import PasswordConfirmContainer from './PasswordConfirmContainer';
import PasswordContainer from './PasswordContainer';
import PhoneContainer from './PhoneContainer';
import UserNameContainer from './UserNameContainer';
import fetchSignUp from 'utils/auth/fetchSignUp';
import { useNavigate } from 'react-router-dom';
import {
  ValidateEmail,
  ValidateNickname,
  ValidatePassword,
  ValidatePhone,
  ValidateUserName,
} from 'utils/auth/authValidate';

export default function SignUpForm() {
  const navigate = useNavigate();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [userName, setUserName] = useState('');
  const [nickname, setNickname] = useState('');
  const [phone, setPhone] = useState('');

  const [isEmailValid, setIsEmailValid] = useState(true);
  const [isPasswordValid, setIsPasswordValid] = useState(true);
  const [isUserNameValid, setIsUserNameValid] = useState(true);
  const [isNicknameValid, setIsNicknameValid] = useState(true);
  const [isPhoneValid, setIsPhoneValid] = useState(true);

  const submitHandler = (e: React.FormEvent): void => {
    e.preventDefault();

    const userInfo = {
      email,
      password,
      userName,
      nickname,
      phone,
    };

    if (!ValidateEmail(email, setIsEmailValid)) return;
    if (!ValidatePassword(password, setIsPasswordValid)) return;
    if (password !== passwordConfirm) return;
    if (!ValidateUserName(userName, setIsUserNameValid)) return;
    if (!ValidateNickname(nickname, setIsNicknameValid)) return;
    if (!ValidatePhone(phone, setIsPhoneValid)) return;

    fetchSignUp(userInfo, navigate);
  };
  return (
    <form
      className="relative flex flex-col p-5 mt-40 bg-white rounded-xl w-96 shadow-signBox h-175"
      onSubmit={submitHandler}
    >
      <span className="mt-4 text-xl font-bold text-center">회원가입</span>
      <EmailContainer
        email={email}
        setEmail={setEmail}
        isEmailValid={isEmailValid}
      />
      <PasswordContainer
        password={password}
        setPassword={setPassword}
        isPasswordValid={isPasswordValid}
      />
      <PasswordConfirmContainer
        password={password}
        passwordConfirm={passwordConfirm}
        setPasswordConfirm={setPasswordConfirm}
      />
      <UserNameContainer
        userName={userName}
        setUserName={setUserName}
        isUserNameValid={isUserNameValid}
      />
      <NicknameContainer
        nickname={nickname}
        setNickname={setNickname}
        isNicknameValid={isNicknameValid}
      />
      <PhoneContainer
        phone={phone}
        setPhone={setPhone}
        isPhoneValid={isPhoneValid}
      />
      <button
        className="mt-10 w-full text-white bg-black h-11 rounded-xl shadow-btn active:shadow-none active:ml-0.5 active:mt-4.5 duration-100"
        type="submit"
      >
        회원가입
      </button>
      <button
        className="mt-4 w-full text-white bg-black h-11 rounded-xl shadow-btn active:shadow-none active:ml-0.5 active:mt-4.5 duration-100"
        type="button"
        onClick={() => {
          navigate('/signin');
        }}
      >
        취소
      </button>
    </form>
  );
}

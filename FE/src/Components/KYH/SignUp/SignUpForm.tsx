import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from 'api';

import EmailContainer from './EmailContainer';
import NicknameContainer from './NicknameContainer';
import PasswordCheckContainer from './PasswordCheckContainer';
import PasswordContainer from './PasswordContainer';
import PhoneNumberContainer from './PhoneNumberContainer';
import UserNameContainer from './UserNameContainer';

export default function SignUpForm() {
  const [email, setEmail] = useState('');
  const [isEmailCheck, setIsEmailCheck] = useState(false);
  const [password, setPassword] = useState('');
  const [isPasswordCheck, setIsPasswordCheck] = useState(false);
  const [passwordCheck, setPasswordCheck] = useState('');
  const [userName, setUserName] = useState('');
  const [nickname, setNickname] = useState('');
  const [phone, setPhone] = useState('');
  const [isPhoneCheck, setIsPhoneCheck] = useState(false);

  const navigate = useNavigate();

  const submitHandler = (e: React.FormEvent): void => {
    e.preventDefault();
    const userInfo = {
      email,
      password,
      userName,
      nickname,
      phone,
    };

    if (!isEmailCheck) return;
    if (password !== passwordCheck || !isPasswordCheck) return;
    if (userName.length === 0) return;
    if (!isPhoneCheck) return;

    const formData = new FormData();
    const blob = new Blob([JSON.stringify(userInfo)], {
      type: 'application/json',
    });

    formData.append('post', blob);

    api
      .post('/api/users', formData)
      .then(() => {
        navigate('/signin');
      })
      .catch((err) => {
        console.log(err);
      });
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
        isEmailCheck={isEmailCheck}
        setIsEmailCheck={setIsEmailCheck}
      />
      <PasswordContainer
        password={password}
        setPassword={setPassword}
        isPasswordCheck={isPasswordCheck}
        setIsPasswordCheck={setIsPasswordCheck}
      />
      <PasswordCheckContainer
        password={password}
        passwordCheck={passwordCheck}
        setPasswordCheck={setPasswordCheck}
      />
      <UserNameContainer userName={userName} setUserName={setUserName} />
      <NicknameContainer nickname={nickname} setNickname={setNickname} />
      <PhoneNumberContainer
        phone={phone}
        setPhone={setPhone}
        isPhoneCheck={isPhoneCheck}
        setIsPhoneCheck={setIsPhoneCheck}
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

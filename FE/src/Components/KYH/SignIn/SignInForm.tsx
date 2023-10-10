import { useNavigate } from 'react-router-dom';
import EmailAndPWContainer from './EmailAndPWContainer';
import KeepLoginContainer from './KeepLoginContainer';
import OneClickLogInContainer from './OneClickLogInContainer';
import { useState } from 'react';
import { ValidateEmail, ValidatePassword } from 'utils/auth/authValidate';
import fetchSignIn from 'utils/auth/fetchSignIn';
import SearchPasswordNavBtn from './SearchPasswordNavBtn';

export default function SignInForm() {
  const navigate = useNavigate();

  const [email, setEmail] = useState('admin002@gmail.com');
  const [password, setPassword] = useState('admin111!!');

  const [isEmailValid, setIsEmailValid] = useState(true);
  const [isPasswordValid, setIsPasswordValid] = useState(true);

  const [isSignInSuccess, setIsSignInSuccess] = useState(true);

  const navigateHandler = (e: React.MouseEvent) => {
    e.preventDefault();
    navigate('/signup');
  };

  const submitHandler = (e: React.FormEvent): void => {
    e.preventDefault();

    const userInfo = {
      username: email,
      password,
    };

    if (!ValidateEmail(email, setIsEmailValid)) return;
    if (!ValidatePassword(password, setIsPasswordValid)) return;

    fetchSignIn({ userInfo, navigate, setIsSignInSuccess });
  };

  return (
    <form
      className="flex flex-col items-center p-5 mt-40 bg-white rounded-xl w-80 h-136 shadow-signBox"
      onSubmit={submitHandler}
    >
      <span className="mt-4 text-2xl font-bold">DEV SHOP</span>
      <EmailAndPWContainer
        email={email}
        setEmail={setEmail}
        password={password}
        setPassword={setPassword}
        isEmailValid={isEmailValid}
        isPasswordValid={isPasswordValid}
      />
      <div className="flex justify-between mt-2">
        <KeepLoginContainer />
        <SearchPasswordNavBtn />
      </div>
      {isSignInSuccess ? null : (
        <span className="mt-4 text-sm font-bold text-center text-red-500">
          아이디 또는 비밀번호를 잘못 입력했습니다. <br />
          입력하신 내용을 확인해주세요
        </span>
      )}
      <button
        className="mt-8 w-full text-white bg-black h-11 rounded-xl shadow-btn active:shadow-none active:ml-0.5 active:mt-4.5 duration-100"
        type="submit"
      >
        로그인
      </button>
      <button
        className="mt-4 text-sm underline underline-offset-4 text-sky-600 hover:text-blue-800"
        onClick={navigateHandler}
      >
        아직 회원이 아니신가요?
      </button>
      <OneClickLogInContainer />
    </form>
  );
}

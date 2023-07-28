import { useNavigate } from 'react-router-dom';
import EmailAndPWContainer from './EmailAndPWContainer';
import KeepLoginContainer from './KeepLoginContainer';
import SearchPasswordContainer from './SearchPasswordContainer';
import OneClickLogInContainer from './OneClickLogInContainer';
import { useState } from 'react';
import api from 'api';

export default function SignInForm() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const navigateHandler = (e: React.MouseEvent) => {
    e.preventDefault();
    navigate('/signup');
  };

  const submitHandler = (e: React.FormEvent): void => {
    e.preventDefault();
    api
      .post('/api/login', {
        username: email,
        password,
      })
      .then((res) => {
        localStorage.setItem('authorization', res.headers.authorization);
        localStorage.setItem('refresh', res.headers.refresh);
        navigate('/');
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <form
      className="flex flex-col items-center p-5 mt-40 bg-white rounded-xl w-80 h-120 shadow-signBox"
      onSubmit={submitHandler}
    >
      <span className="mt-2 text-2xl font-bold">DEV SHOP</span>
      <EmailAndPWContainer
        email={email}
        setEmail={setEmail}
        password={password}
        setPassword={setPassword}
      />
      <div className="flex justify-between mt-2">
        <KeepLoginContainer />
        <SearchPasswordContainer />
      </div>
      <button
        className="mt-4 w-full text-white bg-black h-11 rounded-xl shadow-btn active:shadow-none active:ml-0.5 active:mt-4.5 duration-100"
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

import { useNavigate } from 'react-router-dom';
import EmailAndPWContainer from './EmailAndPWContainer';
import KeepLoginContainer from './KeepLoginContainer';
import SearchPasswordContainer from './SearchPasswordContainer';
import OneClickLogInContainer from './OneClickLogInContainer';

export default function SignInForm() {
  const navigate = useNavigate();

  const navigateHandler = (e: React.MouseEvent) => {
    e.preventDefault();
    navigate('/signup');
  };

  return (
    <form className="flex flex-col items-center p-5 mt-40 bg-white rounded-xl w-80 h-120 shadow-signBox">
      <span className="mt-2 text-2xl font-bold">DEV SHOP</span>
      <EmailAndPWContainer />
      <div className="flex justify-between mt-2">
        <KeepLoginContainer />
        <SearchPasswordContainer />
      </div>
      <button className="mt-4 w-full text-white bg-black h-11 rounded-xl shadow-btn active:shadow-none active:ml-0.5 active:mt-4.5 duration-100">
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

import { useNavigate } from 'react-router-dom';

import EmailContainer from './EmailContainer';
import NicknameContainer from './NicknameContainer';
import CheckPasswordContainer from './CheckPasswordContainer';
import PasswordContainer from './PasswordContainer';

export default function SignUpForm() {
  const navigate = useNavigate();
  const submitHandler = (e: React.MouseEvent) => {
    e.preventDefault();
    navigate('/signin');
  };

  return (
    <form className="flex flex-col p-5 mt-40 bg-white rounded-xl w-96 h-128 shadow-signBox">
      <span className="text-xl font-bold text-center">회원가입</span>
      <EmailContainer />
      <NicknameContainer />
      <PasswordContainer />
      <CheckPasswordContainer />
      <button
        className="mt-6 w-full text-white bg-black h-11 rounded-xl shadow-btn active:shadow-none active:ml-0.5 active:mt-4.5 duration-100"
        onClick={submitHandler}
      >
        회원가입
      </button>
    </form>
  );
}

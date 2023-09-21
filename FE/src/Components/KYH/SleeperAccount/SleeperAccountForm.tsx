import { useNavigate } from 'react-router-dom';
import EmailContainer from './EmailContainer';
import PasswordContainer from './PasswordContainer';
import { useState } from 'react';
import fetchSleeperAccount from 'utils/auth/fetchSleeperAccount';

export default function SleeperAccountForm() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const sleeperAccountBtnHandler = () => {
    const userInfo = {
      username: email,
      password,
    };

    fetchSleeperAccount(userInfo, navigate);
  };
  return (
    <form className="flex flex-col p-5 mt-40 bg-white rounded-xl w-96 h-128 shadow-signBox">
      <span className="mt-4 text-xl font-bold text-center">휴면 계정 안내</span>
      <p className="my-4 text-center">
        회원님이 1년 이상 로그인 하지 않아
        <br />
        계정을 안전하게 보호하기 위해
        <br />
        휴먼 상태로 전환 되었습니다.
      </p>
      <EmailContainer email={email} setEmail={setEmail} />
      <PasswordContainer password={password} setPassword={setPassword} />
      <p className="pb-5 mt-4 text-sm text-center text-blue-600 border-b border-black">
        서비스를 계속 이용 하시려면 <br />
        이메일과 비밀번호를 입력 후<br />[ 해제하기 ] 버튼을 눌러주세요
      </p>
      <div className="flex justify-around w-full mt-4">
        <input
          type="button"
          value="취소"
          className="mt-6 w-2/5 text-black border bg-white h-11 rounded-xl shadow-btn active:shadow-none active:ml-0.5 active:mt-6.5 duration-100"
          onClick={() => {
            navigate('/signin');
          }}
        />
        <input
          type="button"
          value="해제하기"
          className="mt-6 w-2/5 text-black bg-neon-green h-11 rounded-xl shadow-btn active:shadow-none active:ml-0.5 active:mt-6.5 duration-100"
          onClick={sleeperAccountBtnHandler}
        />
      </div>
    </form>
  );
}

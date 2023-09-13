import { useState } from 'react';
import EmailContainer from './EmailContainer';
import PhoneContainer from './PhoneContainer';
import fetchSearchPassword from 'utils/auth/fetchSearchPassword';
import NameContainer from './NameContainer';
import { useNavigate } from 'react-router-dom';

export default function SearchPasswordForm() {
  const navigate = useNavigate();

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [temporaryPassword, setTemporaryPassword] = useState('');

  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [phone, setPhone] = useState('');

  const submitHandler = (e: React.FormEvent) => {
    e.preventDefault();

    const userInfo = {
      email,
      name: username,
      phone,
    };

    fetchSearchPassword(userInfo, setIsModalOpen, setTemporaryPassword);
  };

  return (
    <form
      className="relative flex flex-col p-5 mt-40 bg-white rounded-xl w-80 h-120 shadow-signBox"
      onSubmit={submitHandler}
    >
      {isModalOpen ? (
        <>
          <div className="absolute top-0 left-0 bg-black opacity-50 w-80 h-120 rounded-xl" />
          <div className="absolute left-0 z-10 flex flex-col items-center h-64 bg-gray-200 rounded-lg w-80 top-24">
            <span className="mt-6">회원님의 임시 비밀번호는</span>
            <span className="mt-2 text-xl font-bold">{temporaryPassword}</span>
            <span className="mt-2">입니다.</span>
            <span className="mt-4 text-sm leading-relaxed text-center text-red-500">
              저장하신 후 로그인 시 사용하시기 바라며, <br />
              로그인 후 비밀번호를 변경해주시기 바랍니다.
            </span>
            <input
              type="button"
              value="로그인하기"
              className="px-4 py-1 mt-6 text-white bg-gray-600 rounded-lg cursor-pointer"
              onClick={() => {
                navigate('/signin');
              }}
            />
          </div>
        </>
      ) : null}
      <span className="mt-4 mb-16 text-2xl text-center">
        비밀번호 재설정하기
      </span>
      <EmailContainer email={email} setEmail={setEmail} />
      <NameContainer username={username} setUsername={setUsername} />
      <PhoneContainer phone={phone} setPhone={setPhone} />
      <button
        className="mt-20 w-full text-white bg-black h-11 rounded-xl shadow-btn active:shadow-none active:ml-0.5 active:mt-4.5 duration-100"
        type="submit"
      >
        재설정
      </button>
    </form>
  );
}

import { useState } from 'react';
// import { useNavigate } from 'react-router-dom';
// import { ValidateEmail } from 'utils/auth/authValidate';

export default function SearchPasswordForm() {
  // const navigate = useNavigate();

  const [email, setEmail] = useState('');

  // const [isEmailValid, setIsEmailValid] = useState(true);
  // const [isPasswordValid, setIsPasswordValid] = useState(true);

  const submitHandler = (e: React.FormEvent) => {
    e.preventDefault();

    // const userInfo = {
    //   email,
    //   name,
    // };

    // if (!ValidateEmail(email, setIsEmailValid)) return;

    return null;
  };

  return (
    <form
      className="flex flex-col items-center p-5 mt-40 bg-white rounded-xl w-80 h-136 shadow-signBox"
      onSubmit={submitHandler}
    >
      <span className="mt-4 text-2xl">비밀번호 재설정하기</span>
      <input
        type="text"
        className="w-full h-10 border border-black"
        onChange={(e) => {
          setEmail(e.target.value);
        }}
        value={email}
      />
      <button
        className="mt-8 w-full text-white bg-black h-11 rounded-xl shadow-btn active:shadow-none active:ml-0.5 active:mt-4.5 duration-100"
        type="submit"
      >
        로그인
      </button>
    </form>
  );
}

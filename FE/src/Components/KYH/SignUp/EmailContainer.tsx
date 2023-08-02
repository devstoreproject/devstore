import type { Email } from 'model/auth';

export default function EmailContainer({
  email,
  setEmail,
  isEmailValid,
}: Email) {
  const EmailInputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };

  return (
    <div className="flex mt-2">
      <div className="flex flex-col w-4/6">
        <span className="ml-2">이메일</span>
        <input
          type="text"
          className="h-10 pl-2 border border-black"
          placeholder="이메일을 입력하세요"
          onChange={EmailInputHandler}
          value={email}
        ></input>
        {isEmailValid ? null : (
          <span className="mt-1 ml-2 text-xs font-bold text-red-500">
            올바른 형식의 이메일을 입력해주세요
          </span>
        )}
      </div>
      <button className="h-10 px-3 mt-6 ml-3 text-sm bg-gray-300 rounded-xl shadow-btn active:shadow-none active:ml-3.5 active:mt-6.5 duration-100">
        인증코드전송
      </button>
    </div>
  );
}

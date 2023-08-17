import type { Email } from 'model/auth';

export default function EmailContainer({
  email,
  setEmail,
  isEmailValid,
}: Email) {
  return (
    <div className="flex mt-2">
      <div className="flex flex-col w-full mr-2">
        <span className="ml-2">이메일</span>
        <input
          type="text"
          className="h-10 pl-2 border border-black"
          placeholder="이메일을 입력하세요"
          onChange={(e) => {
            setEmail(e.target.value);
          }}
          value={email}
        ></input>
        {isEmailValid ? null : (
          <span className="mt-1 ml-2 text-xs font-bold text-red-500">
            올바른 형식의 이메일을 입력해주세요
          </span>
        )}
      </div>
    </div>
  );
}

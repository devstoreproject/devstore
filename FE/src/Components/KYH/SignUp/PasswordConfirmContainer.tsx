import { type passwordConfirm } from 'model/auth';

export default function PasswordConfirmContainer({
  password,
  passwordConfirm,
  setPasswordConfirm,
}: passwordConfirm) {
  return (
    <div className="flex mt-2">
      <div className="flex flex-col w-full mr-2">
        <span className="ml-2">비밀번호 확인</span>
        <input
          type="password"
          className="h-10 pl-2 border border-black"
          placeholder="비밀번호를 다시 입력하세요"
          value={passwordConfirm}
          onChange={(e) => {
            setPasswordConfirm(e.target.value);
          }}
        ></input>
        {password === passwordConfirm ? null : (
          <span className="mt-1 ml-2 text-xs font-bold text-red-500">
            비밀번호가 일치하는지 확인해주세요
          </span>
        )}
      </div>
    </div>
  );
}

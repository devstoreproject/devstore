import type { passwordConfirm } from 'model/auth';

export default function PasswordConfirmContainer({
  password,
  passwordConfirm,
  setPasswordConfirm,
}: passwordConfirm) {
  return (
    <div className="flex items-center mb-4">
      <span className="w-32 text-gray-500">비밀번호 확인</span>
      <input
        type="password"
        className="h-10 pl-4 text-sm border border-gray-300 w-96 rounded-3xl"
        value={passwordConfirm}
        onChange={(e) => {
          setPasswordConfirm(e.target.value);
        }}
      />
      {password === passwordConfirm ? null : (
        <span className="mt-1 ml-2 text-xs font-bold text-red-500">
          비밀번호가 일치하는지 확인해주세요
        </span>
      )}
    </div>
  );
}

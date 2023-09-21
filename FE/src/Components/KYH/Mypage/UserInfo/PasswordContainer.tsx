import type { password } from 'model/auth';

export default function PasswordContainer({
  password,
  setPassword,
  isPasswordValid,
}: password) {
  return (
    <div className="flex items-center mb-4">
      <span className="w-32 text-gray-500">비밀번호 변경</span>
      <input
        type="password"
        className="h-10 pl-4 text-sm border border-gray-300 w-96 rounded-3xl"
        value={password}
        onChange={(e) => {
          setPassword(e.target.value);
        }}
      />
      {isPasswordValid !== undefined && isPasswordValid ? null : (
        <span className="mt-1 ml-2 text-xs font-bold text-red-500">
          영어, 숫자가 포함된 8~16글자이어야 합니다
        </span>
      )}
    </div>
  );
}

import type { Email, password } from 'model/auth';

type emailAndPassword = Email & password;

export default function EmailAndPWContainer({
  email,
  setEmail,
  password,
  setPassword,
  isEmailValid,
  isPasswordValid,
}: emailAndPassword) {
  const EmailInputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };

  const PasswordInputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  return (
    <div className="mt-4">
      <input
        type="text"
        className="w-full h-10 border border-black"
        onChange={EmailInputHandler}
        value={email}
      />
      {isEmailValid ? null : (
        <span className="mt-1 ml-2 text-xs font-bold text-red-500">
          올바른 형식의 이메일을 입력해주세요
        </span>
      )}
      <input
        type="password"
        className="w-full h-10 mt-2 border border-black"
        onChange={PasswordInputHandler}
        value={password}
      />
      {isPasswordValid ? null : (
        <span className="mt-1 ml-2 text-xs font-bold text-red-500">
          영어, 숫자가 포함된 8~16글자이어야 합니다
        </span>
      )}
    </div>
  );
}

import { validatePassword } from 'utils/authValidate';

interface passwordContainerProps {
  password: string;
  setPassword: React.Dispatch<React.SetStateAction<string>>;
  isPasswordCheck: boolean;
  setIsPasswordCheck: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function PasswordContainer({
  password,
  setPassword,
  isPasswordCheck,
  setIsPasswordCheck,
}: passwordContainerProps) {
  const passwordInputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
    setIsPasswordCheck(validatePassword(e.target.value));
  };

  return (
    <div className="flex mt-2">
      <div className="flex flex-col w-full mr-2">
        <span className="ml-2">비밀번호</span>
        <input
          type="password"
          className="h-10 pl-2 border border-black"
          placeholder="비밀번호를 입력하세요"
          value={password}
          onChange={passwordInputHandler}
        ></input>
        {isPasswordCheck ? null : (
          <span className="mt-1 ml-2 text-xs font-bold text-red-500">
            영어, 숫자가 포함된 8~16글자이어야 합니다
          </span>
        )}
      </div>
    </div>
  );
}

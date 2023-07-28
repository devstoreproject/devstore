interface EmailAndPWContainerProps {
  email: string;
  password: string;
  setEmail: React.Dispatch<React.SetStateAction<string>>;
  setPassword: React.Dispatch<React.SetStateAction<string>>;
}

export default function EmailAndPWContainer({
  email,
  setEmail,
  password,
  setPassword,
}: EmailAndPWContainerProps) {
  return (
    <div className="mt-4">
      <input
        type="email"
        className="w-full h-10 border border-black"
        onChange={(e) => {
          setEmail(e.target.value);
        }}
        value={email}
      />
      <span className="mt-1 ml-2 text-xs font-bold text-red-500">
        올바른 형식의 이메일을 입력해주세요
      </span>
      <input
        type="password"
        className="w-full h-10 mt-2 border border-black"
        onChange={(e) => {
          setPassword(e.target.value);
        }}
        value={password}
      />
      <span className="mt-1 ml-2 text-xs font-bold text-red-500">
        비밀번호가 일치하지 않습니다
      </span>
    </div>
  );
}

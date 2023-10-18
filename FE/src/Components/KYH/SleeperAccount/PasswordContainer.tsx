import type { password } from 'model/auth';

export default function PasswordContainer({ password, setPassword }: password) {
  return (
    <div className="flex flex-col">
      <span className="ml-8">비밀번호</span>
      <input
        type="password"
        className="h-8 mx-6 border border-black"
        value={password}
        onChange={(e) => {
          setPassword(e.target.value);
        }}
      />
    </div>
  );
}

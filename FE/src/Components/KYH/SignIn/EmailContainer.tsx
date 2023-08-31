import type { Email } from 'model/auth';

export default function EmailContainer({ email, setEmail }: Email) {
  return (
    <div className="flex flex-col mb-4">
      <span className="ml-6">이메일</span>
      <input
        type="text"
        className="h-8 pl-2 mx-4 border border-black"
        placeholder="이메일을 입력해 주세요"
        onChange={(e) => {
          setEmail(e.target.value);
        }}
        value={email}
      />
    </div>
  );
}

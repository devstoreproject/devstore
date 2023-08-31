import type { Email } from 'model/auth';

export default function EmailContainer({ email, setEmail }: Email) {
  return (
    <div className="flex flex-col">
      <span className="ml-8">이메일</span>
      <input
        type="text"
        className="h-8 mx-6 mb-4 border border-black"
        value={email}
        onChange={(e) => {
          setEmail(e.target.value);
        }}
      />
    </div>
  );
}

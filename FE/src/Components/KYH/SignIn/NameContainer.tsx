import type { Username } from 'model/auth';

export default function NameContainer({ username, setUsername }: Username) {
  return (
    <div className="flex flex-col mb-4">
      <span className="ml-6">이름</span>
      <input
        type="text"
        className="h-8 pl-2 mx-4 border border-black"
        placeholder="이름을 입력해 주세요"
        onChange={(e) => {
          setUsername(e.target.value);
        }}
        value={username}
      />
    </div>
  );
}

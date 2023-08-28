import type { Username } from 'model/auth';

export default function NameContainer({
  username,
  setUsername,
  isUserNameValid,
}: Username) {
  return (
    <div className="flex items-center mb-4">
      <span className="w-32 text-gray-500">이름</span>
      <input
        type="text"
        className="h-10 pl-4 text-sm border border-gray-300 w-96 rounded-3xl"
        value={username}
        onChange={(e) => {
          setUsername(e.target.value);
        }}
      />
      {isUserNameValid ? null : (
        <span className="mt-1 ml-2 text-xs font-bold text-red-500">
          이름을 입력해 주세요
        </span>
      )}
    </div>
  );
}

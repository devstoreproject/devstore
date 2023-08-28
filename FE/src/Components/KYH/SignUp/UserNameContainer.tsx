import type { Username } from 'model/auth';

export default function UserNameContainer({
  username,
  setUsername,
  isUserNameValid,
}: Username) {
  const UserNameInputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value);
  };
  return (
    <div className="flex mt-2">
      <div className="flex flex-col w-full mr-2">
        <span className="ml-2">이름</span>
        <input
          type="text"
          className="h-10 pl-2 border border-black"
          placeholder="이름을 입력해 주세요"
          value={username}
          onChange={UserNameInputHandler}
        ></input>
        {isUserNameValid ? null : (
          <span className="mt-1 ml-2 text-xs font-bold text-red-500">
            이름을 입력해 주세요
          </span>
        )}
      </div>
    </div>
  );
}

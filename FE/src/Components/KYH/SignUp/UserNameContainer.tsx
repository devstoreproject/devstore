interface UserNameContainerProps {
  userName: string;
  setUserName: React.Dispatch<React.SetStateAction<string>>;
}

export default function UserNameContainer({
  userName,
  setUserName,
}: UserNameContainerProps) {
  return (
    <div className="flex mt-2">
      <div className="flex flex-col w-full mr-2">
        <span className="ml-2">이름</span>
        <input
          type="text"
          className="h-10 pl-2 mr-2 border border-black"
          placeholder="이름을 입력해 주세요"
          value={userName}
          onChange={(e) => {
            setUserName(e.target.value);
          }}
        ></input>
        {userName.length > 0 ? null : (
          <span className="mt-1 ml-2 text-xs font-bold text-red-500">
            이름을 입력해 주세요
          </span>
        )}
      </div>
    </div>
  );
}

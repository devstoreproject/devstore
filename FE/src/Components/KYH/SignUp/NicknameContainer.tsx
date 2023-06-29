export default function NicknameContainer() {
  return (
    <div className="flex mt-2">
      <div className="flex flex-col w-4/6">
        <span className="ml-2">닉네임</span>
        <input
          type="text"
          className="h-10 pl-2 border border-black"
          placeholder="닉네임을 입력하세요"
        ></input>
        <span className="mt-1 ml-2 text-xs font-bold text-red-500">
          중복된 닉네임 입니다.
        </span>
      </div>
      <button className="h-10 px-6 mt-6 ml-3 text-sm bg-gray-300 rounded-xl shadow-btn active:shadow-none active:ml-3.5 active:mt-6.5 duration-100">
        중복확인
      </button>
    </div>
  );
}

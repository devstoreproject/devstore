export default function CheckPasswordContainer() {
  return (
    <div className="flex mt-2">
      <div className="flex flex-col w-full mr-2">
        <span className="ml-2">비밀번호 확인</span>
        <input
          type="text"
          className="h-10 pl-2 border border-black"
          placeholder="비밀번호를 다시 입력하세요"
        ></input>
        <span className="mt-1 ml-2 text-xs font-bold text-red-500">
          비밀번호가 틀립니다
        </span>
      </div>
    </div>
  );
}

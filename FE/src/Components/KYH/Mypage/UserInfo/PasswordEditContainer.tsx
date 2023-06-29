export default function PasswordEditContainer() {
  return (
    <>
      <div className="flex items-center mb-4">
        <span className="w-32 text-gray-500">비밀번호 변경</span>
        <input
          type="password"
          className="h-10 pl-4 text-sm border border-gray-300 w-96 rounded-3xl"
        />
      </div>
      <div className="flex items-center mb-4">
        <span className="w-32 text-gray-500">비밀번호 확인</span>
        <input
          type="password"
          className="h-10 pl-4 text-sm border border-gray-300 w-96 rounded-3xl"
        />
      </div>
    </>
  );
}

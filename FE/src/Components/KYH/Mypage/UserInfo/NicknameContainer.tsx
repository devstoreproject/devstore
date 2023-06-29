export default function NicknameContainer() {
  return (
    <div className="flex items-center mb-4">
      <span className="w-32 text-gray-500">닉네임</span>
      <input
        type="text"
        className="h-10 pl-4 text-sm border border-gray-300 w-96 rounded-3xl"
      />
    </div>
  );
}

export default function EmailContainer() {
  return (
    <div className="flex items-center mb-4">
      <span className="w-32 text-gray-500">이메일</span>
      <input
        type="email"
        className="h-10 pl-4 text-sm border border-gray-300 w-96 rounded-3xl"
      />
    </div>
  );
}

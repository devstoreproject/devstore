export default function LandlinePhoneNumberContainer() {
  return (
    <div className="flex items-center mb-4">
      <span className="w-32 text-gray-500">일반전화</span>
      <input className="w-24 h-10 text-sm text-center border border-gray-300 rounded-3xl" />
      <span className="mx-3">-</span>
      <input
        type="text"
        className="w-64 h-10 pl-4 text-sm border border-gray-300 rounded-3xl"
      />
    </div>
  );
}

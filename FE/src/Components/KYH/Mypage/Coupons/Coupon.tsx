export default function Coupon() {
  return (
    <li className="flex flex-col pt-2 pl-2 mb-4 mr-4 border border-gray-300 rounded-lg h-28 w-44">
      <span className="text-xs text-gray-500">신규가입 혜택</span>
      <span className="text-xs text-gray-500">10,000원 이상 구매시</span>
      <span className="mt-2 text-sm">10% 할인</span>
      <span className="text-xs text-gray-500">(최대 10,000원)</span>
      <span className="mt-2 mr-2 text-xs text-right text-gray-500">
        ~2023.07.28
      </span>
    </li>
  );
}

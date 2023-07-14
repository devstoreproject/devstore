import { FiHeart } from 'react-icons/fi';

export default function Button() {
  return (
    <div className="flex pt-5 items-center justify-end">
      <FiHeart size={24} />
      <button className="border-box outline rounded-full py-3 w-48 mr-4 ml-4">
        장바구니 추가
      </button>
      <button className="border-box rounded-full bg-slate-800 text-slate-200 py-3 w-48">
        구매하기
      </button>
    </div>
  );
}

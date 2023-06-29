import { AiOutlineHeart } from 'react-icons/ai';

export default function BookmarkItem() {
  return (
    <li className="relative mb-4 mr-4 w-60">
      <img className="w-full h-40 mb-2 bg-gray-300 rounded-lg" />
      <p className="w-56 text-sm truncate">
        알파스캔 AOC Q32V3S QHD IPS 75 시력보호보호보호보호
      </p>
      <span className="text-sm text-gray-500">199,000원</span>
      <div className="absolute text-lg bottom-16 right-3">
        <AiOutlineHeart />
      </div>
    </li>
  );
}

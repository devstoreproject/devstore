import { IoHomeSharp } from 'react-icons/io5';

export default function HomeNavigate() {
  return (
    <div className="flex items-center h-20 pl-4 mt-6 mb-4 border border-gray-400 rounded-md">
      <span className="mr-4 text-2xl">
        <IoHomeSharp />
      </span>
      <span className="text-xl font-bold">HOME</span>
    </div>
  );
}

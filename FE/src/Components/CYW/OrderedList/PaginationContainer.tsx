import {
  MdKeyboardDoubleArrowLeft,
  MdKeyboardArrowLeft,
  MdKeyboardArrowRight,
  MdKeyboardDoubleArrowRight,
} from 'react-icons/md';

export default function PaginationContainer() {
  return (
    <div className="flex items-center justify-center w-300">
      <button className="text-lg text-gray-500 bg-white border border-gray-300 rounded-l-md">
        <MdKeyboardDoubleArrowLeft />
      </button>
      <button className="mr-4 text-lg text-gray-500 bg-white border-r rounded-r-lg border-y border-y-gray-300 border-r-gray-300">
        <MdKeyboardArrowLeft />
      </button>
      <span className="mr-4 font-bold">1</span>
      <span className="mr-4">2</span>
      <span className="mr-4">3</span>
      <span className="mr-4">4</span>
      <button className="text-lg text-gray-500 bg-white border border-gray-300 rounded-l-md">
        <MdKeyboardArrowRight />
      </button>
      <button className="text-lg text-gray-500 bg-white border-r rounded-r-lg border-y border-y-gray-300 border-r-gray-300">
        <MdKeyboardDoubleArrowRight />
      </button>
    </div>
  );
}

import { useState } from 'react';
import {
  MdKeyboardDoubleArrowLeft,
  MdKeyboardArrowLeft,
  MdKeyboardArrowRight,
  MdKeyboardDoubleArrowRight,
} from 'react-icons/md';

interface OwnProps {
  page: number;
  totalPage: number;
  setPage: React.Dispatch<React.SetStateAction<number>>;
}

export default function PaginationContainer({
  page,
  setPage,
  totalPage,
}: OwnProps) {
  const [maxPage, setMaxPage] = useState(0);

  const doubleArrowLeftBtnHandler = () => {
    if (maxPage === 0) return;
    setMaxPage((prev) => prev - 5);
    setPage((prev) => prev - 5);
  };

  const doubleArrowRightBtnHandler = () => {
    if (totalPage >= page + 1) return;
    setMaxPage((prev) => prev + 5);
    setPage((prev) => prev + 5);
  };

  const arrowLeftBtnHandler = () => {
    if (page === 0) return;
    if (page % 5 === 0) setMaxPage((prev) => prev - 5);
    setPage((prev) => prev - 1);
  };

  const arrowRightBtnHandler = () => {
    if (totalPage === page + 1) return;
    if (page % 5 === 4) setMaxPage((prev) => prev + 5);
    setPage((prev) => prev + 1);
  };

  return (
    <div className="flex items-center justify-center mt-5 mb-5">
      <button
        className="text-lg text-gray-500 bg-white border border-gray-300 rounded-l-md"
        onClick={doubleArrowLeftBtnHandler}
      >
        <MdKeyboardDoubleArrowLeft />
      </button>
      <button
        className="mr-4 text-lg text-gray-500 bg-white border-r rounded-r-lg border-y border-y-gray-300 border-r-gray-300"
        onClick={arrowLeftBtnHandler}
      >
        <MdKeyboardArrowLeft />
      </button>
      {Array(totalPage)
        .fill('')
        .map((btn, idx) => (
          <button
            className={`w-5 mr-4 ${page === idx + maxPage ? 'font-bold' : ''}`}
            key={idx}
            onClick={() => {
              setPage(idx + maxPage);
            }}
          >
            {idx + maxPage + 1}
          </button>
        ))}
      <button
        className="text-lg text-gray-500 bg-white border border-gray-300 rounded-l-md"
        onClick={arrowRightBtnHandler}
      >
        <MdKeyboardArrowRight />
      </button>
      <button
        className="text-lg text-gray-500 bg-white border-r rounded-r-lg border-y border-y-gray-300 border-r-gray-300"
        onClick={doubleArrowRightBtnHandler}
      >
        <MdKeyboardDoubleArrowRight />
      </button>
    </div>
  );
}

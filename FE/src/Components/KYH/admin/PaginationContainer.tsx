import { useState } from 'react';
import {
  MdKeyboardDoubleArrowLeft,
  MdKeyboardArrowLeft,
  MdKeyboardArrowRight,
  MdKeyboardDoubleArrowRight,
} from 'react-icons/md';

interface OwnProps {
  page: number;
  setPage: React.Dispatch<React.SetStateAction<number>>;
  totalPages: number;
}

export default function PaginationContainer({
  page,
  setPage,
  totalPages,
}: OwnProps) {
  const [maxPages, setMaxPages] = useState(0);

  const doubleArrowLeftBtnHandler = () => {
    if (page < 5) return;
    setMaxPages((prev) => prev - 5);
    setPage(maxPages - 1);
  };

  const doubleArrowRightBtnHandler = () => {
    if (page + 1 >= totalPages) return;
    setMaxPages((prev) => prev + 5);
    setPage(maxPages + 5);
  };

  const arrowLeftBtnHandler = () => {
    if (page === 0) return;
    if (page % 5 === 0) setMaxPages((prev) => prev - 5);
    setPage((prev) => prev - 1);
  };

  const arrowRightBtnHandler = () => {
    if (page + 1 === totalPages) return;
    if (page % 5 === 4) setMaxPages((prev) => prev + 5);
    setPage((prev) => prev + 1);
  };

  if (totalPages === 0) return null;

  return (
    <div className="flex items-center justify-center w-300">
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
      {Array(5)
        .fill('')
        .map((btn, idx) => (
          <button
            className={`w-5 mr-4 ${
              page === idx + maxPages ? 'font-bold' : ''
            } ${idx + 1 + maxPages > totalPages ? 'text-gray-300' : ''}`}
            key={idx}
            disabled={idx + 1 + maxPages > totalPages}
            onClick={() => {
              setPage(idx);
            }}
          >
            {idx + 1 + maxPages}
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

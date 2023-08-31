import type { Inquiry } from 'model/inquiry';
import { useEffect, useState } from 'react';

interface OwnProps {
  setInquirys: React.Dispatch<React.SetStateAction<Inquiry[]>>;
  defaultInquiry: Inquiry[];
}

export default function SearchTableContents({
  setInquirys,
  defaultInquiry,
}: OwnProps) {
  const [checked, setChecked] = useState([true, true]);

  useEffect(() => {
    if (checked[0] && checked[1]) {
      setInquirys(defaultInquiry);
      return;
    }
    if (checked[0] && !checked[1]) {
      setInquirys(defaultInquiry.filter((inquiry) => inquiry.answer === null));
      return;
    }
    if (!checked[0] && checked[1]) {
      setInquirys(defaultInquiry.filter((inquiry) => inquiry.answer !== null));
      return;
    }
    if (!checked[0] && !checked[1]) {
      setInquirys([]);
    }
  }, [checked, setInquirys]);
  return (
    <div className="flex items-center border-b-gray-400">
      <label className="flex items-center">
        <input
          type="checkbox"
          className="w-6 h-6 ml-6"
          checked={checked[0]}
          onChange={() => {
            setChecked((prev) => [!prev[0], prev[1]]);
          }}
        />
        <span className="ml-2 text-lg text-gray-500">답변대기중</span>
      </label>
      <label className="flex items-center">
        <input
          type="checkbox"
          className="w-6 h-6 ml-6"
          checked={checked[1]}
          onChange={() => {
            setChecked((prev) => [prev[0], !prev[1]]);
          }}
        />
        <span className="ml-2 text-lg text-gray-500">답변완료</span>
      </label>
    </div>
  );
}

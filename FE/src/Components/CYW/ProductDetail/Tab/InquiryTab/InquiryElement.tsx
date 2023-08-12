import { useState } from 'react';
import type { InquiryContentType } from '../Tab';
import { TiArrowSortedDown, TiArrowSortedUp } from 'react-icons/ti';
import { BsFillXCircleFill } from 'react-icons/bs';

export interface OwnProps {
  inquiry: InquiryContentType[] | null;
}

export default function InquiryElement({ inquiry }: OwnProps) {
  const [selected, setSelected] = useState<number | null>(null);

  const toggle = (i: number) => {
    if (selected === i) {
      setSelected(null);
    } else {
      setSelected(i);
    }
  };

  return (
    <div>
      {inquiry?.map((inquiryItem, i) => (
        <div key={inquiryItem.questionId} className="flex flex-col border-box">
          <div
            className="flex pl-8 border-b-2 py-4"
            onClick={() => {
              toggle(i);
            }}
          >
            <p>Q. {inquiryItem.comment}</p>
            {inquiryItem.qnaStatus === 'ANSWER_COMPLETE' ? (
              <div className="flex items-center pl-2">
                {selected === i ? (
                  <TiArrowSortedUp size={20} />
                ) : (
                  <TiArrowSortedDown size={20} />
                )}
              </div>
            ) : inquiryItem.qnaStatus === 'REGISTER' ? (
              <div className="flex items-center pl-2">
                <BsFillXCircleFill size={16} />
              </div>
            ) : null}
          </div>
          {inquiryItem.qnaStatus === 'ANSWER_COMPLETE' &&
          inquiryItem.answer !== null ? (
            <div
              className={
                selected === i
                  ? 'flex border-box border-b-2 py-4 pl-8 bg-slate-50'
                  : 'hidden'
              }
            >
              <p>A. {inquiryItem.answer.comment}</p>
            </div>
          ) : null}
        </div>
      ))}
    </div>
  );
}

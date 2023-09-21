import { useState } from 'react';
import type { InquiryContentType } from '../Tab';
import { TiArrowSortedDown, TiArrowSortedUp } from 'react-icons/ti';
import { BsFillXCircleFill } from 'react-icons/bs';
import InquiryEditDeleteBtn from './InquiryEditDeleteBtn';

export interface OwnProps {
  inquiry: InquiryContentType[] | null;
  setInquiry: React.Dispatch<React.SetStateAction<InquiryContentType[] | null>>;
}

export default function InquiryElement({ inquiry, setInquiry }: OwnProps) {
  const [openInquiry, setOpenInquiry] = useState<number | null>(null);
  const [edit, setEdit] = useState<number | null>(null);
  const userId: string | null = localStorage.getItem('userId');
  const parsedUserId: number | null = userId !== null ? Number(userId) : null;

  const inquiryToggle = (i: number) => {
    if (openInquiry === i) {
      setOpenInquiry(null);
    } else {
      setOpenInquiry(i);
    }
  };

  return (
    <div>
      {inquiry?.map((inquiryItem, i) => {
        const isCurrentUserAuthor =
          parsedUserId !== null && parsedUserId === inquiryItem.userId;

        return (
          <div
            key={inquiryItem.questionId}
            className="relative flex flex-col border-box"
          >
            <div className="flex py-4 pl-8 border-b-2">
              {edit === i ? (
                <input
                  type="text"
                  value={inquiryItem.comment}
                  onChange={(e) => {
                    const editedInquiry = [...inquiry];
                    editedInquiry[i].comment = e.target.value;
                    setInquiry(editedInquiry);
                  }}
                />
              ) : (
                <p
                  onClick={() => {
                    inquiryToggle(i);
                  }}
                  className="cursor-pointer"
                >
                  Q. {inquiryItem.comment}
                </p>
              )}
              {inquiryItem.qnaStatus === 'ANSWER_COMPLETE' ? (
                <div className="flex items-center pl-2">
                  {openInquiry === i ? (
                    <TiArrowSortedUp size={20} />
                  ) : (
                    <TiArrowSortedDown size={20} />
                  )}
                </div>
              ) : (
                <div className="flex items-center pl-2">
                  <BsFillXCircleFill size={13} />
                </div>
              )}
              {isCurrentUserAuthor ? (
                <InquiryEditDeleteBtn
                  i={i}
                  inquiryItem={inquiryItem}
                  inquiry={inquiry}
                  setInquiry={setInquiry}
                  edit={edit}
                  setEdit={setEdit}
                />
              ) : null}
            </div>
            {inquiryItem.qnaStatus === 'ANSWER_COMPLETE' &&
            inquiryItem.answer !== null ? (
              <div
                className={
                  openInquiry === i
                    ? 'flex border-box border-b-2 py-4 pl-8 bg-slate-50'
                    : 'hidden'
                }
              >
                <p>A. {inquiryItem.answer.comment}</p>
              </div>
            ) : null}
          </div>
        );
      })}
    </div>
  );
}

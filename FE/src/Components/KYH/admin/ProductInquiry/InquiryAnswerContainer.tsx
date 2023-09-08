import api from 'api';
import { useEffect, useState } from 'react';

interface OwnProps {
  answer: string | undefined;
  inquiryId: number;
}

export default function InquiryAnswerContainer({
  answer,
  inquiryId,
}: OwnProps) {
  const [inquiryAnswer, setInquiryAnswer] = useState('');
  const [comment, setComment] = useState('');

  useEffect(() => {
    if (answer === undefined) return;
    setInquiryAnswer(answer);
  }, [setInquiryAnswer, answer]);

  const inquiryAnswerSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    api
      .post(`/api/qna/${inquiryId}/answer`, {
        comment,
      })
      .then((res) => {
        console.log(res);
        setInquiryAnswer(comment);
      })
      .catch((err) => {
        console.error(err);
      });
  };
  return (
    <form className="flex flex-col mt-4 ml-8" onSubmit={inquiryAnswerSubmit}>
      <span className="mb-2 font-bold">답변</span>
      {inquiryAnswer === '' ? (
        <div className="flex flex-col items-center w-128">
          <textarea
            placeholder="답변을 입력해 주세요"
            className="flex h-32 pt-4 pl-4 text-sm bg-white border border-gray-500 rounded-xl w-128"
            value={comment}
            onChange={(e) => {
              setComment(e.target.value);
            }}
          ></textarea>
          <button
            type="submit"
            className="w-20 mt-2 bg-white border-2 border-black rounded-md hover:bg-gray-700 hover:text-white"
          >
            등록
          </button>
        </div>
      ) : (
        <p className="flex h-32 pt-4 pl-4 overflow-y-scroll text-sm border border-gray-500 rounded-xl w-128">
          {inquiryAnswer}
        </p>
      )}
    </form>
  );
}

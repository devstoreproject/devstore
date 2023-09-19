import React, { useState } from 'react';
import api from 'api';
import { useParams } from 'react-router-dom';
import type { InquiryContentType } from '../Tab';

interface OwnProps {
  inquiry: InquiryContentType[] | null;
  setInquiry: React.Dispatch<React.SetStateAction<InquiryContentType[] | null>>;
}

export default function InquiryRegister({ inquiry, setInquiry }: OwnProps) {
  const [inputValue, setInputValue] = useState<string>('');
  const userId: string | null = localStorage.getItem('userId');
  const parsedUserId: number | null = userId !== null ? Number(userId) : null;
  const { id } = useParams();
  const headers = {
    headers: { Authorization: localStorage.getItem('authorization') },
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(event.target.value);
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (userId === null) {
      alert('로그인 후에 사용할 수 있습니다.');
      return;
    }
    if (inputValue === '') return;

    const confirmRegistration = window.confirm('글을 등록하시겠습니까?');

    const requestData = {
      comment: inputValue,
    };
    if (confirmRegistration) {
      api
        .post(`api/qna/items/${id as string}`, requestData, headers)
        .then((res) => {
          if (inquiry !== null) {
            setInquiry([
              ...inquiry,
              {
                answer: null,
                comment: inputValue,
                qnaStatus: 'REGISTER',
                questionId: res.data.data.questionId,
                userId: parsedUserId,
              },
            ]);
          }
          setInputValue('');
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  return (
    <form className="flex justify-center pt-4 pb-4" onSubmit={handleSubmit}>
      <input
        id="inquiryRegister"
        type="text"
        placeholder="문의 사항을 입력해주세요."
        className="px-5 py-3 border lg:w-full rounded-3xl border-gray w-250"
        value={inputValue}
        onChange={handleInputChange}
      />
      <button
        className="w-40 py-3 ml-2 border border-gray bg-slate-800 text-slate-50 rounded-3xl"
        type="submit"
      >
        문의하기
      </button>
    </form>
  );
}

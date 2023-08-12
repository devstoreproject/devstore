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
  const userId = localStorage.getItem('userId') ?? '';
  const { id } = useParams();

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(event.target.value);
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const requestData = {
      comment: inputValue,
      userId,
    };

    api
      .post(`api/qna/items/${id as string}`, requestData)
      .then((res) => {
        setInquiry(res.data.data.content);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <form className="flex justify-center pb-4" onSubmit={handleSubmit}>
      <input
        id="inquiryRegister"
        type="text"
        placeholder="문의 사항을 입력해주세요."
        className="px-5 py-3 border lg:w-full rounded-3xl border-gray w-250"
        value={inputValue}
        onChange={handleInputChange}
      />
      <button
        className="px-24 py-3 ml-2 border border-gray bg-slate-800 text-slate-50 rounded-3xl"
        type="submit"
      >
        문의하기
      </button>
    </form>
  );
}

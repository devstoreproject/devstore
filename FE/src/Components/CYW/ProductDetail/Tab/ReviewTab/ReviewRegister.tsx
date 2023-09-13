import api from 'api';
import { useState } from 'react';
import { useParams } from 'react-router-dom';
import type { ReviewContentType } from '../Tab';
import useFetchProfile from 'hooks/mypage/useFetchProfile';

interface OwnProps {
  setReview: React.Dispatch<React.SetStateAction<ReviewContentType[] | null>>;
  review: ReviewContentType[] | null;
}

export default function ReviewRegister({ review, setReview }: OwnProps) {
  const [inputValue, setInputValue] = useState<string>('');
  const userId: string | null = localStorage.getItem('userId');
  const { id } = useParams();
  const profile = useFetchProfile();
  const nickname = profile.nickname;

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

    if (confirmRegistration) {
      const requestData = {
        comment: inputValue,
      };

      api
        .post(`api/items/${id as string}/reviews`, requestData, {
          headers: {
            Authorization: localStorage.getItem('authorization'),
          },
        })
        .then((res) => {
          if (review !== null) {
            const currentDate = new Date();
            const year = currentDate.getFullYear();
            const month = currentDate.getMonth() + 1;
            const day = currentDate.getDate();
            const formattedDate = `${year}-${month}-${day}`;
            setReview([
              ...review,
              {
                best: false,
                comment: inputValue,
                createdAt: formattedDate,
                image: null,
                itemId: Number(id),
                modifiedAt: formattedDate,
                reviewId: review[review.length - 1].reviewId + 1,
                userId: Number(userId),
                userName: nickname,
              },
            ]);
            setInputValue('');
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  return (
    <form className="flex justify-center pb-4 pt-4" onSubmit={handleSubmit}>
      <div className="flex flex-col">
        <input
          id="inquiryRegister"
          type="text"
          placeholder="상품에 대한 리뷰를 입력해주세요."
          className="px-5 py-3 border lg:w-full rounded-3xl border-gray w-250"
          onChange={handleInputChange}
        />
      </div>
      <button className="px-10 py-2 ml-2 border border-gray bg-slate-800 text-slate-50 rounded-3xl">
        리뷰 남기기
      </button>
    </form>
  );
}

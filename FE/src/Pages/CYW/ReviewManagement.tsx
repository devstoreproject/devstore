import AllReviewManagement from 'Components/CYW/RewiewManagement/AllReviewManagement';
import BestReviewManagement from 'Components/CYW/RewiewManagement/BestReviewManagement';
import api from 'api';
import { useEffect, useState } from 'react';
import { BiSolidCommentCheck } from 'react-icons/bi';

export interface ReviewContentType {
  best: false;
  comment: string;
  createdAt: string;
  image: string | null;
  itemId: number;
  modifiedAt: string;
  reviewId: number;
  userId: number;
  userName: string;
}
interface ImageListType {
  imageId: number;
  imageOrder: number;
  originalPath: string;
  representative: boolean;
  thumbnailPath: string;
  title: string;
}

interface OptionListType {
  additionalPrice: number;
  itemCount: number;
  itemId: number;
  optionDetail: string;
  optionId: number;
  optionName: string;
}
export interface ItemContentType {
  category: string;
  defaultCount: number;
  deliveryPrice: number;
  description: string;
  imageList: ImageListType[];
  itemId: number;
  itemPrice: number;
  like: boolean;
  name: string;
  optionList: OptionListType[];
  totalCount: number;
  viewCount: number;
}

export interface BestReviewType {
  best: boolean;
  comment: string;
  createdAt: string;
  image: string | null;
  itemId: number;
  modifiedAt: string;
  reviewId: number;
  userId: number;
  userName: string;
}

export default function ReviewManagement() {
  const [review, setReview] = useState<ReviewContentType[]>([]);
  const [bestReview, setBestReview] = useState<BestReviewType[]>([]);
  const [item, setItem] = useState<ItemContentType[]>([]);
  const REVIEW_API_URL = 'api/reviews';
  const ITEM_API_URL = 'api/items';

  useEffect(() => {
    api
      .get(`${REVIEW_API_URL}?page=0&size=20&sort=createdAt`)
      .then((getRes) => {
        setReview(getRes.data.data.content);
      })
      .catch((getErr) => {
        console.log('Error fetching reviews:', getErr);
      });
  }, [setReview]);

  useEffect(() => {
    api
      .get(`${ITEM_API_URL}?page=0&size=20&sort=createdAt`)
      .then((getRes) => {
        setItem(getRes.data.data.content);
      })
      .catch((getErr) => {
        console.log('Error fetching itemList:', getErr);
      });
  }, [setItem]);

  useEffect(() => {
    api
      .get(`${REVIEW_API_URL}/best`)
      .then((getRes) => {
        setBestReview(getRes.data.data);
      })
      .catch((getErr) => {
        console.log('Error fetching bestReview:', getErr);
      });
  }, [setBestReview]);

  return (
    <div className="flex flex-col bg-light-gray">
      <div className="flex items-center">
        <BiSolidCommentCheck size={30} />
        <h1 className="pl-2 font-bold text-2xl">리뷰</h1>
      </div>
      <div className="pt-10">
        <h2 className="font-bold text-xl pb-5">베스트 리뷰 목록</h2>
        <BestReviewManagement
          item={item}
          bestReview={bestReview}
          setBestReview={setBestReview}
        />
      </div>
      <div className="pt-10">
        <h2 className="font-bold text-xl pb-5">전체 리뷰 목록</h2>
        <AllReviewManagement
          bestReview={bestReview}
          review={review}
          item={item}
          setBestReview={setBestReview}
        />
      </div>
    </div>
  );
}

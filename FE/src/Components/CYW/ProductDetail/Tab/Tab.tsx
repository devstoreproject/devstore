import ProductTab from './ProductTab/ProductTab';
import ReviewTab from './ReviewTab/ReviewTab';
import { useEffect, useState } from 'react';
import InquiryTab from './InquiryTab/InquiryTab';
import type { ProductType } from '../../../../Pages/CYW/ProductDetail';
import fetchInquiry from 'utils/productDetail/fetchInquiry';
import fetchReview from 'utils/productDetail/fetchReview';
import { useParams } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { setTab } from 'store/modules/setCurrentTab';

interface ProductTypeProps {
  product: ProductType;
  tab: number;
}

export type QnaStatus = 'ANSWER_COMPLETE' | 'REGISTER';
interface Sort {
  empty: boolean;
  sorted: boolean;
  unsorted: boolean;
}

export interface Pageable {
  offset: number;
  pageNumber: number;
  pageSize: number;
  paged: boolean;
  sort: Sort;
  unpaged: boolean;
}

interface AnswerData {
  answerId: number | null;
  qnaStatus: QnaStatus;
  comment: string;
  userId: number;
  questionId: number;
}
export interface InquiryContentType {
  answer: AnswerData | null;
  comment: string;
  qnaStatus: QnaStatus;
  questionId: number;
  userId: number | null | string;
}
export interface InquiryType {
  content: InquiryContentType[];
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  pageable: Pageable;
  size: number;
  sort: Sort;
  totalElements: number;
  totalPages: number;
}

export interface ReviewContentType {
  best: boolean;
  comment: string;
  createdAt: string;
  image: null;
  itemId: number;
  modifiedAt: string;
  reviewId: number;
  userId: number;
  userName: string;
}
export interface ReviewType {
  content: ReviewContentType[];
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  pageable: {
    offset: number;
    pageNumber: number;
    pageSize: number;
    paged: boolean;
    sort: {
      empty: boolean;
      sorted: boolean;
      unsorted: boolean;
    };
    unpaged: boolean;
  };
  size: number;
  sort: Sort;
  totalElements: number;
  totalPages: number;
}

export default function Tab({ product, tab }: ProductTypeProps) {
  const dispatch = useDispatch();
  const [review, setReview] = useState<ReviewContentType[] | null>(null);
  const [inquiry, setInquiry] = useState<InquiryContentType[] | null>(null);
  const [reviewPage, setReviewPage] = useState<number>(0);
  const [inquiryPage, setInquiryPage] = useState<number>(0);
  const [reviewTotalPage, setReviewTotalPage] = useState<number>(0);
  const [inquiryTotalPage, setInquiryTotalPage] = useState<number>(0);
  const { id } = useParams();

  const handleClick = (event: React.MouseEvent<HTMLParagraphElement>) => {
    if (event.currentTarget.textContent === '상품 상세') {
      if (tab === 0) return;
      dispatch(setTab(0));
    }
    if (event.currentTarget.textContent === '상품 리뷰') {
      if (tab === 1) return;
      dispatch(setTab(1));
    }
    if (event.currentTarget.textContent === '상품 문의') {
      if (tab === 2) return;
      dispatch(setTab(2));
    }
  };

  useEffect(() => {
    fetchReview(id as string, setReview, reviewPage, setReviewTotalPage);
    fetchInquiry(id as string, setInquiry, inquiryPage, setInquiryTotalPage);
  }, [reviewPage, inquiryPage]);

  return (
    <div className="w-3/4 pt-12">
      <div className="flex items-center border-2 rounded-lg border-box text-slate-500">
        <p
          className={`text-center w-1/3 border-slate-300  cursor-pointer ${
            tab === 0 ? 'underline underline-offset-8' : ''
          }`}
          onClick={handleClick}
        >
          상품 상세
        </p>
        <p
          className={`text-center w-1/3 border-x border-x-slate-300 my-4 cursor-pointer ${
            tab === 1 ? 'underline underline-offset-8' : ''
          }`}
          onClick={handleClick}
        >
          상품 리뷰
        </p>
        <p
          className={`text-center w-1/3 cursor-pointer ${
            tab === 2 ? 'underline underline-offset-8' : ''
          }`}
          onClick={handleClick}
        >
          상품 문의
        </p>
      </div>
      <div>
        <ProductTab tab={tab} description={product.description} />
        <ReviewTab
          tab={tab}
          review={review}
          setReview={setReview}
          page={reviewPage}
          setPage={setReviewPage}
          totalPage={reviewTotalPage}
        />
        <InquiryTab
          tab={tab}
          inquiry={inquiry}
          setInquiry={setInquiry}
          page={inquiryPage}
          setPage={setInquiryPage}
          totalPage={inquiryTotalPage}
        />
      </div>
    </div>
  );
}

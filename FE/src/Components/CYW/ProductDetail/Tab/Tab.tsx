import ProductTab from './ProductTab/ProductTab';
import ReviewTab from './ReviewTab/ReviewTab';
import { useState } from 'react';
import InquiryTab from './InquiryTab/InquiryTab';

export default function Tab() {
  const [tab, setTab] = useState<number>(0);

  const handleClick = (event: React.MouseEvent<HTMLParagraphElement>) => {
    if (event.currentTarget.textContent === '상품 상세') {
      setTab(0);
    } else if (event.currentTarget.textContent === '상품 리뷰') {
      setTab(1);
    } else if (event.currentTarget.textContent === '상품 문의') {
      setTab(2);
    }
  };

  return (
    <div className="pt-12 w-3/4">
      <div className="flex items-center border-box border-2 text-slate-500 rounded-lg">
        <p
          className={`text-center w-1/3 border-slate-300 underline ${
            tab === 0 ? 'underline-offset-8' : ''
          }`}
          onClick={handleClick}
        >
          상품 상세
        </p>
        <p
          className={`text-center w-1/3 border-x border-x-slate-300 my-4 ${
            tab === 1 ? 'underline-offset-8' : ''
          }`}
          onClick={handleClick}
        >
          상품 리뷰
        </p>
        <p
          className={`text-center w-1/3 ${
            tab === 2 ? 'underline-offset-8' : ''
          }`}
          onClick={handleClick}
        >
          상품 문의
        </p>
      </div>
      <div>
        <ProductTab tab={tab} />
        <ReviewTab tab={tab} />
        <InquiryTab tab={tab} />
      </div>
    </div>
  );
}

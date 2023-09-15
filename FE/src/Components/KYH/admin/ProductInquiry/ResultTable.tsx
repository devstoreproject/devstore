import type { Inquiry } from 'model/inquiry';
import ResultTableContents from './ResultTableContents';
import ResultTableTitle from './ResultTableTitle';
import useFetchProducts from 'hooks/admin/product/useFetchProducts';
import type { Product } from 'model/product';
import { useState } from 'react';
import InquiryDetail from './InquiryDetail';

interface OwnProps {
  inquirys: Inquiry[];
  page: number;
}

export default function ResultTable({ inquirys, page }: OwnProps) {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [inquiryId, setInquiryId] = useState(0);
  const [productName, setProductName] = useState('');
  const products: Product[] = useFetchProducts();

  return (
    <div className="relative flex flex-col mt-6 mb-4 bg-gray-100 border border-gray-400 rounded-t-lg h-132.8 w-300">
      {isModalOpen ? (
        <div className="absolute h-132.8 w-300 bg-black opacity-50 rounded-t-lg" />
      ) : null}
      <ResultTableTitle />
      {inquirys.length === 0 ? (
        <span className="flex justify-center items-center w-300 h-132.8">
          등록된 상품문의 내역이 없습니다.
        </span>
      ) : (
        inquirys.map((inquiry, idx) => (
          <ResultTableContents
            key={inquiry.questionId}
            questionId={inquiry.questionId}
            comment={inquiry.comment}
            answer={inquiry.answer}
            idx={idx}
            page={page}
            name={
              products.filter((product) => product.itemId === inquiry.itemId)[0]
                ?.name
            }
            setIsModalOpen={setIsModalOpen}
            setInquiryId={setInquiryId}
            setProductName={setProductName}
          />
        ))
      )}
      {isModalOpen ? (
        <InquiryDetail
          setIsModalOpen={setIsModalOpen}
          inquiryId={inquiryId}
          productName={productName}
        />
      ) : null}
    </div>
  );
}

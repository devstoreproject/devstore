import useFetchInquiryDetail from 'hooks/admin/inquiry/useFetchInquiryDetail';
import InquiryAnswerContainer from './InquiryAnswerContainer';
import InquiryCommentContainer from './InquiryCommentContainer';
import ModalCloseBtn from './ModalCloseBtn';
import ProductNameContainer from './ProductNameContainer';

interface OwnProps {
  inquiryId: number;
  setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
  productName: string;
}

export default function InquiryDetail({
  setIsModalOpen,
  inquiryId,
  productName,
}: OwnProps) {
  const inquiryDetail = useFetchInquiryDetail(inquiryId);

  return (
    <div className="absolute flex flex-col bg-gray-100 border border-gray-500 rounded-xl left-80 h-132 w-144">
      <ModalCloseBtn setIsModalOpen={setIsModalOpen} />
      <ProductNameContainer productName={productName} />
      <InquiryCommentContainer comment={inquiryDetail?.comment} />
      <InquiryAnswerContainer
        answer={inquiryDetail?.answer?.comment}
        inquiryId={inquiryId}
      />
    </div>
  );
}

import { MdOutlineArrowForwardIos } from 'react-icons/md';
import { useNavigate } from 'react-router-dom';

export default function InquiryListTitle() {
  const navigate = useNavigate();

  return (
    <div className="flex items-center justify-between">
      <span className="mb-2 text-lg font-bold">문의내역</span>
      <button
        className="flex items-center -mt-2 text-sm text-gray-500 hover:text-black hover:font-bold"
        onClick={() => {
          navigate('/admin/productinquiry');
        }}
      >
        <span className="mr-2">더보기</span>
        <MdOutlineArrowForwardIos />
      </button>
    </div>
  );
}

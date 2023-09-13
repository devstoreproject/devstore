import { MdOutlineArrowForwardIos } from 'react-icons/md';
import { useNavigate } from 'react-router-dom';

export default function OrderListTitle() {
  const navigate = useNavigate();

  return (
    <div className="flex items-center justify-between">
      <span className="mb-4 text-lg font-bold">주문내역</span>
      <button
        className="flex items-center -mt-4 text-sm text-gray-500 hover:text-black hover:font-bold"
        onClick={() => {
          navigate('/admin/orderedlist');
        }}
      >
        <span className="mr-2">더보기</span>
        <MdOutlineArrowForwardIos />
      </button>
    </div>
  );
}

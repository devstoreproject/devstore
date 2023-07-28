import { useNavigate } from 'react-router-dom';

export default function OrderListBtnContainer() {
  const navigate = useNavigate();
  const deleteBtnHandler = () => {};
  const postBtnHandler = () => {
    navigate('/admin/productlist/post');
  };
  const editBtnHandler = () => {
    navigate('/admin/productlist/edit');
  };

  return (
    <div className="ml-auto">
      <button
        className="w-40 h-10 mr-6 border border-gray-500 rounded-3xl"
        onClick={deleteBtnHandler}
      >
        선택 상품 삭제하기
      </button>
      <button
        className="w-40 h-10 mr-6 text-white bg-gray-700 rounded-3xl"
        onClick={editBtnHandler}
      >
        선택 상품 수정하기
      </button>
      <button
        className="w-40 h-10 text-white bg-gray-700 rounded-3xl"
        onClick={postBtnHandler}
      >
        상품 등록하기
      </button>
    </div>
  );
}

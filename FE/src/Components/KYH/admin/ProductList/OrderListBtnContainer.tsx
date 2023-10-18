import api from 'api';
import axios from 'axios';
import type { Product } from 'model/product';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import type { StoreType } from 'model/redux';

interface OwnProps {
  checkedId: number[];
  setProducts: React.Dispatch<React.SetStateAction<Product[]>>;
}

export default function OrderListBtnContainer({
  checkedId,
  setProducts,
}: OwnProps) {
  const navigate = useNavigate();
  const getItemId = useSelector((e: StoreType) => e.currentItemId);

  const deleteBtnHandler = () => {
    const userConfirmed = window.confirm('삭제하시겠습니까?');

    if (userConfirmed) {
      const fetchDelete = checkedId.map(async (id) => {
        await api.delete(`/api/items/${id}`);
      });

      axios
        .all(fetchDelete)
        .then(() => {
          setProducts((prev) =>
            prev.filter((product) => !checkedId.includes(product.itemId))
          );
          window.alert('삭제되었습니다.');
        })
        .catch((err) => {
          console.log(err);
        });
    } else {
      window.alert('취소되었습니다.');
    }
  };

  return (
    <div className="mt-4 ml-auto">
      <button
        className="w-40 h-10 mr-6 border border-gray-500 rounded-3xl"
        onClick={deleteBtnHandler}
      >
        선택 상품 삭제하기
      </button>
      <button
        className="w-40 h-10 mr-6 text-white bg-gray-700 rounded-3xl"
        onClick={() => {
          navigate(`/admin/productlist/edit/${getItemId}`);
        }}
      >
        선택 상품 수정하기
      </button>
      <button
        className="w-40 h-10 text-white bg-gray-700 rounded-3xl"
        onClick={() => {
          navigate('/admin/productlist/post');
        }}
      >
        상품 등록하기
      </button>
    </div>
  );
}

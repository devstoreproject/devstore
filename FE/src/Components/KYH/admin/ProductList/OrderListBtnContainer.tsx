import api from 'api';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

interface OwnProps {
  checkedId: number[];
}

export default function OrderListBtnContainer({ checkedId }: OwnProps) {
  const navigate = useNavigate();
  const Authorization = localStorage.getItem('authorization');

  const deleteBtnHandler = () => {
    const userConfirmed = window.confirm('삭제하시겠습니까?');

    if (userConfirmed) {
      const fetchDelete = checkedId.slice(1).map(async (id) => {
        await api.delete(`/api/items/${id}`, {
          headers: {
            Authorization,
          },
        });
      });

      axios
        .all(fetchDelete)
        .then(() => {
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
    <div className="ml-auto">
      <button
        className="w-40 h-10 mr-6 border border-gray-500 rounded-3xl"
        onClick={deleteBtnHandler}
      >
        선택 상품 삭제하기
      </button>
      <button
        className="w-40 h-10 mr-6 text-white bg-gray-700 rounded-3xl"
        onClick={() => {
          navigate('/admin/productlist/edit');
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

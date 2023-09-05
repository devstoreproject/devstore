import useFetchReviews from 'hooks/admin/reviews/useFetchReviews';
import type { OrderItem } from 'model/order';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { setTab } from 'store/modules/setCurrentTab';
import addCommasToPrice from 'utils/addCommasToPrice';

interface OwnProps extends OrderItem {
  idx: number;
}

export default function OrderItemDetail({
  idx,
  itemName,
  itemCount,
  itemPrice,
  discountPrice,
  itemId,
}: OwnProps) {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const reviews = useFetchReviews();
  const filteredReview = reviews.filter((review) => review.itemId === itemId);
  const totalPrice = addCommasToPrice(discountPrice);
  const price = addCommasToPrice(itemPrice);

  return (
    <div className="flex items-center h-16 border-b border-gray-300">
      <span className="w-4 ml-2 text-sm text-center text-gray-500">
        {idx + 1}
      </span>
      <p className="w-64 mx-2 text-sm text-center truncate">{itemName}</p>
      <div className="flex flex-col items-center w-52">
        <span className="text-sm text-center">{totalPrice}원</span>
        <span className="text-sm text-center">
          {itemCount}개 (개당 {price}원)
        </span>
      </div>
      <div className="flex justify-center w-60">
        {filteredReview.length === 0 ? (
          <input
            type="button"
            value="리뷰 작성하러 가기"
            className="px-2 py-1 text-sm text-gray-600 border border-gray-400 cursor-pointer rounded-2xl hover:bg-black hover:text-white"
            onClick={() => {
              navigate(`/products/${itemId}`);
              dispatch(setTab(1));
            }}
          />
        ) : (
          <p className="text-sm line-clamp-3">{filteredReview[0].comment}</p>
        )}
      </div>
    </div>
  );
}

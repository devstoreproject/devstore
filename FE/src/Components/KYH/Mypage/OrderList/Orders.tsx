import { useEffect } from 'react';
import Order from './Order';
import useFetchReviews from 'hooks/admin/product/useFetchReviews';
import api from 'api';

export default function Orders() {
  const reviews = useFetchReviews();
  const Authorization = localStorage.getItem('authorization');
  console.log(reviews);

  useEffect(() => {
    api
      .get('/api/orders', {
        headers: {
          Authorization,
        },
      })
      .then((res) => {
        console.log(res.data.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <div className="flex flex-col mt-14">
      <span className="mb-4 font-bold">주문 목록</span>
      <ul className="w-200">
        <Order />
      </ul>
    </div>
  );
}

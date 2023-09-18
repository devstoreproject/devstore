import api from 'api';
import type { Order } from 'model/order';
import { useEffect, useState } from 'react';

const useFetchOrdersPaging = (page: number) => {
  const [orders, setOrders] = useState<Order[]>([]);
  const [totalPages, setTotalPages] = useState<number>(0);

  useEffect(() => {
    api
      .get(`/api/orders?page=${page}&size=10`)
      .then((res) => {
        setOrders(res.data.data.content);
        setTotalPages(res.data.data.totalPages);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setOrders]);

  return { orders, totalPages };
};

export default useFetchOrdersPaging;

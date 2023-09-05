import api from 'api';
import type { Order } from 'model/order';
import { useEffect, useState } from 'react';

const useFetchOrders = () => {
  const [orders, setOrders] = useState<Order[]>([]);
  const Authorization = localStorage.getItem('authorization');

  useEffect(() => {
    api
      .get('/api/orders', {
        headers: {
          Authorization,
        },
      })
      .then((res) => {
        setOrders(res.data.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setOrders, Authorization]);

  return orders;
};

export default useFetchOrders;

import api from 'api';
import type { Order } from 'model/order';
import { useEffect, useState } from 'react';

const useFetchOrders = () => {
  const [orders, setOrders] = useState<Order[]>([]);

  useEffect(() => {
    api
      .get('/api/orders')
      .then((res) => {
        setOrders(res.data.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setOrders]);

  return orders;
};

export default useFetchOrders;

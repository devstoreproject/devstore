import api from 'api';
import { useEffect, useState } from 'react';

const useFetchItemsSales = () => {
  const [itemSales, setItemSales] = useState([]);

  useEffect(() => {
    api
      .get('/api/orders/items-sale')
      .then((res) => {
        console.log(res.data.data);
        // setItemSales(res.data.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setItemSales]);

  return itemSales;
};

export default useFetchItemsSales;

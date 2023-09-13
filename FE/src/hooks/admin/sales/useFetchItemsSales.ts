import api from 'api';
import type { ItemSales } from 'model/sales';
import { useEffect, useState } from 'react';

const useFetchItemsSales = () => {
  const [itemSales, setItemSales] = useState<ItemSales[]>([]);

  useEffect(() => {
    api
      .get('/api/orders/items-sale')
      .then((res) => {
        setItemSales(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setItemSales]);

  return itemSales;
};

export default useFetchItemsSales;

import api from 'api';
import type { ItemSales } from 'model/sales';
import { useEffect, useState } from 'react';

const useFetchItemsSalesPaging = (page: number) => {
  const [itemSales, setItemSales] = useState<ItemSales[]>([]);

  useEffect(() => {
    api
      .get(`/api/orders/items-sale?page=${page}&size=10`)
      .then((res) => {
        setItemSales(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setItemSales]);

  return itemSales;
};

export default useFetchItemsSalesPaging;

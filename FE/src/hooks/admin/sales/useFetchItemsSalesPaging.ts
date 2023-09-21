import api from 'api';
import type { ItemSales } from 'model/sales';
import { useEffect, useState } from 'react';

const useFetchItemsSalesPaging = (page: number) => {
  const [itemSales, setItemSales] = useState<ItemSales[]>([]);
  const [totalPages, setTotalPages] = useState<number>(0);

  useEffect(() => {
    api
      .get(`/api/orders/items-sale?page=${page}&size=10`)
      .then((res) => {
        setItemSales(res.data.data);
        if (res.data.totalPages !== undefined)
          setTotalPages(res.data.totalPages);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setItemSales]);

  return { itemSales, totalPages };
};

export default useFetchItemsSalesPaging;

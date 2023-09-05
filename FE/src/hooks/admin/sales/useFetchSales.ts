import api from 'api';
import type { Sales } from 'model/sales';
import { useEffect, useState } from 'react';

const useFetchSales = (salesStatus: boolean[]) => {
  const [sales, setSales] = useState<Sales[]>([]);

  useEffect(() => {
    if (salesStatus[1]) {
      api
        .get('/api/orders/month-sale')
        .then((res) => {
          setSales(res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }

    if (salesStatus[0]) {
      api
        .get('/api/orders/day-sale')
        .then((res) => {
          setSales(res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, [salesStatus, setSales]);

  return sales;
};

export default useFetchSales;

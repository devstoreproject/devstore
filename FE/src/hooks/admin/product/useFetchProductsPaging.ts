import api from 'api';
import type { Product } from 'model/product';
import { useEffect, useState } from 'react';

const useFetchProductsPaging = (page: number) => {
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    api
      .get(`/api/items?page=${page}&size=10`)
      .then((res) => {
        setProducts(res.data.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setProducts, page]);

  return { products, setProducts };
};

export default useFetchProductsPaging;

import api from 'api';
import type { Product } from 'model/product';
import { useEffect, useState } from 'react';

const useFetchProductsPaging = (page: number) => {
  const [products, setProducts] = useState<Product[]>([]);
  const [totalPages, setTotalPages] = useState<number>(0);

  useEffect(() => {
    api
      .get(`/api/items?page=${page}&size=10`)
      .then((res) => {
        setTotalPages(res.data.data.totalPages);
        setProducts(res.data.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setProducts, page]);

  return { products, setProducts, totalPages };
};

export default useFetchProductsPaging;

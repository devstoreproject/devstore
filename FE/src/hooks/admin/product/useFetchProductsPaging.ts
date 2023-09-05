import api from 'api';
import { useEffect, useState } from 'react';

const useFetchProductsPaging = (page: number) => {
  const [products, setProducts] = useState([]);
  const Authorization = localStorage.getItem('authorization');

  useEffect(() => {
    api
      .get(`/api/items?page=${page}&size=10`, {
        headers: {
          Authorization,
        },
      })
      .then((res) => {
        setProducts(res.data.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setProducts, page]);

  return products;
};

export default useFetchProductsPaging;

import api from 'api';
import { useEffect, useState } from 'react';

const useFetchProducts = () => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    api
      .get('/api/items')
      .then((res) => {
        setProducts(res.data.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setProducts]);

  return products;
};

export default useFetchProducts;

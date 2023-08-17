import api from 'api';
import { useEffect, useState } from 'react';

const useFetchProducts = () => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    api
      .get('/api/items?page=0&size=10')
      .then((res) => {
        setProducts(res.data.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return products;
};

export default useFetchProducts;

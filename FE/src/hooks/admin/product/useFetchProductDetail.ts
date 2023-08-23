import api from 'api';
import { useEffect, useState } from 'react';

const useFetchProductDetail = (productId: number) => {
  const [product, setProduct] = useState({
    name: '',
    category: '',
    itemPrice: 0,
    defaultCount: 0,
    optionList: [],
  });
  useEffect(() => {
    api
      .get(`/api/items/${productId}`)
      .then((res) => {
        setProduct(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setProduct, productId]);

  return product;
};

export default useFetchProductDetail;

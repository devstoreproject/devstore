import api from 'api';
import type { ProductDetail } from 'model/product';
import { useEffect, useState } from 'react';

const useFetchProductDetail = (productId: number) => {
  const [product, setProduct] = useState<ProductDetail>({
    name: '',
    category: '',
    itemPrice: 0,
    totalCount: 0,
    optionList: [],
    salesQuantity: 0,
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

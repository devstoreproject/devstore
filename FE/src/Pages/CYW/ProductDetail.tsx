import ProductImg from '../../Components/CYW/ProductDetail/ProductImg/ProductImg';
import ProductInformation from '../../Components/CYW/ProductDetail/ProductInformation/ProductInformation';
import Tab from '../../Components/CYW/ProductDetail/Tab/Tab';
import { useEffect, useState } from 'react';
import api from 'api';

interface Option {
  itemId: number;
  optionId: number;
  optionDetail: string;
}
interface Spec {
  specId: number;
  itemName: string;
  content: string;
}

export interface ProductType {
  category: string;
  defaultCount: number;
  deliveryPrice: number;
  description: string;
  itemId: number;
  itemPrice: number;
  name: string;
  optionList: Option[];
  specList: Spec[];
  totalCount: number;
}

export default function ProductDetail() {
  const [product, setProduct] = useState<ProductType | null>(null);

  useEffect(() => {
    api
      .get('/api/items/1')
      .then((res) => {
        setProduct(res.data.data);
        console.log(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setProduct]);

  if (product === null) {
    return (
      <div className="flex justify-center items-center pt-104 pb-104 font-bold w-full h-full">
        Loading...
      </div>
    );
  }

  return (
    <div>
      <div className="flex justify-center">
        <p className="w-1/2 pt-10 text-slate-600">{`카테고리 > ${product.category}`}</p>
      </div>
      <div className="flex justify-center pt-10">
        <ProductImg />
        <ProductInformation product={product} />
      </div>
      <div className="flex justify-center">
        <Tab product={product} />
      </div>
    </div>
  );
}

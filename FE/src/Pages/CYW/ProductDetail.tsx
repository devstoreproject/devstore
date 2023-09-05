import ProductImg from '../../Components/CYW/ProductDetail/ProductImg/ProductImg';
import ProductInformation from '../../Components/CYW/ProductDetail/ProductInformation/ProductInformation';
import Tab from '../../Components/CYW/ProductDetail/Tab/Tab';
import { useEffect, useState } from 'react';
import api from 'api';
import { useParams } from 'react-router-dom';
import type { StoreType } from 'model/redux';
import { useDispatch, useSelector } from 'react-redux';
import { setTab } from 'store/modules/setCurrentTab';

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
  const dispatch = useDispatch();
  const tab = useSelector((state: StoreType) => state.currentTab);
  const [product, setProduct] = useState<ProductType | null>(null);
  const { id } = useParams();

  useEffect(() => {
    api
      .get(`/api/items/${id as string}`)
      .then((res) => {
        setProduct(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setProduct]);

  useEffect(() => {
    dispatch(setTab(tab));
  }, [tab]);

  if (product === null) {
    return (
      <div className="flex items-center justify-center w-full h-full font-bold pt-104 pb-104">
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
        <Tab product={product} tab={tab} />
      </div>
    </div>
  );
}

import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import type { StoreType } from 'model/redux';
import ProductForm from 'Components/LSM/Product/ProductForm/ProductForm';
import ProductTitle from 'Components/LSM/Product/ProductTitle';
import api from 'api';
import { setOptionId } from 'store/modules/setOptionId';

export default function ProductEdit() {
  const pathName = useLocation().pathname.split('/').slice(3)[0];
  const [datas, setDatas] = useState({});
  const getItemId = useSelector((e: StoreType) => e.currentItemId);
  const dispatch = useDispatch();

  const fetchData = async () => {
    try {
      const res = await api.get(`/api/items/${getItemId}`);
      setDatas(res?.data?.data);
      const optionList = res?.data?.data?.optionList;
      dispatch(setOptionId(optionList));
    } catch (error) {
      console.log(error);
    }
  };
  useEffect(() => {
    void fetchData();
  }, []);

  return (
    <div className="pr-16 mb-16">
      <ProductTitle />
      {pathName === 'edit' && <ProductForm datas={datas} pathName={pathName} />}
    </div>
  );
}

import { useLocation } from 'react-router-dom';
import ProductForm from 'Components/LSM/Product/ProductForm/ProductForm';
import ProductTitle from 'Components/LSM/Product/ProductTitle';

export default function ProductPost() {
  const pathName = useLocation().pathname.split('/').slice(3)[0];

  return (
    <div className="pr-16 mb-16">
      <ProductTitle />
      {pathName === 'post' && <ProductForm datas={null} pathName={pathName} />}
    </div>
  );
}

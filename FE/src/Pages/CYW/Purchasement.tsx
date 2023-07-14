import ProductImg from '../../Components/CYW/ProductDetail/ProductImg/ProductImg';
import ProductInformation from '../../Components/CYW/ProductDetail/ProductInformation/ProductInformation';
import Tab from '../../Components/CYW/ProductDetail/Tab/Tab';

export default function Purchasement() {
  return (
    <div className="pl-80 pt-10 w-screen">
      <p className="text-slate-600">{`카테고리 > 모니터`}</p>
      <div className="flex pt-10 justify-center w-3/4">
        <ProductImg />
        <ProductInformation />
      </div>
      <Tab />
    </div>
  );
}

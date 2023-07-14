import Coupon from './Coupon';
import DeliveryInformation from './DeliveryInformation';
import OptionChoice from './OptionChoice';
import OptionResult from './OptionResult';
import ProductName from './ProductName';
import Button from './Button';
import Price from './Price';

export default function ProductInformation() {
  return (
    <div className="w-1/2 pt-5 pl-10">
      <ProductName />
      <Price />
      <DeliveryInformation />
      <Coupon />
      <OptionChoice />
      <OptionResult />
      <Button />
    </div>
  );
}

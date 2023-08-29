// import Coupon from './Coupon';
import DeliveryInformation from './DeliveryInformation';
import OptionChoice from './OptionChoice';
import ProductResult from './ProductResult';
import ProductName from './ProductName';
import Button from './Button';
import Price from './Price';
import type { ProductType } from 'Pages/CYW/ProductDetail';

interface ProductTypeProps {
  product: ProductType;
}

export default function ProductInformation({ product }: ProductTypeProps) {
  return (
    <div className="w-1/4 pt-5 pl-10">
      <ProductName name={product.name} />
      <Price price={product.itemPrice} />
      <DeliveryInformation
        delivery={product.deliveryPrice}
        price={product.itemPrice}
      />
      <OptionChoice />
      <ProductResult name={product.name} price={product.itemPrice} />
      <Button />
    </div>
  );
}

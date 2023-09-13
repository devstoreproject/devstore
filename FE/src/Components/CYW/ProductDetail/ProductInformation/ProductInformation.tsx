import DeliveryInformation from './DeliveryInformation';
import OptionChoice from './OptionChoice';
import SelectedResult from './SelectedResult';
import ProductName from './ProductName';
import Button from './Button';
import Price from './Price';
import type { ProductType } from 'Pages/CYW/ProductDetail';
import { useState } from 'react';

interface ProductTypeProps {
  product: ProductType;
}

export default function ProductInformation({ product }: ProductTypeProps) {
  const [productCount, setProductCount] = useState<number>(1);
  const [selectedValue, setSelectedValue] = useState<number>(0);
  console.log(selectedValue);
  return (
    <div className="w-1/4 pt-5 pl-10">
      <ProductName name={product.name} />
      <Price price={product.itemPrice} />
      <DeliveryInformation
        delivery={product.deliveryPrice}
        price={product.itemPrice}
      />
      <OptionChoice
        option={product.optionList}
        selectedValue={selectedValue}
        setSelectedValue={setSelectedValue}
      />
      <SelectedResult
        name={product.name}
        price={product.itemPrice}
        option={product.optionList}
        selectedValue={selectedValue}
        productCount={productCount}
        setProductCount={setProductCount}
      />
      <Button
        product={product}
        productCount={productCount}
        option={product.optionList}
        selectedValue={selectedValue}
      />
    </div>
  );
}

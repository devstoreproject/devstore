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
  const [selectedOptionDetail, setSelectedOptionDetail] = useState<
    string | null
  >('');

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
        setSelectedOptionDetail={setSelectedOptionDetail}
      />
      {selectedValue === 0 ? null : (
        <SelectedResult
          name={product.name}
          price={product.itemPrice}
          productCount={productCount}
          setProductCount={setProductCount}
          selectedOptionDetail={selectedOptionDetail}
          setSelectedValue={setSelectedValue}
        />
      )}
      <Button
        product={product}
        productCount={productCount}
        option={product.optionList}
        selectedValue={selectedValue}
      />
    </div>
  );
}

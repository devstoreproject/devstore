import ProductDetailTitle from './ProductDetailTitle';
import ProductDetailContents from './ProductDetailContents';
import useFetchProductDetail from 'hooks/admin/product/useFetchProductDetail';

interface productDetail {
  productId: number;
}

export default function ProductDetail({ productId }: productDetail) {
  const product = useFetchProductDetail(productId);
  return (
    <div className="absolute z-10 flex pt-2.5 pl-4 bg-gray-300 rounded-lg w-144 h-104 top-24 left-52">
      <ProductDetailTitle />
      <ProductDetailContents product={product} />
    </div>
  );
}

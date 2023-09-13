import ProductDetailTitle from './ProductDetailTitle';
import ProductDetailContents from './ProductDetailContents';
import useFetchProductDetail from 'hooks/admin/product/useFetchProductDetail';
import CloseBtn from './CloseBtn';

interface productDetail {
  productId: number;
  setIsDetailModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function ProductDetail({
  productId,
  setIsDetailModalOpen,
}: productDetail) {
  const product = useFetchProductDetail(productId);
  return (
    <div className="absolute z-10 flex flex-col pt-2.5 pl-8 bg-gray-300 rounded-lg w-200 top-14 left-52">
      <CloseBtn setIsDetailModalOpen={setIsDetailModalOpen} />
      <div className="flex mb-6">
        <ProductDetailTitle />
        <ProductDetailContents product={product} />
      </div>
    </div>
  );
}

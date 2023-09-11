import ProductDetailTitle from './ProductDetailTitle';
import ProductDetailContents from './ProductDetailContents';
import useFetchProductDetail from 'hooks/admin/product/useFetchProductDetail';
import { CgCloseR } from 'react-icons/cg';

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
      <div className="flex justify-end mb-2 mr-3">
        <button
          onClick={() => {
            setIsDetailModalOpen(false);
          }}
        >
          <CgCloseR className="w-6 h-6 text-gray-600 hover:text-black" />
        </button>
      </div>
      <div className="flex">
        <ProductDetailTitle />
        <ProductDetailContents product={product} />
      </div>
    </div>
  );
}

import type { Product } from 'model/product';
import TableContents from './TableContents';
import TableTitle from './TableTitle';

interface OwnProps {
  page: number;
  products: Product[];
  isDetailModalOpen: boolean;
  setIsDetailModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
  setProductId: React.Dispatch<React.SetStateAction<number>>;
  setCheckedId: React.Dispatch<React.SetStateAction<number[]>>;
}

export default function Table({
  products,
  isDetailModalOpen,
  setIsDetailModalOpen,
  setProductId,
  setCheckedId,
  page,
}: OwnProps) {
  return (
    <div className="bg-gray-100 border border-gray-400 rounded-t-lg w-300 h-132.8">
      {isDetailModalOpen ? (
        <div className="absolute bg-black border border-black rounded-t-lg opacity-50 w-300 h-132.8" />
      ) : null}
      <TableTitle />
      <ul>
        {products.map((product, idx) => {
          return (
            <TableContents
              idx={idx}
              key={product.itemId}
              itemId={product.itemId}
              name={product.name}
              totalCount={product.totalCount}
              optionList={product.optionList}
              itemPrice={product.itemPrice}
              salesQuantity={product.salesQuantity}
              setIsDetailModalOpen={setIsDetailModalOpen}
              setProductId={setProductId}
              setCheckedId={setCheckedId}
              page={page}
            />
          );
        })}
      </ul>
    </div>
  );
}

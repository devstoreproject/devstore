import type { Product } from 'model/auth';
import TableContents from './TableContents';
import TableTitle from './TableTitle';

interface OwnProps {
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
}: OwnProps) {
  return (
    <div className="bg-gray-100 border border-gray-400 rounded-t-lg w-300 h-128">
      {isDetailModalOpen ? (
        <div className="absolute bg-black border border-black rounded-t-lg opacity-50 w-300 h-128" />
      ) : null}
      <TableTitle />
      <ul>
        {products.map((product) => {
          return (
            <TableContents
              key={product.itemId}
              itemId={product.itemId}
              name={product.name}
              totalCount={product.totalCount}
              itemPrice={product.itemPrice}
              setIsDetailModalOpen={setIsDetailModalOpen}
              setProductId={setProductId}
              setCheckedId={setCheckedId}
            />
          );
        })}
      </ul>
    </div>
  );
}

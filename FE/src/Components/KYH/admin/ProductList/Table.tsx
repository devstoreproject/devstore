import type { Product } from 'model/auth';
import TableContents from './TableContents';
import TableTitle from './TableTitle';

interface OwnProps {
  products: Product[];
}

export default function Table({ products }: OwnProps) {
  return (
    <div className="bg-gray-100 border border-gray-400 rounded-t-lg w-300 h-128">
      <TableTitle />
      <ul>
        {products.map((product) => {
          return (
            <TableContents
              key={product.itemId}
              itemId={product.itemId}
              name={product.name}
              defaultCount={product.defaultCount}
              totalCount={product.totalCount}
              itemPrice={product.itemPrice}
            />
          );
        })}
      </ul>
    </div>
  );
}

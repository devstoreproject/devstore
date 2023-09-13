import type { ProductType } from 'Pages/CYW/ProductDetail';

interface OwnProps {
  product: ProductType | null;
}

export default function SlideImgItem({ product }: OwnProps) {
  return (
    <div>
      <img
        className="box-border rounded-md border-slate-500 border-2 w-16 h-16 object-cover"
        src={product?.imageList[0].thumbnailPath}
      />
    </div>
  );
}

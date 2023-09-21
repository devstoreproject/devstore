import TitleImg from './TitleImg';
import SlideImg from './SlideImg';
import type { ProductType } from 'Pages/CYW/ProductDetail';

interface OwnProps {
  product: ProductType | null;
}

export default function ProductImg({ product }: OwnProps) {
  const imgArrJudge: number | undefined = product?.imageList.length;

  return (
    <div className="flex flex-col justify-center w-1/4">
      <TitleImg product={product} imgArrJudge={imgArrJudge} />
      {imgArrJudge !== undefined && imgArrJudge > 0 ? (
        <SlideImg product={product} />
      ) : null}
    </div>
  );
}

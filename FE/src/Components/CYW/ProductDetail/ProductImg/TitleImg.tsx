import type { ProductType } from 'Pages/CYW/ProductDetail';
import { LuImageOff } from 'react-icons/lu';

interface OwnProps {
  product: ProductType | null;
  imgArrJudge: number | undefined;
}

export default function TitleImg({ product, imgArrJudge }: OwnProps) {
  const iconSize: number = 100;

  return (
    <div className="flex justify-center items-center">
      {imgArrJudge !== undefined && imgArrJudge > 0 ? (
        <img
          className="object-cover w-full rounded-xl"
          src={product?.imageList[0].thumbnailPath}
        />
      ) : (
        <LuImageOff
          size={iconSize}
          style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
          }}
        />
      )}
    </div>
  );
}

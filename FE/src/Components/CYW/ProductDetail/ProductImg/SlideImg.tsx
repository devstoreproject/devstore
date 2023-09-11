import {
  MdOutlineKeyboardArrowLeft,
  MdOutlineKeyboardArrowRight,
} from 'react-icons/md';
import SlideImgItem from './SlideImgItem';
import type { ProductType } from 'Pages/CYW/ProductDetail';

interface OwnProps {
  product: ProductType | null;
}

export default function SlideImg({ product }: OwnProps) {
  const btnSize = 20;
  return (
    <div className="flex justify-center items-center pt-4">
      <button>
        <MdOutlineKeyboardArrowLeft size={btnSize} />
      </button>
      <SlideImgItem product={product} />
      <button>
        <MdOutlineKeyboardArrowRight size={btnSize} />
      </button>
    </div>
  );
}

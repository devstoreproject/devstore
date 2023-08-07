import {
  MdOutlineKeyboardArrowLeft,
  MdOutlineKeyboardArrowRight,
} from 'react-icons/md';
import SlideImgItem from './SlideImgItem';

export default function SlideImg() {
  const btnSize = 20;
  return (
    <div className="flex justify-center items-center pt-4">
      <button>
        <MdOutlineKeyboardArrowLeft size={btnSize} />
      </button>
      <SlideImgItem />
      <button>
        <MdOutlineKeyboardArrowRight size={btnSize} />
      </button>
    </div>
  );
}

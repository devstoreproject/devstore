import {
  MdOutlineKeyboardArrowLeft,
  MdOutlineKeyboardArrowRight,
} from 'react-icons/md';
import SlideImgItem from './SlideImgItem';

export default function SlideImg() {
  return (
    <div className="flex justify-center items-center pt-4">
      <MdOutlineKeyboardArrowLeft />
      <SlideImgItem />
      <MdOutlineKeyboardArrowRight />
    </div>
  );
}

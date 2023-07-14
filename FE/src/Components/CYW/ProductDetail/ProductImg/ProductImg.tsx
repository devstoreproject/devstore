import TitleImg from './TitleImg';
import SlideImg from './SlideImg';

export default function ProductImg() {
  return (
    <div className="flex flex-col justify-center w-1/2">
      <TitleImg />
      <SlideImg />
    </div>
  );
}

import Button from './Button';
import ContentsInput from './ContentsInput';
import ImageInput from './ImageInput';
import OptionInput from './OptionInput';
import OrderInput from './OrderInput';
import TitleInput from './TitleInput';

export default function ProductForm() {
  return (
    <form className="w-full">
      <div className="w-175">
        <TitleInput />
      </div>
      <div className="w-200">
        <ImageInput />
        <OrderInput />
        <OptionInput />
      </div>
      <ContentsInput />
      <Button />
    </form>
  );
}

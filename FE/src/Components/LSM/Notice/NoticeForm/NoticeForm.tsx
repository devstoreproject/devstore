import Button from './Button';
import ImageInput from './ImageInput';
import TitleInput from './TitleInput';
import Select from './Select';
import Textarea from './Textarea';

export default function NoticeForm() {
  return (
    <form className="flex flex-col">
      <div className="flex items-center mb-4">
        <Select />
        <TitleInput />
      </div>
      <Textarea />
      <ImageInput />
      <Button />
    </form>
  );
}

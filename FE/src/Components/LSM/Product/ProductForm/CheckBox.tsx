import { BsCheck } from 'react-icons/bs';

export default function CheckBox() {
  return (
    <div className="relative flex items-center justify-between">
      <div className="absolute top-4.5 left-4 text-white">
        <BsCheck />
      </div>
      <input
        id="check"
        type="checkbox"
        className="w-4 h-4 ml-4 mr-2 border rounded-full appearance-none border-label-gray checked:bg-label-gray"
      />
      <label htmlFor="check" className="w-20 text-label-gray">
        무료배송
      </label>
    </div>
  );
}

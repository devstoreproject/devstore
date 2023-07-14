import {
  MdKeyboardArrowLeft,
  MdKeyboardArrowRight,
  MdKeyboardDoubleArrowLeft,
  MdKeyboardDoubleArrowRight,
} from 'react-icons/md';

export default function Pagination() {
  return (
    <div className="flex py-4 border-t-2 items-center justify-center">
      <MdKeyboardDoubleArrowLeft size={20} />
      <MdKeyboardArrowLeft size={20} />
      <button className="text-lg">&nbsp;1&nbsp;</button>
      <button className="text-lg">&nbsp;2&nbsp;</button>
      <button className="text-lg">&nbsp;3&nbsp;</button>
      <button className="text-lg">&nbsp;4</button>
      <MdKeyboardArrowRight size={20} />
      <MdKeyboardDoubleArrowRight size={20} />
    </div>
  );
}

import { AiOutlineEdit, AiOutlineDelete } from 'react-icons/ai';

export default function Icon() {
  return (
    <div className="absolute top-0 right-0 flex items-center text-2xl">
      <AiOutlineEdit className="mr-2 cursor-pointer" />
      <AiOutlineDelete className="cursor-pointer" />
    </div>
  );
}

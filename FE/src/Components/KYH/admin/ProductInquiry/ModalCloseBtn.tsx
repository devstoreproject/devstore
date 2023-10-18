import { FaRegWindowClose } from 'react-icons/fa';

interface OwnProps {
  setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function ModalCloseBtn({ setIsModalOpen }: OwnProps) {
  return (
    <label className="flex justify-end mt-2 mr-2">
      <input
        type="button"
        className="hidden"
        onClick={() => {
          setIsModalOpen(false);
        }}
      />
      <FaRegWindowClose className="w-6 h-6 text-gray-700 cursor-pointer hover:text-black" />
    </label>
  );
}

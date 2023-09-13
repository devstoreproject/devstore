import { CgCloseR } from 'react-icons/cg';

interface OwnProps {
  setIsDetailModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function CloseBtn({ setIsDetailModalOpen }: OwnProps) {
  return (
    <div className="flex justify-end mr-3">
      <button
        onClick={() => {
          setIsDetailModalOpen(false);
        }}
      >
        <CgCloseR className="w-5 h-5 text-gray-600 hover:text-black" />
      </button>
    </div>
  );
}

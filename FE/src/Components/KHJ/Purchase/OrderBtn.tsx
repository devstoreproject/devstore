import ModalBasic from '../Common/ModalBasic';
import CancelModal from '../Modal/CancelModal';
import ReturnModal from '../Modal/ReturnModal';

interface OwnProps {
  btnName: string;
  modalType?: string;
  isOpen?: boolean;
  setIsOpen?: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function Button({
  btnName,
  isOpen,
  setIsOpen,
  modalType,
}: OwnProps): React.ReactElement {
  const modalChoice = () => {
    if (modalType === 'return') {
      return <ReturnModal />;
    }
    if (modalType === 'cancel') {
      return <CancelModal />;
    }
    return null;
  };

  const handleOpen = () => {
    if (setIsOpen !== null && setIsOpen !== undefined) {
      setIsOpen(true);
    }
  };

  return (
    <>
      <button
        className={`h-14 w-32 rounded-full border 
          ${
            btnName !== '리뷰작성'
              ? 'border-gray-300'
              : 'bg-light-black text-white'
          }
        `}
        onClick={handleOpen}
      >
        {btnName}
      </button>
      {isOpen !== null && (
        <ModalBasic
          childComponent={modalChoice}
          isOpen={isOpen}
          setIsOpen={setIsOpen}
        />
      )}
    </>
  );
}

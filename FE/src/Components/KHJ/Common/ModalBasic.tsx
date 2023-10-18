import React from 'react';

interface OwnComponentProps {
  childComponent: React.FC;
  isOpen?: boolean;
  setIsOpen?: React.Dispatch<React.SetStateAction<boolean>>;
}

const ModalBasic: React.FC<OwnComponentProps> = ({
  childComponent: Child,
  isOpen,
  setIsOpen,
}) => {
  const handleClose = () => {
    if (setIsOpen !== null && setIsOpen !== undefined) {
      setIsOpen(false);
    }
  };
  return (
    <section
      className={`fixed w-full h-full left-0 top-0 flex items-center justify-center ${
        isOpen === true ? '' : ' hidden'
      }`}
    >
      <div className="w-11/12 h-4/5 bg-white z-30 rounded-xl overflow-y-auto">
        <Child />
      </div>
      <div
        className="absolute w-full h-full bg-black opacity-40"
        onClick={handleClose}
      ></div>
    </section>
  );
};

export default ModalBasic;

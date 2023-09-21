import { useDispatch } from 'react-redux';
import { setCurrentIndex } from 'store/modules/setCurrentIndex';

interface OwnProps {
  value: string;
  currentAddress: boolean;
  setCurrentAddress: React.Dispatch<React.SetStateAction<boolean[]>>;
  idx: number;
}

export default function AddressSelectButton({
  value,
  currentAddress,
  setCurrentAddress,
  idx,
}: OwnProps) {
  const dispatch = useDispatch();
  const ButtonClickHandler = () => {
    setCurrentAddress((prev) =>
      prev.map((e, i) => {
        if (i === idx) {
          return true;
        } else {
          return false;
        }
      })
    );
    dispatch(setCurrentIndex(idx));
  };

  return (
    <input
      type="button"
      value={value}
      className={`h-10 px-8 mb-4 border border-gray-600 rounded-md cursor-pointer ${
        currentAddress ? 'text-gray-100 bg-black' : ''
      }`}
      onClick={ButtonClickHandler}
    />
  );
}

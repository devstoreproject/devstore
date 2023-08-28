import { useState } from 'react';
import AddressSelectButton from './AddressSelectButton';

export default function AddressSelectButtonList() {
  const [currentAddress, setCurrentAddress] = useState([true, false, false]);
  return (
    <div className="flex justify-between">
      {SelectButton.map((btn, idx) => {
        return (
          <AddressSelectButton
            key={idx}
            value={btn.value}
            currentAddress={currentAddress[idx]}
            setCurrentAddress={setCurrentAddress}
            idx={idx}
          />
        );
      })}
    </div>
  );
}

const SelectButton = [
  {
    value: '기본 주소',
  },
  {
    value: '추가 주소',
  },
  {
    value: '추가 주소',
  },
];

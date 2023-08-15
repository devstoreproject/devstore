import DaumPostcode from 'react-daum-postcode';
import Address from './Address';
import { useState } from 'react';

interface addressContainerProps {
  address: string;
  setAddress: React.Dispatch<React.SetStateAction<string>>;
  addressDetail: string;
  setAddressDetail: React.Dispatch<React.SetStateAction<string>>;
  zipCode: string;
  setZipCode: React.Dispatch<React.SetStateAction<string>>;
}

export default function AddressContainer({
  address,
  setAddress,
  addressDetail,
  setAddressDetail,
  zipCode,
  setZipCode,
}: addressContainerProps) {
  const [isOpenPost, setIsOpenPost] = useState(false);

  const onCompletePost = (data: any) => {
    setZipCode(data.zonecode);
    setAddress(data.address);
    setIsOpenPost(false);
  };
  return (
    <div>
      {isOpenPost ? (
        <div className="absolute top-0 left-0 flex flex-col w-full h-full bg-black opacity-90 rounded-xl" />
      ) : null}
      <Address
        setIsOpenPost={setIsOpenPost}
        addressDetail={addressDetail}
        setAddressDetail={setAddressDetail}
        address={address}
        zipCode={zipCode}
      />
      {isOpenPost ? (
        <div className="absolute top-96 w-120">
          <DaumPostcode onComplete={onCompletePost} autoClose={false} />
        </div>
      ) : null}
    </div>
  );
}

import AddressContainer from './AddressContainer';
import DeliveryOptionsContainer from './DeliveryOptionsContainer';
import EmailContainer from './EmailContainer';
import LandlinePhoneNumberContainer from './LandlinePhoneNumberContainer';
import NameContainer from './NameContainer';
import NicknameContainer from './NicknameContainer';
import PasswordEditContainer from './PasswordEditContainer';
import PhoneNumberContainer from './PhoneNumberContainer';

export default function UserInfo() {
  return (
    <div className="flex flex-col">
      <NicknameContainer />
      <EmailContainer />
      <PasswordEditContainer />
      <NameContainer />
      <PhoneNumberContainer />
      <LandlinePhoneNumberContainer />
      <AddressContainer />
      <DeliveryOptionsContainer />
      <button className="self-center w-20 h-10 mt-4 text-white bg-black rounded-2xl">
        수정
      </button>
    </div>
  );
}

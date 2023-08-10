import AddressContainer from './AddressContainer';
import DeliveryOptionsContainer from './DeliveryOptionsContainer';
import EmailContainer from './EmailContainer';
import LandlinePhoneNumberContainer from './LandlinePhoneNumberContainer';
import NameContainer from './NameContainer';
import NicknameContainer from './NicknameContainer';
import PasswordEditContainer from './PasswordEditContainer';
import PhoneNumberContainer from './PhoneNumberContainer';
import useFetchProfile from 'hooks/mypage/useFetchProfile';

export default function UserInfo() {
  const profile = useFetchProfile();
  console.log(profile);
  return (
    <div className="flex flex-col">
      <NicknameContainer nickname={profile.nickname} />
      <EmailContainer />
      <PasswordEditContainer />
      <NameContainer />
      <PhoneNumberContainer />
      <LandlinePhoneNumberContainer />
      <AddressContainer />
      <DeliveryOptionsContainer />
      <button className="w-20 h-10 mt-4 text-white bg-black ml-60 rounded-2xl">
        수정
      </button>
    </div>
  );
}

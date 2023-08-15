import { useEffect, useState } from 'react';
import AddressContainer from './AddressContainer';
import DeliveryOptionsContainer from './DeliveryOptionsContainer';
import EmailContainer from './EmailContainer';
import LandlinePhoneNumberContainer from './LandlinePhoneNumberContainer';
import NameContainer from './NameContainer';
import NicknameContainer from './NicknameContainer';
import PhoneNumberContainer from './PhoneNumberContainer';
import useFetchProfile from 'hooks/mypage/useFetchProfile';
import {
  ValidateNickname,
  ValidatePassword,
  ValidatePhone,
  ValidateUserName,
} from 'utils/auth/authValidate';
import PasswordContainer from './PasswordContainer';
import PasswordConfirmContainer from './PasswordConfirmContainer';
import api from 'api';

export default function UserInfo() {
  const profile = useFetchProfile();
  const userId = localStorage.getItem('userId');
  const [nickname, setNickname] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [userName, setUserName] = useState('');
  const [phonePrefix, setPhonePrefix] = useState('');
  const [phone, setPhone] = useState('');
  const [address, setAddress] = useState('');
  const [addressDetail, setAddressDetail] = useState('');
  const [zipCode, setZipCode] = useState('');

  const [isNicknameValid, setIsNicknameValid] = useState(true);
  const [isPasswordValid, setIsPasswordValid] = useState(true);
  const [isUserNameValid, setIsUserNameValid] = useState(true);
  const [isPhoneValid, setIsPhoneValid] = useState(true);
  const [isNicknameDuplicate, setIsNicknameDuplicate] = useState(true);

  useEffect(() => {
    setNickname(profile.nickname);
    setPhonePrefix(profile.phone.slice(0, 3));
    setPhone(profile.phone.slice(3));
  }, [profile]);

  const patchUserInfo = (e: React.FormEvent) => {
    e.preventDefault();

    if (!ValidateNickname(nickname, setIsNicknameValid)) return;
    if (password !== '' && !ValidatePassword(password, setIsPasswordValid))
      return;
    if (password !== passwordConfirm) return;
    if (userName !== '' && !ValidateUserName(userName, setIsUserNameValid))
      return;
    if (!ValidatePhone(`${phonePrefix}${phone}`, setIsPhoneValid)) return;
    if (!isNicknameDuplicate) return;

    const userInfo = {
      nickname,
      phone: phonePrefix + phone,
    };

    console.log(userInfo);

    const formData = new FormData();
    const blob = new Blob([JSON.stringify(userInfo)], {
      type: 'application/json',
    });

    formData.append('patch', blob);

    if (userId !== null) {
      api
        .patch(`/api/users/${userId}`, formData)
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  return (
    <form className="flex flex-col" onSubmit={patchUserInfo}>
      <NicknameContainer
        nickname={nickname}
        setNickname={setNickname}
        isNicknameValid={isNicknameValid}
        isNicknameDuplicate={isNicknameDuplicate}
        setIsNicknameDuplicate={setIsNicknameDuplicate}
      />
      <EmailContainer email={profile.email} />
      <PasswordContainer
        password={password}
        setPassword={setPassword}
        isPasswordValid={isPasswordValid}
      />
      <PasswordConfirmContainer
        password={password}
        passwordConfirm={passwordConfirm}
        setPasswordConfirm={setPasswordConfirm}
      />
      <NameContainer
        userName={userName}
        setUserName={setUserName}
        isUserNameValid={isUserNameValid}
      />
      <PhoneNumberContainer
        phone={phone}
        setPhone={setPhone}
        isPhoneValid={isPhoneValid}
        phonePrefix={phonePrefix}
        setPhonePrefix={setPhonePrefix}
      />
      <LandlinePhoneNumberContainer />
      <AddressContainer
        address={address}
        setAddress={setAddress}
        addressDetail={addressDetail}
        setAddressDetail={setAddressDetail}
        zipCode={zipCode}
        setZipCode={setZipCode}
      />
      <DeliveryOptionsContainer />
      <button
        className="w-20 h-10 mt-4 text-white bg-black ml-60 rounded-2xl"
        type="submit"
      >
        수정
      </button>
    </form>
  );
}

import { useEffect, useState } from 'react';
import AddressContainer from './AddressContainer';
import DeliveryOptionsContainer from './DeliveryOptionsContainer';
import EmailContainer from './EmailContainer';
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
import fetchUserDataEdit from 'utils/mypage/fetchUserDataEdit';
import { fetchPatchAddress, fetchPostAddress } from 'utils/mypage/fetchAddress';
import useFetchAddress from 'hooks/mypage/useFetchAddress';
import { useSelector } from 'react-redux';

export default function UserInfo() {
  const profile = useFetchProfile();
  const currentIdx = useSelector((state: number) => state);
  const address = useFetchAddress();

  const [nickname, setNickname] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [username, setUsername] = useState('');
  const [phonePrefix, setPhonePrefix] = useState('');
  const [phone, setPhone] = useState('');
  const [addressDetail, setAddressDetail] = useState('');
  const [addressSimple, setAddressSimple] = useState('');
  const [zipCode, setZipCode] = useState('');

  const [isNicknameValid, setIsNicknameValid] = useState(true);
  const [isPasswordValid, setIsPasswordValid] = useState(true);
  const [isUserNameValid, setIsUserNameValid] = useState(true);
  const [isPhoneValid, setIsPhoneValid] = useState(true);
  const [isNicknameDuplicate, setIsNicknameDuplicate] = useState(true);

  useEffect(() => {
    setUsername(profile.username);
    setNickname(profile.nickname);
    setPhonePrefix(profile.phone.slice(0, 3));
    setPhone(profile.phone.slice(3));
    setZipCode(address[currentIdx]?.zipCode);
    setAddressDetail(address[currentIdx]?.addressDetail);
    setAddressSimple(
      address[currentIdx]?.addressSimple === undefined
        ? ''
        : address[currentIdx].addressSimple
    );
  }, [profile, address, currentIdx]);

  const userInfoSubmitHandler = (e: React.FormEvent) => {
    e.preventDefault();

    const userInfo = {
      nickname,
      username,
      phone: phonePrefix + phone,
      password: password === '' ? null : password,
    };

    const newAddress = {
      recipient: profile.username,
      mobileNumber: `${phonePrefix}-${phone.slice(0, 4)}-${phone.slice(4)}`,
      zipCode,
      addressDetail,
      addressSimple,
    };

    if (!ValidateNickname(nickname, setIsNicknameValid)) return;
    if (password !== '' && !ValidatePassword(password, setIsPasswordValid))
      return;
    if (password !== passwordConfirm) return;
    if (username !== '' && !ValidateUserName(username, setIsUserNameValid))
      return;
    if (!ValidatePhone(`${phonePrefix}${phone}`, setIsPhoneValid)) return;
    if (!isNicknameDuplicate) return;

    fetchUserDataEdit(userInfo);

    if (zipCode !== undefined) {
      if (address[currentIdx] === undefined) {
        fetchPostAddress(newAddress);
      } else {
        if (
          newAddress.zipCode === address[currentIdx].zipCode &&
          newAddress.addressDetail === address[currentIdx].addressDetail &&
          newAddress.addressSimple === address[currentIdx].addressSimple
        )
          return;
        fetchPatchAddress(newAddress, currentIdx);
      }
    }
  };

  return (
    <form className="flex flex-col" onSubmit={userInfoSubmitHandler}>
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
        username={username}
        setUsername={setUsername}
        isUserNameValid={isUserNameValid}
      />
      <PhoneNumberContainer
        phone={phone}
        setPhone={setPhone}
        isPhoneValid={isPhoneValid}
        phonePrefix={phonePrefix}
        setPhonePrefix={setPhonePrefix}
      />
      <AddressContainer
        addressSimple={addressSimple}
        setAddressSimple={setAddressSimple}
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

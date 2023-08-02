import { validatePhone } from 'utils/auth/authValidate';

interface PhoneNumberContainerProps {
  phone: string;
  setPhone: React.Dispatch<React.SetStateAction<string>>;
  isPhoneValid: boolean;
  setIsPhoneValid: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function PhoneNumberContainer({
  phone,
  setPhone,
  isPhoneValid,
  setIsPhoneValid,
}: PhoneNumberContainerProps) {
  const PhoneNumberInputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPhone(e.target.value);
    setIsPhoneValid(validatePhone(e.target.value));
  };
  return (
    <div className="flex mt-2">
      <div className="flex flex-col w-full mr-2">
        <span className="ml-2">휴대전화번호</span>
        <input
          type="text"
          className="h-10 pl-2 mr-2 border border-black"
          placeholder="휴대전화번호를 입력해주세요(-제외)"
          value={phone}
          onChange={PhoneNumberInputHandler}
        ></input>
        {isPhoneValid ? null : (
          <span className="mt-1 ml-2 text-xs font-bold text-red-500">
            올바른 번호를 입력해주세요
          </span>
        )}
      </div>
    </div>
  );
}
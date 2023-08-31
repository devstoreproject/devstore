import type { Phone } from 'model/auth';

interface OwnProps extends Phone {
  phonePrefix: string;
  setPhonePrefix: React.Dispatch<React.SetStateAction<string>>;
}

export default function PhoneNumberContainer({
  phone,
  setPhone,
  isPhoneValid,
  phonePrefix,
  setPhonePrefix,
}: OwnProps) {
  return (
    <div className="flex items-center mb-4">
      <span className="w-32 text-gray-500">휴대전화</span>
      <input
        className="w-24 h-10 text-center border border-gray-300 text-md rounded-3xl"
        value={phonePrefix}
        onChange={(e) => {
          setPhonePrefix(e.target.value);
        }}
      />
      <span className="mx-3">-</span>
      <input
        type="text"
        className="w-64 h-10 pl-4 border border-gray-300 text-md rounded-3xl"
        value={phone}
        onChange={(e) => {
          setPhone(e.target.value);
        }}
      />
      {isPhoneValid !== undefined && isPhoneValid ? null : (
        <span className="mt-1 ml-2 text-xs font-bold text-red-500">
          올바른 번호를 입력해주세요
        </span>
      )}
    </div>
  );
}

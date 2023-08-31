import type { Phone } from 'model/auth';

export default function PhoneContainer({ phone, setPhone }: Phone) {
  return (
    <div className="flex flex-col">
      <span className="ml-6">휴대전화</span>
      <input
        type="text"
        className="h-8 pl-2 mx-4 border border-black"
        placeholder="전화번호를 입력해 주세요 (- 제외)"
        onChange={(e) => {
          setPhone(e.target.value);
        }}
        value={phone}
      />
    </div>
  );
}

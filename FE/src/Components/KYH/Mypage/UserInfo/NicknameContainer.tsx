import type { Nickname } from 'model/auth';
import fetchNicknameDuplicate from 'utils/auth/fetchNicknameDuplicate';

interface OwnProps extends Nickname {
  isNicknameDuplicate: boolean;
  setIsNicknameDuplicate: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function NicknameContainer({
  nickname,
  setNickname,
  isNicknameValid,
  isNicknameDuplicate,
  setIsNicknameDuplicate,
}: OwnProps) {
  return (
    <div className="flex items-center mb-4">
      <span className="flex w-32 text-gray-500">닉네임</span>
      <input
        type="text"
        className="h-10 pl-4 text-sm border border-gray-300 w-96 rounded-3xl"
        value={nickname}
        onChange={(e) => {
          setNickname(e.target.value);
        }}
      />
      <input
        type="button"
        className="h-10 px-6 ml-3 text-sm bg-gray-300 rounded-xl shadow-btn active:shadow-none active:ml-3.5 active:mt-6.5 duration-100"
        onClick={() => {
          fetchNicknameDuplicate(nickname, setIsNicknameDuplicate);
        }}
        value="중복확인"
      />
      <div className="flex flex-col">
        {isNicknameDuplicate ? null : (
          <span className="mt-1 ml-2 text-xs font-bold text-red-500">
            중복된 닉네임 입니다. 다른 닉네임을 사용해 주세요
          </span>
        )}
        {isNicknameValid ? null : (
          <span className="mt-1 ml-2 text-xs font-bold text-red-500">
            특수문자, 띄어쓰기를 제외한 한글, 영문, 숫자만 입력 가능합니다.
          </span>
        )}
      </div>
    </div>
  );
}

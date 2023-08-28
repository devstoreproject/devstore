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
    <div className="flex mt-2">
      <div className="flex flex-col w-4/6">
        <span className="ml-2">닉네임</span>
        <input
          type="text"
          className="h-10 pl-2 border border-black"
          placeholder="닉네임을 입력하세요"
          value={nickname}
          onChange={(e) => {
            setNickname(e.target.value);
          }}
        ></input>
        {isNicknameDuplicate ? null : (
          <span className="mt-1 ml-2 text-xs font-bold text-red-500">
            중복된 닉네임 입니다.
            <br />
            다른 닉네임을 사용해 주세요
          </span>
        )}
        {isNicknameValid ? null : (
          <span className="mt-1 ml-2 text-xs font-bold text-red-500">
            특수문자, 띄어쓰기를 제외한
            <br />
            한글, 영문, 숫자만 입력 가능합니다.
          </span>
        )}
      </div>
      <input
        type="button"
        className="h-10 px-6 mt-6 ml-3 text-sm bg-gray-300 rounded-xl shadow-btn active:shadow-none active:ml-3.5 active:mt-6.5 duration-100"
        onClick={() => {
          fetchNicknameDuplicate(nickname, setIsNicknameDuplicate);
        }}
        value="중복확인"
      />
    </div>
  );
}

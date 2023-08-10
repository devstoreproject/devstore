import useFetchProfile from 'hooks/mypage/useFetchProfile';
import { AiFillCamera } from 'react-icons/ai';

export default function ProfileContainer() {
  const profile = useFetchProfile();

  return (
    <div className="flex items-center mb-10 ml-28">
      <div className="relative w-20 h-20 mr-4 bg-white border border-black rounded-full">
        <img src={profile.profileImage === null ? '' : profile.profileImage} />
        <button className="absolute right-0 flex items-center justify-center w-5 h-5 text-xs text-white bg-gray-600 rounded-full bottom-1">
          <AiFillCamera />
        </button>
      </div>
      <div>
        <span className="font-bold">{profile.nickname}</span> 님 어서오세요!
      </div>
    </div>
  );
}

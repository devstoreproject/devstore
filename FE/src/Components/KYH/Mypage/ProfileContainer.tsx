import useFetchProfile from 'hooks/mypage/useFetchProfile';
import { AiFillCamera } from 'react-icons/ai';
import fetchProfileImage from 'utils/mypage/fetchProfileImage';

export default function ProfileContainer() {
  const profile = useFetchProfile();

  const saveImageHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files !== null) {
      const image = e.target.files[0];
      fetchProfileImage(image, profile);
    }
  };

  return (
    <div className="flex items-center mb-10 ml-28">
      <div className="relative w-32 h-32 mr-10 bg-white border border-black rounded-full">
        <img src={profile.profileImage === null ? '' : profile.profileImage} />
        <label>
          <input type="file" className="hidden" onChange={saveImageHandler} />
          <AiFillCamera className="absolute items-center justify-center w-8 h-8 text-gray-700 bg-white border border-gray-700 rounded-full cursor-pointer bottom-1 -right-2" />
        </label>
      </div>
      <div className="text-xl">
        <span className="text-2xl font-bold">{profile.nickname}</span> 님
        어서오세요!
      </div>
    </div>
  );
}

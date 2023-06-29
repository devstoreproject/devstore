import { AiFillCamera } from 'react-icons/ai';
import naverCircleIcon from 'Assets/naverCircleIcon.png';

export default function ProfileContainer() {
  return (
    <div className="flex items-center mb-10 ml-28">
      <div className="relative w-20 h-20 mr-4 bg-white border border-black rounded-full">
        <img src={naverCircleIcon} />
        <div className="absolute right-0 flex items-center justify-center w-5 h-5 text-xs text-white bg-gray-600 rounded-full bottom-1">
          <AiFillCamera />
        </div>
      </div>
      <div>
        <span className="font-bold">여수밤바다</span> 님 어서오세요!
      </div>
    </div>
  );
}

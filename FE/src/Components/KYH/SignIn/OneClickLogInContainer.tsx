import naverCircleIcon from 'Assets/naverCircleIcon.png';
import kakaoCircleIcon from 'Assets/kakaoCircleIcon.png';

export default function OneClickLogInContainer() {
  return (
    <>
      <div className="flex items-center justify-center w-full mt-8">
        <div className="w-1/3 mt-4 border-b-2 border-gray-500" />
        <span className="mx-2 mt-4 text-gray-500">간편로그인</span>
        <div className="w-1/3 mt-4 border-b-2 border-gray-500" />
      </div>
      <div className="flex items-center mt-2">
        <img src={naverCircleIcon} className="w-10 h-10 mr-4" />
        <img src={kakaoCircleIcon} className="w-12 h-12" />
        <a
          href={`${String(
            process.env.REACT_APP_BASE_URL
          )}api/oauth2/authorization/google`}
        ></a>
      </div>
    </>
  );
}

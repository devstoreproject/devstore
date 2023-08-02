import UserInfo from './UserInfo';
import TitleImg from '../../ProductImg/TitleImg';
import UserReview from './UserReview';

export default function ReviewElement() {
  return (
    <div className="flex items-center border-b-2 py-4 justify-between px-10">
      <div className="flex items-center justify-center mr-2">
        <UserInfo />
        <div className="w-28">
          <TitleImg />
        </div>
      </div>
      <UserReview />
    </div>
  );
}

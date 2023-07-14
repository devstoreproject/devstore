import AllReviewManagement from 'Components/CYW/RewiewManagement/AllReviewManagement';
import RegisteredReviewManagement from 'Components/CYW/RewiewManagement/RegisteredReviewManagement';
import { BiSolidCommentCheck } from 'react-icons/bi';

export default function ReviewManagement() {
  return (
    <div className="flex flex-col lg:w-full bg-light-gray">
      <div className="flex items-center">
        <BiSolidCommentCheck size={30} />
        <h1 className="pl-2 font-bold text-2xl">리뷰</h1>
      </div>
      <div className="pt-10">
        <h2 className="font-bold text-xl pb-5">등록된 리뷰 목록</h2>
        <RegisteredReviewManagement />
      </div>
      <div className="pt-10">
        <h2 className="font-bold text-xl pb-5">전체 리뷰 목록</h2>
        <AllReviewManagement />
      </div>
    </div>
  );
}

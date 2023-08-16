import { useLocation } from 'react-router-dom';
import NoticeForm from 'Components/LSM/Notice/NoticeForm/NoticeForm';
import NoticeTitle from 'Components/LSM/Notice/NoticeTitle';

export default function NoticePost() {
  const path = useLocation();
  const pathName = path.pathname.slice(14);

  return (
    <div className="w-250">
      <NoticeTitle />
      {pathName === 'post' && <NoticeForm datas={null} />}
    </div>
  );
}

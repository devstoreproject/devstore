import { useLocation } from 'react-router-dom';
import NoticeTitle from 'Components/LSM/Notice/NoticeTitle';
import TabList from 'Components/LSM/Notice/NoticeList/TabList';
import ContentsList from 'Components/LSM/Notice/NoticeList/ContentsList';

export default function NoticeList() {
  const path: string = useLocation().pathname.slice(8);
  const pathRender: boolean = path === '';
  return (
    <div className="mx-32 my-16">
      <NoticeTitle />
      {pathRender && <TabList />}
      {pathRender && <ContentsList />}
    </div>
  );
}

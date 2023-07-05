import NoticeTitle from 'Components/LSM/Notice/NoticeTitle';
import TabList from 'Components/LSM/Notice/NoticeList/TabList';
import ContentsList from 'Components/LSM/Notice/NoticeList/ContentsList';

export default function NoticeList() {
  return (
    <div className="m-auto my-16 max-w-1460">
      <NoticeTitle />
      <TabList />
      <ContentsList />
    </div>
  );
}

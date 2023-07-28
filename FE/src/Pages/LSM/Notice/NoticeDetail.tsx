import Contents from 'Components/LSM/Notice/NoticeDetail/Contents';
import Date from 'Components/LSM/Notice/NoticeDetail/Date';
import IconButton from 'Components/LSM/Notice/NoticeDetail/IconButton';
import ImageBox from 'Components/LSM/Notice/NoticeDetail/ImageBox';
import TabTitle from 'Components/LSM/Notice/NoticeDetail/TabTitle';
import Title from 'Components/LSM/Notice/NoticeDetail/Title';

export default function NoticeDetail() {
  return (
    <div className="flex flex-col items-center h-screen m-auto mx-32 my-16">
      <div className="relative flex flex-col text-center w-250">
        <TabTitle />
        <Title />
        <Date />
        <div className="m-auto w-250">
          <ImageBox />
          <Contents />
        </div>
        <IconButton />
      </div>
    </div>
  );
}
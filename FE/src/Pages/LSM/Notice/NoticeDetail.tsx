import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import Contents from 'Components/LSM/Notice/NoticeDetail/Contents';
import Date from 'Components/LSM/Notice/NoticeDetail/Date';
import IconButton from 'Components/LSM/Notice/NoticeDetail/IconButton';
import ImageBox from 'Components/LSM/Notice/NoticeDetail/ImageBox';
import TabTitle from 'Components/LSM/Notice/NoticeDetail/TabTitle';
import Title from 'Components/LSM/Notice/NoticeDetail/Title';
import api from 'api';

export default function NoticeDetail() {
  const [datas, setDatas] = useState({});
  const { id } = useParams() as { id: string };
  const fetchData = async () => {
    try {
      const res = await api.get(`/api/notices/${id}`);
      setDatas(res?.data?.data);
      console.log(res?.data?.data);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    void fetchData();
    window.scrollTo(0, 0);
  }, []);

  return (
    <div className="flex flex-col items-center w-full px-32 m-auto my-20">
      <div className="relative flex flex-col w-full text-center">
        <div className="flex flex-col items-center justify-between">
          <TabTitle datas={datas} />
          <Title datas={datas} />
          <Date datas={datas} />
        </div>
        <div className="m-auto w-250">
          <ImageBox datas={datas} />
          <Contents datas={datas} />
        </div>
        <IconButton datas={datas} />
      </div>
    </div>
  );
}

import { useState, useEffect } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import NoticeForm from 'Components/LSM/Notice/NoticeForm/NoticeForm';
import NoticeTitle from 'Components/LSM/Notice/NoticeTitle';
import api from 'api';

export default function NoticeEdit() {
  const path = useLocation().pathname.split('/').slice(3)[0];

  const [datas, setDatas] = useState({});
  const { id } = useParams() as { id: string };

  const fetchData = async () => {
    try {
      const res = await api.get(`/api/notices/${id}`);
      setDatas(res?.data?.data);
    } catch (error) {
      console.log(error);
    }
  };
  useEffect(() => {
    void fetchData();
  }, []);

  return (
    <div className="w-250">
      <NoticeTitle />
      {path === 'edit' && <NoticeForm datas={datas} path={path} />}
    </div>
  );
}

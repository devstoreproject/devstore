import { useState, useEffect } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import NoticeForm from 'Components/LSM/Notice/NoticeForm/NoticeForm';
import NoticeTitle from 'Components/LSM/Notice/NoticeTitle';
import api from 'api';

export default function NoticeEdit() {
  const [datas, setDatas] = useState<any[]>([]);

  const { id } = useParams() as { id: string };

  const path = useLocation();
  const pathName = path.pathname.slice(14);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await api.get(`/api/notices/${id}`);
        console.log(res);
        setDatas(res.data.data);
      } catch (error) {
        console.log(error);
      }
    };

    if (id !== undefined) {
      void fetchData();
    }
  }, [id]);

  return (
    <div className="w-250">
      <NoticeTitle />
      {pathName === 'edit' && <NoticeForm datas={datas} />}
    </div>
  );
}

import { useState, useEffect } from 'react';
import NoticeTitle from 'Components/LSM/Notice/NoticeTitle';
import TabList from 'Components/LSM/Notice/NoticeList/TabList';
import ContentsList from 'Components/LSM/Notice/NoticeList/ContentsList';
import api from 'api';

export default function NoticeList() {
  const tabList = [
    { id: 1, title: '전체' },
    { id: 2, title: '운영정책' },
    { id: 3, title: '업데이트' },
    { id: 4, title: '이벤트' },
  ];

  const [datas, setDatas] = useState<any[]>([]);
  const [activeTab, setActiveTab] = useState<number | null>(tabList[0]?.id);

  const fetchData = async (tabId: number | null) => {
    try {
      let res;
      if (tabId === 2) {
        res = await api.get('/api/notices?page=0&size=1&category=OPERATING');
      } else if (tabId === 3) {
        res = await api.get('/api/notices?page=0&size=1&category=UPDATE');
      } else if (tabId === 4) {
        res = await api.get('/api/notices?page=0&size=1&category=EVENT');
      } else {
        const operatingRes = await api.get(
          '/api/notices?page=0&size=1&category=OPERATING'
        );
        const updateRes = await api.get(
          '/api/notices?page=0&size=1&category=UPDATE'
        );
        const eventRes = await api.get(
          '/api/notices?page=0&size=1&category=EVENT'
        );

        const allData = [
          ...operatingRes.data.data.content,
          ...updateRes.data.data.content,
          ...eventRes.data.data.content,
        ];
        allData.sort(
          (a, b) =>
            new Date(b.createAt).getTime() - new Date(a.createAt).getTime()
        );
        res = { data: { data: { content: allData } } };
      }

      setDatas(res?.data.data.content);
      console.log(res?.data.data.content);
    } catch (error) {
      console.log(error);
    }
  };

  const handleTabClick = (tabId: number) => {
    setActiveTab(tabId);
    void fetchData(tabId);
  };

  useEffect(() => {
    void fetchData(tabList[0]?.id);
  }, []);

  return (
    <div className="m-auto my-16 max-w-1460">
      <NoticeTitle />
      <TabList
        tabList={tabList}
        activeTab={activeTab}
        onTabClick={handleTabClick}
      />
      <ContentsList tabList={tabList} datas={datas} />
    </div>
  );
}

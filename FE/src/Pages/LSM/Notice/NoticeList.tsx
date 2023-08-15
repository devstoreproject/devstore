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
        res = await api.get(
          '/api/notices?category=OPERATING&page=0&size=20&sort=id%2Cdesc'
        );
      } else if (tabId === 3) {
        res = await api.get(
          '/api/notices?category=UPDATE&page=0&size=20&sort=id%2Cdesc'
        );
      } else if (tabId === 4) {
        res = await api.get(
          '/api/notices?category=EVENT&page=0&size=20&sort=id%2Cdesc'
        );
      } else {
        res = await api.get(
          '/api/notices?page=0&size=10&sort=id,desc&sort=createdAt,desc'
        );
      }

      setDatas(res?.data.data.content);
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

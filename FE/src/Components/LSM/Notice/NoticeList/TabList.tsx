import TabListItem from './TabListItem';

export default function TabList() {
  const tabList = [
    { id: 1, title: '전체' },
    { id: 2, title: '운영정책' },
    { id: 3, title: '업데이트' },
    { id: 4, title: '이벤트' },
  ];
  return (
    <ul className="flex">
      {tabList.map((tab) => (
        <TabListItem key={tab.id} title={tab.title} />
      ))}
    </ul>
  );
}

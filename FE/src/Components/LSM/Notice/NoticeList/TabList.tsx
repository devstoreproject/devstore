import TabListItem from './TabListItem';

interface NoticeListProp {
  tabList: Array<{ id: number; title: string }>;
  activeTab: number | null;
  onTabClick: (tabId: number) => void;
}

export default function TabList({
  tabList,
  activeTab,
  onTabClick,
}: NoticeListProp) {
  return (
    <div className="flex">
      {tabList.map((tab) => (
        <TabListItem
          key={tab.id}
          title={tab.title}
          active={tab.id === activeTab}
          onClick={() => {
            onTabClick(tab.id);
          }}
        />
      ))}
    </div>
  );
}

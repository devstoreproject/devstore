import TabListItem from './TabListItem';

interface NoticeListProp {
  tabList: Array<{ id: number; title: string }>;
  activeTab: number | null;
  onTabClick: (tabId: number) => void;
  adminPath: string;
}

export default function TabList({
  tabList,
  activeTab,
  onTabClick,
  adminPath,
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
          adminPath={adminPath}
        />
      ))}
    </div>
  );
}

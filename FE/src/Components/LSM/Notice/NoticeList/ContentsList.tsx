import ContentsItem from './ContentsItem';

interface NoticeListProp {
  tabList: Array<{ id: number; title: string }>;
  datas: Array<{
    key: number;
    category: any;
    content: string;
    createAt: string;
    modifiedAt: string;
    noticeId: number;
    title: string;
    viewCount: number;
    image: {
      imageId: number;
      originalPath: string;
      thumbnailPath: string;
    };
  }>;
  adminPath: string;
}

export default function ContentsList({
  tabList,
  datas,
  adminPath,
}: NoticeListProp) {
  return (
    <div
      className={`flex ${
        adminPath === 'admin' ? 'flex-wrap' : 'flex-wrap'
      }  items-center  w-full`}
    >
      {datas?.map((data, idx) => (
        <ContentsItem
          classnames="ml-0"
          key={data.noticeId}
          tabList={tabList}
          category={data.category}
          noticeId={data.noticeId}
          content={data.content}
          createAt={data.createAt}
          modifiedAt={data.modifiedAt}
          title={data.title}
          viewCount={data.viewCount}
          image={data.image}
          adminPath={adminPath}
        />
      ))}
    </div>
  );
}

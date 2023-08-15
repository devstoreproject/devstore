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
}

export default function ContentsList({ tabList, datas }: NoticeListProp) {
  return (
    <div className="flex flex-wrap items-center w-full">
      {datas?.map((data, idx) => (
        <ContentsItem
          classnames={` ${idx % 4 === 0 ? 'ml-0' : ''} ${
            idx % 4 === 3 ? 'mr-0' : ''
          }`}
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
        />
      ))}
    </div>
  );
}

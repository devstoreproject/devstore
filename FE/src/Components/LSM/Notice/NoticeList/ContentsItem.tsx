import { useNavigate } from 'react-router-dom';
import { BsImage } from 'react-icons/bs';
interface ContentsListProps {
  classnames: string;
  tabList: Array<{ id: number; title: string }>;
  key: number;
  category: any;
  content: string;
  createAt: string;
  modifiedAt: string;
  noticeId: number;
  title: string;
  viewCount: number;
  image?: {
    imageId: number;
    originalPath: string;
    thumbnailPath: string;
  };
}

export default function ContentsItem({
  tabList,
  classnames,
  category,
  modifiedAt,
  title,
  image,
  noticeId,
}: ContentsListProps) {
  const classname = `flex flex-col items-center transition-all transform cursor-pointer m-7 hover:-translate-y-2 shadow-signBox hover:shadow-contentsBox rounded-xl w-80 h-80 px-5 ${classnames}`;

  let date = modifiedAt?.slice();
  date = date?.slice(0, 10);

  const navigate = useNavigate();

  const routeHandler = () => {
    navigate(`/notice/${noticeId}`);
  };

  return (
    <button type="button" className={classname} onClick={routeHandler}>
      <div className="w-full">
        <div className="flex items-center my-5">
          <p className="px-3 py-1 text-sm font-semibold bg-tab-gray rounded-2xl">
            {category === 'OPERATING'
              ? '운영정책'
              : category === 'UPDATE'
              ? '업데이트'
              : '이벤트'}
          </p>
          <span className="ml-2 text-xs font-semibold text-subtitle-gray">
            {date}
          </span>
        </div>
        <p className="mb-4 text-lg font-bold text-left line-clamp-2">{title}</p>
        <div className="flex items-center justify-center bg-gray">
          {image != null ? (
            <img
              src={image?.thumbnailPath}
              alt="공지사항 이미지"
              className="object-cover w-full h-40"
            />
          ) : (
            <div className="w-full h-40">
              <BsImage className="w-full my-2 text-8xl text-tab-gray" />
              <span className="text-2xl font-bold text-tab-gray">No Image</span>
            </div>
          )}
        </div>
      </div>
    </button>
  );
}

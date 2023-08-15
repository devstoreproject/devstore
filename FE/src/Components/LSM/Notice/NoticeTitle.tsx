import { useLocation } from 'react-router-dom';

export default function NoticeTitle() {
  const path: string = useLocation().pathname.slice(14);

  const pathList = ['', 'post', 'edit'];
  const titleList = ['📢 공지사항', '공지사항 등록', '공지사항 수정'];

  const idx = pathList.indexOf(path);
  const title = titleList[idx];

  return (
    <>
      {path === '' ? (
        <h2 className="mb-4 text-2xl font-bold">{title}</h2>
      ) : (
        <h2 className="mb-6 text-xl font-bold">{title}</h2>
      )}
      {path === '' ? (
        <h3 className="text-lg font-semibold">DEV SHOP에서 알려주는 소식들</h3>
      ) : null}
    </>
  );
}

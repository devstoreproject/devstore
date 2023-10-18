import { useLocation } from 'react-router-dom';

export default function ProductTitle() {
  const path: string = useLocation().pathname.slice(19);
  const adminPath: string = useLocation().pathname.split('/').slice(3)[0];

  const pathList = ['post', 'edit'];
  const titleList = ['상품등록', '상품수정'];

  const idx =
    path === 'post' ? pathList.indexOf(path) : pathList.indexOf(adminPath);
  const title = titleList[idx];

  return (
    <>
      <h2 className="pb-16 text-2xl font-bold">{title}</h2>
    </>
  );
}

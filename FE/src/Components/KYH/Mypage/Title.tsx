import { useParams } from 'react-router-dom';

export default function Title() {
  const params = useParams()['*'];
  const paramList = ['userinfo', 'orderlist', 'bookmarks'];
  const titleList = ['사용자 정보', '주문 목록', '찜'];

  const idx = paramList.indexOf(params as string);
  const title = titleList[idx];

  return <span className="mb-8 text-xl font-bold text-center">{title}</span>;
}

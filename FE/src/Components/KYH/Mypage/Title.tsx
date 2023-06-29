import { useParams } from 'react-router-dom';

export default function Title() {
  const params = useParams()['*'];
  const paramList = [
    'orderlist',
    'userinfo',
    'bookmarks',
    'reviews',
    'coupons',
    'subscription',
  ];
  const titleList = [
    '주문 목록',
    '사용자 정보',
    '찜',
    '리뷰',
    '쿠폰',
    '정기 구독',
  ];

  const idx = paramList.indexOf(params as string);
  const title = titleList[idx];

  return <span className="mb-8 text-xl font-bold text-center">{title}</span>;
}

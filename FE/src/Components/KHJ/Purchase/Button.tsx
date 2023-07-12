import { Link } from 'react-router-dom';

export default function Button() {
  const ButtonlinkTo = [
    {
      to: '/',
      text: '반품요청',
    },
    {
      to: '/',
      text: '교환요청',
    },
    {
      to: '/',
      text: '취소요청',
    },
    {
      to: '/',
      text: '리뷰작성',
    },
  ];
  return (
    <>
      {ButtonlinkTo.map((link) => (
        <button
          className={
            link.text !== '리뷰작성'
              ? 'h-14 w-32 rounded-full border border-gray-300'
              : 'h-14 w-32 rounded-full bg-light-black text-white'
          }
          key={link.to}
        >
          <Link to={link.to}>{link.text}</Link>
        </button>
      ))}
    </>
  );
}

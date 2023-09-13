import { Link } from 'react-router-dom';

export default function FooterNav() {
  return (
    <ul className="flex max-w-365">
      {navList.map((nav, idx) => (
        <li key={idx} className="mr-4">
          <Link to="">
            {idx < 2 ? (
              <span className={`${classnames} font-semibold`}>{nav}</span>
            ) : (
              <span className={classnames}>{nav}</span>
            )}
          </Link>
        </li>
      ))}
    </ul>
  );
}

const navList = [
  '고객센터',
  '비회원 주문조회',
  '개인정보 처리방침',
  '이용약관',
];

const classnames = 'pb-1 text-sm';

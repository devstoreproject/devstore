import { NavLink } from 'react-router-dom';

export default function MainNav() {
  return (
    <ul className="flex flex-col mr-28 w-28">
      {linkList.map((link) => (
        <li className="flex mb-2" key={link.to}>
          <NavLink
            className={({ isActive }) =>
              isActive ? 'font-bold border-r-4 border-black pr-2 w-full' : ''
            }
            to={link.to}
          >
            {link.text}
          </NavLink>
        </li>
      ))}
    </ul>
  );
}

const linkList = [
  { to: '/mypage/userinfo', text: '사용자 정보' },
  { to: '/mypage/orderlist', text: '주문 목록' },
  { to: '/mypage/bookmarks', text: '찜' },
];

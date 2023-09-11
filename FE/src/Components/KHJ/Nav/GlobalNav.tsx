import { Link } from 'react-router-dom';
import { BsFillPersonFill } from 'react-icons/bs';
import { RxHamburgerMenu } from 'react-icons/rx';
import type { NavFold } from '../Type/NavType';

export default function GlobalNav({
  navOpen,
  setNavOpen,
}: NavFold): React.ReactElement {
  const linkTo = {
    category: [
      {
        to: '/com',
        text: '컴퓨터',
      },
      {
        to: '/mon',
        text: '모니터',
      },
      {
        to: '/mo',
        text: '마우스',
      },
      {
        to: '/head',
        text: '헤드셋',
      },
      {
        to: '/de',
        text: '책상',
      },
      {
        to: '/c',
        text: '의자',
      },
    ],
    service: [
      {
        to: '/notice',
        text: '공지사항',
      },
    ],
  };
  const authorization = localStorage.getItem('authorization');
  const category = linkTo.category;
  const service = linkTo.service;
  return (
    <nav
      className={`box-border fixed top-0 z-20 h-full px-5 pt-24 bg-white w-80 transition-transform opacity-90 ${
        navOpen ? 'translate-x-0 sm:w-full' : '-translate-x-80'
      }`}
    >
      <button
        className="absolute top-12 right-5"
        onClick={() => {
          setNavOpen(false);
        }}
      >
        <RxHamburgerMenu size={24} />
      </button>
      <div className="box-border border-4 bg-neon-green rounded-xl hover:bg-white border-neon-green">
        {authorization === null ? (
          <Link to="/signin" className="flex px-5 py-5">
            <BsFillPersonFill size={24} />
            <span className="block ml-3">로그인</span>
          </Link>
        ) : (
          <Link to="/mypage" className="flex px-5 py-5">
            <BsFillPersonFill size={24} />
            <span className="block ml-3">마이페이지</span>
          </Link>
        )}
      </div>
      <h3 className="px-5 py-2 mt-2">Category</h3>
      <ul>
        {category.map((link) => (
          <Link to={link.to} key={link.to}>
            <li className="h-12 px-5 flex items-center hover:bg-slate-100">
              {link.text}
            </li>
          </Link>
        ))}
      </ul>
      <h3 className="px-5 py-2 mt-2">Service</h3>
      <ul>
        {service.map((link) => (
          <Link to={link.to} key={link.to}>
            <li className="h-12 px-5 flex items-center hover:bg-slate-100">
              {link.text}
            </li>
          </Link>
        ))}
      </ul>
    </nav>
  );
}

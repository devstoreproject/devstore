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
        to: '/login',
        text: '모니터',
      },
      {
        to: '/signup',
        text: '손목보호대',
      },
      {
        to: '/service',
        text: '마우스',
      },
    ],
    service: [
      {
        to: '/notice',
        text: '공지사항',
      },
      {
        to: '/notica',
        text: '고객센터',
      },
    ],
  };
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
      <div className="box-border bg-neon-green rounded-xl">
        <Link to="/login" className="flex px-5 py-5">
          <BsFillPersonFill size={24} />
          <span className="block ml-3">로그인</span>
        </Link>
      </div>
      <h3>Category</h3>
      <ul>
        {category.map((link) => (
          <li key={link.to}>
            <Link to={link.to}>{link.text}</Link>
          </li>
        ))}
      </ul>
      <h3>Service</h3>
      <ul>
        {service.map((link) => (
          <li key={link.to}>
            <Link to={link.to}>{link.text}</Link>
          </li>
        ))}
      </ul>
    </nav>
  );
}

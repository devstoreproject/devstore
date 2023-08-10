import { Link, useNavigate } from 'react-router-dom';
import { BsFillPersonFill } from 'react-icons/bs';
import { BiSearch, BiBell, BiSolidCartAlt } from 'react-icons/bi';
import { RxHamburgerMenu } from 'react-icons/rx';
import type { NavFold } from '../Type/NavType';
import type { SearchNavFold } from '../Type/NavSearchType';

type NavFoldingType = NavFold & SearchNavFold;

export default function NavBottom({
  navOpen,
  setNavOpen,
  searchOpen,
  setSearchOpen,
}: NavFoldingType): React.ReactElement {
  const navigate = useNavigate();
  const sizeAll: number = 22;
  const linkTo = [
    {
      to: '/mypage',
      icon: <BsFillPersonFill size={sizeAll} />,
    },
    {
      to: '/mypage/alert',
      icon: <BiBell size={sizeAll} />,
    },
    {
      to: '/mypage/b',
      icon: <BiSolidCartAlt size={sizeAll} />,
    },
  ];

  return (
    <nav className="relative flex items-center justify-center bg-white sm:justify-between h-14">
      <button
        className={`absolute left-5 top-2/4 -translate-y-2/4 sm:relative sm:top-auto sm:translate-y-0 ${
          navOpen ? 'hidden' : ''
        }`}
        onClick={() => {
          setNavOpen(true);
        }}
      >
        <RxHamburgerMenu size={24} />
      </button>
      <h1 className="text-xl font-bold whitespace-nowrap text-light-black">
        <button
          onClick={() => {
            navigate('/');
          }}
        >
          DEV SHOP
        </button>
      </h1>
      <ul className="absolute flex top-2/4 right-5 -translate-y-2/4 sm:relative sm:translate-y-0 sm:top-auto sm:right-5">
        <li
          onClick={() => {
            setSearchOpen(true);
          }}
        >
          <button>
            <BiSearch size={sizeAll} />
          </button>
        </li>
        {linkTo.map((link) => (
          <li className="ml-6" key={link.to}>
            <Link to={link.to}>{link.icon}</Link>
          </li>
        ))}
      </ul>
    </nav>
  );
}

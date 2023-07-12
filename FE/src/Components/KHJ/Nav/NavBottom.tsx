import { Link } from 'react-router-dom';
import { BsFillPersonFill } from 'react-icons/bs';
import { BiSearch, BiBell, BiSolidCartAlt } from 'react-icons/bi';
import { RxHamburgerMenu } from 'react-icons/rx';

export default function NavBottom() {
  const sizeAll: number = 22;

  const linkTo = [
    {
      to: '/mypage/orderlist',
      icon: <BiSearch size={sizeAll} />,
    },
    {
      to: '/mypage/userinfo',
      icon: <BsFillPersonFill size={sizeAll} />,
    },
    {
      to: '/mypage/alert',
      icon: <BiBell size={sizeAll} />,
    },
    {
      to: '/mypage/alert',
      icon: <BiSolidCartAlt size={sizeAll} />,
    },
  ];

  return (
    <nav className="flex justify-center items-center relative h-14 bg-white">
      <button className="absolute left-5 top-2/4 -translate-y-2/4">
        <RxHamburgerMenu size={24} />
      </button>
      <h1 className="whitespace-nowrap text-xl font-bold text-light-black">
        DEV SHOP
      </h1>
      <ul className="flex absolute top-2/4 right-5 -translate-y-2/4">
        {linkTo.map((link) => (
          <li className="ml-6" key={link.to}>
            <Link to={link.to}>{link.icon}</Link>
          </li>
        ))}
      </ul>
    </nav>
  );
}

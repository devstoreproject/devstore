import { Link } from 'react-router-dom';
import { BsFillPersonFill } from 'react-icons/bs';

export default function GlobalNav() {
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
        to: '/notice',
        text: '고객센터',
      },
    ],
  };
  const category = linkTo.category;
  const service = linkTo.service;

  return (
    <nav className="fixed left-0 top-0 w-80 h-full pt-24 px-5 box-border bg-white opacity-80 hidden">
      <div className="bg-neon-green box-border rounded-xl">
        <Link to="/login" className="flex py-5 px-5">
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

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
        to: '/notica',
        text: '고객센터',
      },
    ],
  };
  const category = linkTo.category;
  const service = linkTo.service;

  return (
    <nav className="box-border fixed top-0 left-0 hidden h-full px-5 pt-24 bg-white w-80 opacity-80">
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

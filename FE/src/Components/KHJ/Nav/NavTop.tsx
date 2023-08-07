import { Link } from 'react-router-dom';
import noticeList from 'Dummy/Notice';

export default function NavTop() {
  const linkTo = [
    {
      to: '/login',
      text: '로그인',
    },
    {
      to: '/signup',
      text: '회원가입',
    },
    {
      to: '/service',
      text: '고객센터',
    },
  ];

  return (
    <nav className="h-9 flex items-center justify-between sm:justify-end px-5 text-subtitle-gray border-b-tab-gray border-b bg-light-gray text-sm">
      <article className="flex items-center sm:hidden">
        <h3 className="text-base">Notice</h3>
        <ul className="h-5 overflow-hidden ml-3">
          {noticeList.map((notice) => (
            <li className="text-sm" key={notice.key}>
              {notice.text}
            </li>
          ))}
        </ul>
      </article>
      <ul className="flex">
        {linkTo.map((link) => (
          <li className="ml-6" key={link.to}>
            <Link to={link.to}>{link.text}</Link>
          </li>
        ))}
      </ul>
    </nav>
  );
}

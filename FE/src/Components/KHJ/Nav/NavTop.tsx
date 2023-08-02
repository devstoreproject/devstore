import { Link } from 'react-router-dom';
import noticeList from 'Dummy/Notice';

export default function NavTop() {
  const linkTo = [
    {
      to: '/signin',
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
    <nav className="flex items-center justify-between px-5 text-sm border-b h-9 text-subtitle-gray border-b-tab-gray bg-light-gray">
      <article className="flex items-center">
        <h3 className="text-base">Notice</h3>
        <ul className="h-5 ml-3 overflow-hidden">
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
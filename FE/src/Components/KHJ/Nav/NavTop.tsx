import { Link, useNavigate } from 'react-router-dom';
import noticeList from 'Dummy/Notice';
import fetchLogOut from 'utils/auth/fetchLogOut';

export default function NavTop() {
  const authorization = localStorage.getItem('authorization');
  const userId = localStorage.getItem('userId');
  const navigate = useNavigate();

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
    <nav className="flex items-center justify-between px-5 text-sm border-b h-9 sm:justify-end text-subtitle-gray border-b-tab-gray bg-light-gray">
      <article className="flex items-center sm:hidden">
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
        {userId === '1' ? (
          <li>
            <button
              onClick={() => {
                navigate('/admin');
              }}
              className="font-bold"
            >
              관리자 페이지
            </button>
          </li>
        ) : null}
        <li className="ml-6">
          {authorization === null ? null : (
            <button
              onClick={() => {
                fetchLogOut(navigate);
              }}
            >
              로그아웃
            </button>
          )}
        </li>
        {(authorization === null ? linkTo : [linkTo[2]]).map((link) => (
          <li className="ml-6" key={link.to}>
            <Link to={link.to}>{link.text}</Link>
          </li>
        ))}
      </ul>
    </nav>
  );
}

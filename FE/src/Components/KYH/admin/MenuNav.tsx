import { NavLink } from 'react-router-dom';
import { RiFileListFill } from 'react-icons/ri';
import { FaBox, FaChartLine } from 'react-icons/fa';
import { IoMdCheckboxOutline } from 'react-icons/io';
import { BiSolidStar } from 'react-icons/bi';
import { AiOutlineSound } from 'react-icons/ai';
import { MdOutlineReviews } from 'react-icons/md';
import { BsFillQuestionCircleFill } from 'react-icons/bs';

export default function MenuNav() {
  return (
    <ul>
      {menuList.map((menu) => (
        <li key={menu.to}>
          <NavLink
            to={menu.to}
            className={({ isActive }) =>
              isActive
                ? 'text-xl flex bg-light-black text-neon-green h-14 rounded-lg items-center'
                : 'text-xl flex h-14 items-center'
            }
          >
            <div className="mx-4 text-2xl">{menu.icon}</div>
            <span>{menu.text}</span>
          </NavLink>
        </li>
      ))}
    </ul>
  );
}

const menuList = [
  {
    to: '/admin/productlist',
    text: '판매 상품 리스트',
    icon: <RiFileListFill />,
  },
  { to: '/admin/any1', text: '주문 전 상품 내역', icon: <FaBox /> },
  {
    to: '/admin/returnorexchange',
    text: '반품 교환 신청 확인',
    icon: <IoMdCheckboxOutline />,
  },
  { to: '/admin/eventvanner', text: '이벤트 배너', icon: <BiSolidStar /> },
  { to: '/admin/any2', text: '공지사항', icon: <AiOutlineSound /> },
  { to: '/admin/any3', text: '매출', icon: <FaChartLine /> },
  { to: '/admin/any4', text: '리뷰', icon: <MdOutlineReviews /> },
  {
    to: '/admin/productinquiry',
    text: '상품 문의',
    icon: <BsFillQuestionCircleFill />,
  },
];

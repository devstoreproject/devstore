import { NavLink } from 'react-router-dom';
import { RiFileListFill } from 'react-icons/ri';
import { FaBox, FaChartLine } from 'react-icons/fa';
import { AiOutlineSound } from 'react-icons/ai';
import { MdOutlineReviews } from 'react-icons/md';
import { BsFillQuestionCircleFill } from 'react-icons/bs';

interface HomeNavigateProp {
  isFold: boolean;
}

export default function MenuNav({ isFold }: HomeNavigateProp) {
  return (
    <ul>
      {menuList.map((menu) => (
        <li key={menu.to}>
          <NavLink
            to={menu.to}
            className={({ isActive }) =>
              isActive
                ? 'flex bg-light-black text-neon-green h-14 rounded-lg items-center'
                : 'flex h-14 items-center'
            }
          >
            <div className="mx-4 text-xl">{menu.icon}</div>
            <p className={`truncate ${isFold ? 'hidden' : ''}`}>{menu.text}</p>
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
  { to: '/admin/orderedlist', text: '주문 된 상품 내역', icon: <FaBox /> },
  { to: '/admin/notice', text: '공지사항', icon: <AiOutlineSound /> },
  { to: '/admin/sales', text: '매출', icon: <FaChartLine /> },
  { to: '/admin/reviewmanagement', text: '리뷰', icon: <MdOutlineReviews /> },
  {
    to: '/admin/productinquiry',
    text: '상품 문의',
    icon: <BsFillQuestionCircleFill />,
  },
];

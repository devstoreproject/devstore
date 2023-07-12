import { IoHomeSharp } from 'react-icons/io5';
import { NavLink } from 'react-router-dom';

interface HomeNavigateProp {
  fold: boolean;
}

export default function HomeNavigate({ fold }: HomeNavigateProp) {
  return (
    <NavLink
      to="/admin"
      className={`flex items-center h-16 pl-4 mt-6 mb-4 ${
        fold ? '' : 'border border-gray-400 rounded-md'
      }`}
    >
      <span className="mr-4 text-xl">
        <IoHomeSharp />
      </span>
      <span className={`${fold ? 'hidden' : 'font-bold'}`}>HOME</span>
    </NavLink>
  );
}

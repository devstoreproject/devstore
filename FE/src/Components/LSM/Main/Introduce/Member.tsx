import { Link } from 'react-router-dom';

interface MemberProp {
  name: string;
  link: string;
}

export default function Member({ name, link }: MemberProp) {
  return (
    <li className="flex items-center justify-center px-8 py-2 mr-10 transition-all duration-200 delay-75 cursor-pointer hover:bg-neon-green hover:-translate-y-2 rounded-xl bg-light-gray md:px-3 sm:mb-10 sm:w-1/2">
      <Link to={link} target="_blank">
        <p className="text-xs font-semibold">{name}</p>
      </Link>
    </li>
  );
}

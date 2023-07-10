import checkList from 'Dummy/Checklist';
import { FaHeart } from 'react-icons/fa';

export default function CheckListItem() {
  return (
    <>
      {checkList.map((item) => (
        <li
          key={item.key}
          className="bg-white rounded-xl p-5 flex w-128 mb-8 items-center"
        >
          <div className="w-48 h-36 border border-tab-gray rounded-xl"></div>
          <div className="w-60 mx-5">
            <h3>{item.title}</h3>
            <p className="relative block font-bold mt-2">
              {item.price}
              <FaHeart
                size={26}
                className="text-neon-green absolute top-0 right-0"
              />
            </p>
          </div>
        </li>
      ))}
    </>
  );
}

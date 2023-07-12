import BestItemInfo from './BestItemInfo';

interface BestItemProps {
  id: number;
  title: string;
  tags: string[];
  price: string;
}

export default function BestItem({ id, title, tags, price }: BestItemProps) {
  return (
    <li className="relative mx-4 cursor-pointer lg:w-1/4 sm:w-full sm:mb-4 md:w-1/2 md:my-2 md:m-auto md:px-4">
      <div className="mb-2 transition-all bg-white bg-cover rounded-tl-none h-60 rounded-xl hover:scale-95 bg-best-item-image">
        <div className="flex items-center justify-center w-12 h-12 font-semibold text-white rounded-br-lg bg-box-black">
          {id}
        </div>
      </div>
      <BestItemInfo title={title} tags={tags} price={price} />
    </li>
  );
}

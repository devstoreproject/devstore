import BestItem from './BestItem';
import Button from './Button';

export default function BestItemList() {
  return (
    <div className="relative m-auto">
      <ul className="flex items-center justify-center w-full md:w-full md:flex-wrap md:flex-row md:justify-between sm:flex-col md:px-10">
        {contents.map((content) => (
          <BestItem
            key={content.id}
            id={content.id}
            title={content.title}
            tags={content.tags}
            price={content.price}
          />
        ))}
      </ul>
      <Button />
    </div>
  );
}

const contents = [
  {
    id: 1,
    title: '상품1',
    tags: ['태그1', '태그2', '태그3'],
    price: '100원',
  },
  {
    id: 2,
    title: '상품2',
    tags: ['태그1', '태그2', '태그3'],
    price: '10000원',
  },
  {
    id: 3,
    title: '상품3',
    tags: ['태그1', '태그2', '태그3'],
    price: '100000원',
  },
  {
    id: 4,
    title: '상품4',
    tags: ['태그1', '태그2', '태그3'],
    price: '10원',
  },
];

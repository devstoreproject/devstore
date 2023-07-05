import ContentsItem from './ContentsItem';

export default function ContentsList() {
  const contents = [
    '1',
    '2',
    '3',
    '4',
    '5',
    '6',
    '7',
    '8',
    '9',
    '10',
    '11',
    '12',
    '13',
    '14',
  ];
  return (
    <ul className="flex flex-wrap items-center w-full">
      {contents.map((content, idx) => (
        <ContentsItem
          key={idx}
          classnames={` ${idx % 4 === 0 ? 'ml-0' : ''} ${
            idx % 4 === 3 ? 'mr-0' : ''
          }`}
        />
      ))}
    </ul>
  );
}

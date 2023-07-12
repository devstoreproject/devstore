import Button from './Button';
import ThemeItem from './ThemeItem';

export default function ThemeItemList() {
  const contents = [
    {
      id: 1,
      title: '데브스토어 테마상품1',
      paragraph:
        '개발자들이 작업을 진행하며 많은 이들이 오랜 키보드 사용으로 인해 손목에 불편함을 느끼곤 한다. 그런 개발자들을 위해 준비한 상품을 찾아보자',
      tags: ['태그1', '태그2', '태그3'],
    },
    {
      id: 2,
      title: '데브스토어 테마상품2',
      paragraph:
        '개발자들이 작업을 진행하며 많은 이들이 오랜 키보드 사용으로 인해 손목에 불편함을 느끼곤 한다. 그런 개발자들을 위해 준비한 상품을 찾아보자',
      tags: ['태그1', '태그2', '태그3'],
    },
  ];
  return (
    <div className="relative w-full m-auto">
      <ul className="flex items-center justify-between w-full md:flex-col sm:flex-col">
        {contents.map((content) => (
          <ThemeItem
            key={content.id}
            title={content.title}
            paragraph={content.paragraph}
            tags={content.tags}
          />
        ))}
      </ul>
      <Button />
    </div>
  );
}

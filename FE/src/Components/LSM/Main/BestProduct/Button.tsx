import { GrFormPrevious, GrFormNext } from 'react-icons/gr';

interface BestItemListProps {
  onPrevClick: () => void;
  onNextClick: () => void;
}

export default function Button({
  onPrevClick,
  onNextClick,
}: BestItemListProps) {
  const buttons = ['prev', 'next'];
  const classnames =
    'absolute text-6xl top-1/3 md:top-1/6 -right-20 md:-right-8 sm:-right-8';
  return (
    <ul>
      {buttons.map((button, idx) => (
        <li key={idx}>
          {idx === 0 ? (
            <button
              type="button"
              onClick={onPrevClick}
              className={`-left-20 md:-left-8 sm:-left-8 ${classnames}`}
            >
              <GrFormPrevious />
            </button>
          ) : (
            <button type="button" onClick={onNextClick} className={classnames}>
              <GrFormNext />
            </button>
          )}
        </li>
      ))}
    </ul>
  );
}

import { GrFormPrevious, GrFormNext } from 'react-icons/gr';

export default function Button() {
  const buttons = ['prev', 'next'];
  const classnames =
    'absolute text-6xl top-1/3 md:top-1/6 -right-20 md:-right-8 sm:hidden';
  return (
    <ul>
      {buttons.map((button, idx) => (
        <li key={idx}>
          {idx === 0 ? (
            <button
              type="button"
              className={`-left-20 md:-left-8 ${classnames}`}
            >
              <GrFormPrevious />
            </button>
          ) : (
            <button type="button" className={classnames}>
              <GrFormNext />
            </button>
          )}
        </li>
      ))}
    </ul>
  );
}

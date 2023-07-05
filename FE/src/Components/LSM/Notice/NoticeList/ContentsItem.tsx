interface ContentsListProps {
  classnames: string;
}

export default function ContentsItem({ classnames }: ContentsListProps) {
  const classname = `flex flex-col items-center justify-center text-center transition-all transform cursor-pointer m-7 motion-safe:hover:-translate-y-2 shadow-signBox rounded-xl w-80 h-80 ${classnames}`;
  return (
    <li className={classname}>
      <p></p>
    </li>
  );
}

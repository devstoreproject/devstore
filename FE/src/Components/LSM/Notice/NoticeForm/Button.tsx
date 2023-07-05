import { useLocation } from 'react-router-dom';

export default function Button() {
  const path: string = useLocation().pathname.slice(8);
  const postButtonList = [
    { id: 1, name: '등록' },
    { id: 2, name: '취소' },
  ];
  const editButtonList = [
    { id: 1, name: '수정' },
    { id: 2, name: '취소' },
  ];
  const classnames =
    'w-20 py-1.5 px-1 text-sm font-semibold hover:bg-gray-400 border rounded-xl border-gray mr-2 transition-all';
  return (
    <div className="flex items-center justify-center mt-8">
      {path === 'post' &&
        postButtonList.map((btn) => {
          return (
            <button
              type={btn.name === '취소' ? 'button' : 'submit'}
              key={btn.id}
              className={classnames}
            >
              {btn.name}
            </button>
          );
        })}
      {path === 'edit' &&
        editButtonList.map((btn) => {
          return (
            <button
              type={btn.name === '취소' ? 'button' : 'submit'}
              key={btn.id}
              className={classnames}
            >
              {btn.name}
            </button>
          );
        })}
    </div>
  );
}

import { useLocation } from 'react-router-dom';

export default function Button() {
  const path: string = useLocation().pathname.slice(9);
  const postButtonList = [
    { id: 1, name: '등록하기' },
    { id: 2, name: '돌아가기' },
  ];
  const editButtonList = [
    { id: 1, name: '수정하기' },
    { id: 2, name: '삭제하기' },
  ];
  const classnames =
    'hover:bg-label-gray hover:text-white mr-2 px-10 py-3 text-sm border rounded-3xl transition-all';
  return (
    <div className="flex items-center mt-20">
      {path === 'post' &&
        postButtonList.map((btn) => {
          return (
            <button
              type={btn.name === '돌아가기' ? 'button' : 'submit'}
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
              type={btn.name === '삭제하기' ? 'button' : 'submit'}
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

import { useLocation, useNavigate } from 'react-router-dom';

interface NoticeProp {
  postData: any;
  patchData: any;
}

export default function Button({ postData, patchData }: NoticeProp) {
  const navigate = useNavigate();
  const prevHandler = () => {
    navigate('/admin/notice');
  };

  const postPath: string = useLocation().pathname.slice(14);
  const editPath: string = useLocation().pathname.split('/').slice(3)[0];

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
      {postPath === 'post' &&
        postButtonList.map((btn) => {
          return (
            <button
              type={btn.name === '취소' ? 'button' : 'submit'}
              key={btn.id}
              className={classnames}
              onClick={btn.name === '취소' ? prevHandler : postData}
            >
              {btn.name}
            </button>
          );
        })}
      {editPath === 'edit' &&
        editButtonList.map((btn) => {
          return (
            <button
              type={btn.name === '취소' ? 'button' : 'submit'}
              key={btn.id}
              className={classnames}
              onClick={btn.name === '취소' ? prevHandler : patchData}
            >
              {btn.name}
            </button>
          );
        })}
    </div>
  );
}

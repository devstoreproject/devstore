import { useLocation, useNavigate } from 'react-router-dom';

interface ProductProp {
  postData: any;
  // patchData: any;
}

export default function Button({ postData }: ProductProp) {
  const navigate = useNavigate();
  const prevHandler = () => {
    navigate('/admin/productlist');
    window.scrollTo(0, 0);
  };

  const path: string = useLocation().pathname.split('/').slice(3)[0];

  const postButtonList = [
    { id: 1, name: '등록' },
    { id: 2, name: '취소' },
  ];
  const editButtonList = [
    { id: 1, name: '수정' },
    { id: 2, name: '취소' },
  ];
  const classnames =
    'hover:bg-label-gray hover:text-white mr-2 px-10 py-3 text-sm border rounded-3xl transition-all';
  return (
    <div className="flex items-center mt-20">
      {path === 'post' &&
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
      {path === 'edit' &&
        editButtonList.map((btn) => {
          return (
            <button
              type={btn.name === '취소' ? 'button' : 'submit'}
              key={btn.id}
              className={classnames}
              onClick={btn.name === '취소' ? prevHandler : prevHandler}
            >
              {btn.name}
            </button>
          );
        })}
    </div>
  );
}

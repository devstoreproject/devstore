import { useNavigate } from 'react-router-dom';

export default function Button() {
  const navigate = useNavigate();

  const onClickHandler = () => {
    navigate('/search');
    window.scrollTo(0, 0);
  };
  return (
    <button
      type="button"
      onClick={onClickHandler}
      className="block px-8 py-2 m-auto mt-20 mb-40 text-sm transition-all border border-label-gray rounded-3xl hover:bg-label-gray hover:text-white"
    >
      <span className="font-semibold">테마</span> 더 보기
    </button>
  );
}

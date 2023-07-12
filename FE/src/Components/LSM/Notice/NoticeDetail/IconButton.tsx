import { AiOutlineEdit, AiOutlineDelete } from 'react-icons/ai';
import { IoMdArrowRoundBack } from 'react-icons/io';
import { useNavigate } from 'react-router-dom';

export default function IconButton() {
  const navigate = useNavigate();
  const movePrevPageHandler = () => {
    navigate('/notice', { replace: true });
  };
  return (
    <>
      <div className="absolute top-0 right-0 flex items-center text-2xl">
        <button type="button" className="mr-2">
          <AiOutlineEdit />
        </button>
        <button>
          <AiOutlineDelete />
        </button>
      </div>
      <button
        type="button"
        className="absolute top-0 left-0 flex items-center text-2xl"
        onClick={movePrevPageHandler}
      >
        <IoMdArrowRoundBack />
      </button>
    </>
  );
}

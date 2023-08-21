import { AiOutlineEdit, AiOutlineDelete } from 'react-icons/ai';
import { IoMdArrowRoundBack } from 'react-icons/io';
import { useNavigate, useParams } from 'react-router-dom';
import api from 'api';

interface DataProps {
  datas: any;
}

export default function IconButton({ datas }: DataProps) {
  const { id } = useParams() as { id: string };

  // const path = useLocation();
  // const adminPath = path.pathname.slice(1, 6);

  const navigate = useNavigate();
  const movePrevPageHandler = () => {
    navigate(-1);
    // if (adminPath === 'admin') {
    //   navigate('/admin/notice', { replace: true });
    // } else {
    //   navigate('/notice', { replace: true });
    // }
  };

  const moveEditHandler = () => {
    navigate(`/admin/notice/edit/${id}`, { replace: true });
  };

  const deleteHandler = async () => {
    try {
      if (window.confirm('이 게시글을 정말 삭제하시겠습니까?')) {
        await api.delete(`/api/notices/${id}`);
        alert('삭제 되었습니다!😇');
        navigate('/admin/notice', { replace: true });
      } else {
        alert('취소 되었습니다!😯');
      }
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <>
      <div className="absolute top-0 right-0 flex items-center text-xl">
        <button type="button" className="mr-2" onClick={moveEditHandler}>
          <AiOutlineEdit />
        </button>
        <button type="button" onClick={deleteHandler as any}>
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

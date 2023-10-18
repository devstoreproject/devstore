import api from 'api';
import type { InquiryContentType } from '../Tab';
import { useParams } from 'react-router-dom';

interface OwnProps {
  i: number;
  inquiryItem: InquiryContentType;
  inquiry: InquiryContentType[] | null;
  setInquiry: React.Dispatch<React.SetStateAction<InquiryContentType[] | null>>;
  setEdit: React.Dispatch<React.SetStateAction<number | null>>;
  edit: number | null;
}

export default function InquiryEditDeleteBtn({
  i,
  inquiryItem,
  inquiry,
  setInquiry,
  setEdit,
  edit,
}: OwnProps) {
  const { id } = useParams();

  const deleteHandler = () => {
    const confirmDelete = window.confirm('정말로 삭제하시겠습니까?');
    const deleteApiUrl = `api/qna/${inquiryItem.questionId}`;

    if (confirmDelete) {
      api
        .delete(deleteApiUrl, {
          headers: {
            Authorization: localStorage.getItem('authorization'),
          },
        })
        .then((res) => {
          setInquiry((prevInquiry) => {
            if (prevInquiry === null) {
              return [];
            }
            return prevInquiry.filter((item, index) => index !== i);
          });
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  const editHandler = (i: number) => {
    if (edit === i && inquiry !== null) {
      const editedInquiry = [...inquiry];
      const comment = editedInquiry[i].comment;
      api
        .patch(
          `api/qna/items/${id as string}/${inquiryItem.questionId}`,
          { comment },
          {
            headers: { Authorization: localStorage.getItem('authorization') },
          }
        )
        .then(() => {
          setEdit(null);
        })
        .catch((err) => {
          console.log(err);
        });
    } else {
      setEdit(i);
    }
  };

  return (
    <>
      <div className="absolute right-14">
        <button
          className="border border-black rounded-lg px-2"
          onClick={() => {
            editHandler(i);
          }}
        >
          수정
        </button>
      </div>
      <div className="absolute right-2">
        <button
          className="border border-black rounded-lg px-2"
          onClick={deleteHandler}
        >
          삭제
        </button>
      </div>
    </>
  );
}

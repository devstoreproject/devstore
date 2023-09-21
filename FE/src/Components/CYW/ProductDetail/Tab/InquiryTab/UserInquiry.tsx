import type { InquiryContentType } from '../Tab';
import { BiPlus } from 'react-icons/bi';
export interface OwnProps {
  inquiry: InquiryContentType;
}

export default function UserInquiry({ inquiry }: OwnProps) {
  return (
    <div className="flex pl-8">
      <p>Q.</p>
      <p>&nbsp;{inquiry.comment}</p>
      <button>
        <BiPlus />
      </button>
    </div>
  );
}

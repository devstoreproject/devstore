import type { ReviewContentType } from '../Tab';

interface OwnProps {
  review: ReviewContentType;
}

export default function UserInfo({ review }: OwnProps) {
  const dateString = review.createdAt;
  const parsedDate = new Date(dateString);
  const year = parsedDate.getFullYear();
  const month = String(parsedDate.getMonth() + 1).padStart(2, '0');
  const day = String(parsedDate.getDate()).padStart(2, '0');

  const formattedDate = `${year}.${month}.${day}`;

  return (
    <div className="flex flex-col items-center w-30">
      <img
        src="https://i.ytimg.com/vi/d9pypaFzaCA/maxresdefault.jpg"
        className="object-cover w-8 h-8 rounded-full"
      />
      <p className="text-sm">{review.userName}</p>
      <p className="text-xs">{formattedDate}</p>
    </div>
  );
}

import BookmarkItem from './BookmarkItem';

export default function Bookmarks() {
  return (
    <div className="flex flex-col">
      <span className="mb-4 font-bold">찜 목록</span>
      <ul className="flex flex-wrap">
        <BookmarkItem />
        <BookmarkItem />
        <BookmarkItem />
        <BookmarkItem />
        <BookmarkItem />
      </ul>
    </div>
  );
}

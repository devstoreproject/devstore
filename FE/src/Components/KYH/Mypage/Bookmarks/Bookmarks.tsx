import useFetchBookmarks from 'hooks/mypage/useFetchBookmarks';
import BookmarkItem from './BookmarkItem';

export default function Bookmarks() {
  const bookmarks = useFetchBookmarks();
  return (
    <div className="flex flex-col">
      <span className="mb-4 font-bold">찜 목록</span>
      <ul className="flex flex-wrap">
        {bookmarks.map((bookmark) => (
          <BookmarkItem
            key={bookmark.itemId}
            itemId={bookmark.itemId}
            name={bookmark.name}
            itemPrice={bookmark.itemPrice}
            like={bookmark.like}
            imageList={bookmark.imageList}
          />
        ))}
      </ul>
    </div>
  );
}

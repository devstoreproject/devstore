import api from 'api';
import type { Bookmark } from 'model/product';
import { useEffect, useState } from 'react';

export default function useFetchBookmarks() {
  const [bookmarks, setBookmarks] = useState<Bookmark[]>([]);
  useEffect(() => {
    api
      .get('/api/items/favorite')
      .then((res) => {
        setBookmarks(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setBookmarks]);

  return bookmarks;
}

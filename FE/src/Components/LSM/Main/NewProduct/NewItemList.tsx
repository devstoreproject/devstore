import { useState, useEffect } from 'react';
import Button from '../BestProduct/Button';
import NewItem from './NewItem';

interface NewItemListProps {
  newItemsData: any;
}

export default function NewItemList({ newItemsData }: NewItemListProps) {
  const itemsPerPage = 4;
  const [itemsPerVisiblePage, setItemsPerVisiblePage] =
    useState<number>(itemsPerPage);
  const [currentPage, setCurrentPage] = useState<number>(0);

  const handlePrevClick = () => {
    setCurrentPage((prevPage) => Math.max(0, prevPage - 1));
  };

  const handleNextClick = () => {
    setCurrentPage((prevPage) =>
      Math.min(
        Math.ceil([...newItemsData].length / itemsPerVisiblePage) - 1,
        prevPage + 1
      )
    );
  };

  useEffect(() => {
    const handleResize = () => {
      const windowSize = window.innerWidth;
      if (windowSize < 640) {
        setItemsPerVisiblePage(1);
      } else if (windowSize < 1024) {
        setItemsPerVisiblePage(2);
      } else {
        setItemsPerVisiblePage(itemsPerPage);
      }
      setCurrentPage(0);
    };

    window.addEventListener('resize', handleResize);
    handleResize();

    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, []);

  const startIdx = currentPage * itemsPerVisiblePage;
  const endIdx = startIdx + itemsPerVisiblePage;
  const visibleItems = [...newItemsData].slice(startIdx, endIdx);

  const transformValue = `translateX(-${
    currentPage * (0.1 / itemsPerVisiblePage)
  }%)`;

  return (
    <div className="relative w-full m-auto ">
      <div className="overflow-hidden">
        <div
          className="flex w-full transition-transform duration-100 ease-in-out md:w-full md:flex-wrap md:justify-between sm:px-10 md:px-10"
          style={{ transform: transformValue }}
        >
          {visibleItems?.map((item: any, idx: any) => (
            <NewItem
              key={item.itemId}
              id={item.itemId}
              title={item.name}
              category={item.category}
              price={item.itemPrice}
              image={item?.imageList[idx]?.thumbnailPath}
              idx={idx}
            />
          ))}
        </div>
      </div>
      <Button onPrevClick={handlePrevClick} onNextClick={handleNextClick} />
    </div>
  );
}

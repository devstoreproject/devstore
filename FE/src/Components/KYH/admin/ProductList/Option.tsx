interface OwnProps {
  name: string | null;
  additionalPrice: number;
  itemCount: number;
}

export default function Option({ name, additionalPrice, itemCount }: OwnProps) {
  return (
    <div className="flex border-b-2 border-gray-300">
      <div className="flex items-center justify-center h-8 w-36">{name}</div>
      <div className="flex items-center justify-center w-32 h-8 border-gray-300 border-x-2">
        {additionalPrice}
      </div>
      <div className="flex items-center justify-center h-8 w-28">
        {itemCount}
      </div>
    </div>
  );
}

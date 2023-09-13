interface OrderInputProp {
  isFree: boolean;
  setIsFree: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function CheckBox({ isFree, setIsFree }: OrderInputProp) {
  const onCheckHandler = (e: any) => {
    setIsFree(e.target.checked);
  };
  return (
    <div className="relative flex items-center justify-between">
      <input
        id="check"
        type="checkbox"
        onChange={onCheckHandler}
        checked={isFree}
        className="w-4 h-4 ml-4 mr-2 border rounded-full appearance-none border-label-gray checked:bg-neon-green"
      />
      <label htmlFor="check" className="w-20 text-label-gray">
        무료배송
      </label>
    </div>
  );
}

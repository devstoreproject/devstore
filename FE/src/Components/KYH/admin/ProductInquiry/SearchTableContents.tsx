interface OwnProps {
  checked: boolean[];
  setChecked: React.Dispatch<React.SetStateAction<boolean[]>>;
}

export default function SearchTableContents({ checked, setChecked }: OwnProps) {
  return (
    <div className="flex items-center border-b-gray-400">
      <label className="flex items-center">
        <input
          type="checkbox"
          className="w-6 h-6 ml-6"
          checked={checked[0]}
          onChange={() => {
            setChecked((prev) => [!prev[0], prev[1]]);
          }}
        />
        <span className="ml-2 text-lg text-gray-500">답변대기중</span>
      </label>
      <label className="flex items-center">
        <input
          type="checkbox"
          className="w-6 h-6 ml-6"
          checked={checked[1]}
          onChange={() => {
            setChecked((prev) => [prev[0], !prev[1]]);
          }}
        />
        <span className="ml-2 text-lg text-gray-500">답변완료</span>
      </label>
    </div>
  );
}

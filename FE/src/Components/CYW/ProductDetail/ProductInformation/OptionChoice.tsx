import type { OptionListType } from 'Pages/CYW/ProductDetail';

interface OwnProps {
  option: OptionListType[];
  selectedValue: any;
  setSelectedValue: React.Dispatch<React.SetStateAction<any>>;
}

export default function OptionChoice({
  option,
  selectedValue,
  setSelectedValue,
}: OwnProps) {
  const uniqueOptions: Record<string, boolean> = {};
  return (
    <div className="pt-5">
      <p>옵션</p>
      {option.map((optionItem, i) => {
        if (uniqueOptions[optionItem.optionName]) {
          return null;
        }

        uniqueOptions[optionItem.optionName] = true;

        return (
          <div key={i} className="flex flex-col pt-4">
            <div className="flex flex-row border-box border-2 rounded-full items-center py-3 justify-center">
              <label className="pr-4">{optionItem.optionName}</label>
              <select
                id="option"
                className="text-slate-600 bg-slate-50"
                value={selectedValue}
                onChange={(e) => {
                  setSelectedValue(e.currentTarget.value);
                  console.log(e.currentTarget.value);
                }}
              >
                <option>옵션을 선택해주세요</option>
                <option value={optionItem.optionId}>
                  {optionItem.optionDetail}
                </option>
              </select>
            </div>
          </div>
        );
      })}
    </div>
  );
}

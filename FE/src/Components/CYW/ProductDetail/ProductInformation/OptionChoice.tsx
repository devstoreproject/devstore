import type { OptionListType } from 'Pages/CYW/ProductDetail';

interface OwnProps {
  option: OptionListType[];
  selectedValue: any;
  setSelectedValue: React.Dispatch<React.SetStateAction<number>>;
  setSelectedOptionDetail: React.Dispatch<React.SetStateAction<string | null>>;
}

export default function OptionChoice({
  option,
  selectedValue,
  setSelectedValue,
  setSelectedOptionDetail,
}: OwnProps) {
  return (
    <div className="pt-5">
      <div className="flex flex-col pt-4">
        <div className="flex flex-row items-center justify-center py-3 border-2 rounded-full border-box">
          <select
            id="option"
            className="text-slate-600 bg-slate-50"
            value={selectedValue}
            onChange={(e) => {
              setSelectedValue(Number(e.currentTarget.value));
              setSelectedOptionDetail(
                option.filter(
                  (item) => item.optionId === Number(e.currentTarget.value)
                )[0]?.optionDetail
              );
            }}
          >
            <option value="0">옵션을 선택해주세요</option>
            {option.map((item) => (
              <option key={item.optionId} value={item.optionId}>
                {item.optionDetail}
              </option>
            ))}
          </select>
        </div>
      </div>
    </div>
  );
}

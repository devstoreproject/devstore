import MainTitle from './MainTitle';
import ThemeItemList from './ThemeProduct/ThemeItemList';

export default function MainThemeProduct() {
  return (
    <div className="w-full px-10">
      <MainTitle
        title="테마 별 상품"
        icon="✨"
        subTitle="THEME ITEM"
        classnames="text-left"
      />
      <ThemeItemList />
    </div>
  );
}

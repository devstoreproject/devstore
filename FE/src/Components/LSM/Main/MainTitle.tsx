interface MainTitleItem {
  title: string;
  icon: string;
  subTitle: string;
  classnames?: string;
}
export default function MainTitle({
  title,
  icon,
  subTitle,
  classnames = '',
}: MainTitleItem) {
  return (
    <div className="text-center mt-28 mb-14">
      <p className={`text-lg font-semibold ${classnames}`}>
        {title}
        <span className="pl-1">{icon}</span>
      </p>
      <p className={`text-xs text-subtitle-gray ${classnames}`}>{subTitle}</p>
    </div>
  );
}

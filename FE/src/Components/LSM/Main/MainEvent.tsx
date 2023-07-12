import EventItemList from './Event/EventItemList';
import MainTitle from './MainTitle';

export default function MainEvent() {
  return (
    <div className="w-full pl-10">
      <MainTitle
        title="오직 DEV SHOP에서만!"
        icon="😁"
        subTitle="EVENT"
        classnames="text-left"
      />
      <EventItemList />
    </div>
  );
}

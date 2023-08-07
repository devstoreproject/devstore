import CheckListMain from 'Components/KHJ/CheckList/CheckListMain';
import PageHistory from 'Components/KHJ/Common/PageHistory';

export default function CheckList() {
  return (
    <main>
      <PageHistory pageName="찜목록" pageHistory="찜목록" />
      <CheckListMain />
    </main>
  );
}

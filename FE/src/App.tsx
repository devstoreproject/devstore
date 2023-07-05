import { BrowserRouter, Routes, Route } from 'react-router-dom';

import SignIn from 'Pages/KYH/SignIn';
import SignUp from 'Pages/KYH/SignUp';
import SleeperAccount from 'Pages/KYH/SleeperAccount';
import Mypage from 'Pages/KYH/Mypage';
import Admin from 'Pages/KYH/Admin';
import NoticeList from 'Pages/LSM/Notice/NoticeList';
import NoticePost from 'Pages/LSM/Notice/NoticePost';
import NoticeEdit from 'Pages/LSM/Notice/NoticeEdit';
import NoticeDetail from 'Pages/LSM/Notice/NoticeDetail';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/signup" element={<SignUp />} />
        <Route path="/signin" element={<SignIn />} />
        <Route path="/sleeperaccount" element={<SleeperAccount />} />
        <Route path="/mypage/*" element={<Mypage />} />
        <Route path="/admin/*" element={<Admin />} />
        <Route path="/notice" element={<NoticeList />} />
        <Route path="/notice/post" element={<NoticePost />} />
        <Route path="/notice/edit" element={<NoticeEdit />} />
        <Route path="/notice/detail" element={<NoticeDetail />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;

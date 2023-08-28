import api from 'api';

interface UserInfo {
  nickname: string;
  phone: string;
}

const fetchUserDataEdit = (userInfo: UserInfo) => {
  const userId = localStorage.getItem('userId');
  const authorization = localStorage.getItem('authorization');

  const formData = new FormData();
  const blob = new Blob([JSON.stringify(userInfo)], {
    type: 'application/json',
  });

  formData.append('patch', blob);

  if (userId !== null) {
    api
      .patch(`/api/users/${userId}`, formData, {
        headers: {
          Authorization: authorization,
        },
      })
      .then(() => {
        window.alert('수정완료');
      })
      .catch((err) => {
        console.log(err);
      });
  }
};

export default fetchUserDataEdit;

import api from 'api';

interface FetchAddress {
  recipient: string;
  mobileNumber: string;
  zipCode: string;
  addressDetail: string;
  addressSimple: string;
}

export const fetchPostAddress = (address: FetchAddress) => {
  api
    .post('/api/address', address)
    .then(() => {
      window.alert('수정완료');
    })
    .catch((err) => {
      console.log(err);
    });
};

export const fetchPatchAddress = (address: FetchAddress, idx: number) => {
  const addressNumber = idx + 1;

  api
    .patch(`/api/address/${addressNumber}`, address)
    .then(() => {
      window.alert('수정완료');
    })
    .catch((err) => {
      console.log(err);
    });
};

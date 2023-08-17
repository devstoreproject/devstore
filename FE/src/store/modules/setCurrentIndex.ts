export const SET_INDEX = 'contents/SET_INDEX';

export const setCurrentIndex = (idx: number) => ({
  type: SET_INDEX,
  payload: idx,
});

const initialState = 0;

export default function currentIndex(
  state = initialState,
  action: ReturnType<typeof setCurrentIndex>
) {
  switch (action.type) {
    case SET_INDEX:
      return action.payload;
    default:
      return state;
  }
}

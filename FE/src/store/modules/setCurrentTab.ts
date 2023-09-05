export const SET_TAB = 'contents/SET_TAB';

export const setTab = (tab: number) => ({
  type: SET_TAB,
  payload: tab,
});

const initialState = 0;

export default function currentTab(
  state = initialState,
  action: ReturnType<typeof setTab>
) {
  switch (action.type) {
    case SET_TAB:
      return action.payload;
    default:
      return state;
  }
}

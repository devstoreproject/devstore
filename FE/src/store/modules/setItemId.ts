export const SET_ITEMID = 'contents/SET_ITEMID';

export const setItemId = (itemId: number) => ({
  type: SET_ITEMID,
  payload: itemId,
});

const initialState = 0;

export default function currentItemId(
  state = initialState,
  action: ReturnType<typeof setItemId>
) {
  switch (action.type) {
    case SET_ITEMID:
      return action.payload;
    default:
      return state;
  }
}

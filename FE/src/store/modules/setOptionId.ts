export const SET_OPTIONID = 'contents/SET_OPTIONID';

export const setOptionId = (optionId: number) => ({
  type: SET_OPTIONID,
  payload: optionId,
});

const initialState = 0;

export default function currentOptionId(
  state = initialState,
  action: ReturnType<typeof setOptionId>
) {
  switch (action.type) {
    case SET_OPTIONID:
      return action.payload;
    default:
      return state;
  }
}

import { combineReducers, legacy_createStore as createStore } from 'redux';
import currentAddress from './modules/setCurrentIndex';
import currentItemId from './modules/setItemId';

const reducers = combineReducers({
  currentAddress,
  currentItemId,
});

const store = createStore(reducers);

export default store;

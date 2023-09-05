import { combineReducers, legacy_createStore as createStore } from 'redux';
import currentAddress from './modules/setCurrentIndex';
import currentItemId from './modules/setItemId';
import currentTab from './modules/setCurrentTab';

const reducers = combineReducers({
  currentAddress,
  currentItemId,
  currentTab,
});

const store = createStore(reducers);

export default store;

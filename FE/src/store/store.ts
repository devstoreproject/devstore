import { combineReducers, legacy_createStore as createStore } from 'redux';
import currentAddress from './modules/setCurrentIndex';
import currentItemId from './modules/setItemId';
import currentTab from './modules/setCurrentTab';
import currentOptionId from './modules/setOptionId';

const reducers = combineReducers({
  currentAddress,
  currentItemId,
  currentTab,
  currentOptionId,
});

const store = createStore(reducers);

export default store;

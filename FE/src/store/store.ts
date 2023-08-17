import { legacy_createStore as createStore } from 'redux';
import currentAddress from './modules/setCurrentIndex';

const store = createStore(currentAddress);

export default store;

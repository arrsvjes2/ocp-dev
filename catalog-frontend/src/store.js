import {combineReducers, configureStore} from '@reduxjs/toolkit';
import {apiCategory, apiProduct} from './redux';

const appReducer = combineReducers({
  [apiCategory.reducerPath]: apiCategory.reducer,
  [apiProduct.reducerPath]: apiProduct.reducer,
})

const rootReducer = (state, action) => {
  return appReducer(state, action);
}

export const store = configureStore({
  reducer: rootReducer,
  middleware: getDefaultMiddleware => getDefaultMiddleware()
    .concat(apiCategory.middleware)
    .concat(apiProduct.middleware)
});

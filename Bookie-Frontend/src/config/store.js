import {applyMiddleware, combineReducers, compose, createStore} from 'redux';
import {reducer as formReducer} from 'redux-form';
import {routerMiddleware, routerReducer} from 'react-router-redux';
import createHistory from 'history/createBrowserHistory';
import {persistReducer, persistStore} from 'redux-persist'
import storage from 'redux-persist/lib/storage' // defaults to localStorage for web and AsyncStorage for react-native
import thunk from 'redux-thunk';
import usersReducer from "../reducers/users";
import hotelsReducer from "../reducers/hotels";
import reservationsReducer from "../reducers/reservations";
import reviewsReducer from "../reducers/reviews";
import notificationReducer from "../reducers/notification";


const rootReducer = combineReducers({
  users: usersReducer,
  hotels: hotelsReducer,
  reservations: reservationsReducer,
  reviews: reviewsReducer,
  notification: notificationReducer,

  form: formReducer,
  router: routerReducer,
});

export const history = createHistory();
const routeMiddleware = routerMiddleware(history);

const middleWares = [
  thunk,
  routeMiddleware
];

const persistConfig = {
  key: 'root',
  storage,
  blacklist: ['notification']
};

const persistedReducer = persistReducer(persistConfig, rootReducer);
const composeEnhancers =
  typeof window === 'object' &&
  window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ ?
    window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({
      // Specify extension’s options like name, actionsBlacklist, actionsCreators, serialize...
    }) : compose;

const enhancers = composeEnhancers(
  applyMiddleware(...middleWares),
  // other store enhancers if any
);

export const store = createStore(persistedReducer, enhancers);
export const persistor = persistStore(store);
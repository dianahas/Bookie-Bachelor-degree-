import React from "react";
import ReactDOM from "react-dom";
import {history, persistor, store} from "./config/store";
import {ConnectedRouter} from "react-router-redux";

import "assets/scss/material-kit-react.css";
import {Provider} from "react-redux";
import registerServiceWorker from "./registerServiceWorker";
import indexRoutes from "./routes";
import ConnectedSwitch from "./components/ConnectedSwitch";
import {PersistGate} from 'redux-persist/integration/react'
import {Route} from "react-router";


const app = (
  <Provider store={store}>
    <PersistGate loading={null} persistor={persistor}>
      <ConnectedRouter history={history}>
        <ConnectedSwitch>
          {indexRoutes.map((route, key) => {
            return <Route path={route.path} key={key} name={route.name} component={route.component}/>;
          })}
          <Route render={() => (<div>Not existing</div>)}/>
        </ConnectedSwitch>
      </ConnectedRouter>
    </PersistGate>

  </Provider>);

ReactDOM.render(app,
  document.getElementById("root")
);
registerServiceWorker();


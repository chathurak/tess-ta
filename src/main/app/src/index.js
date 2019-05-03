import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import MuiThemeProvider from "@material-ui/core/styles/MuiThemeProvider";
import theme from './index.theme'
import {BrowserRouter} from "react-router-dom";

ReactDOM.render(
  <BrowserRouter>
    <MuiThemeProvider theme={theme}>
      <App/>
    </MuiThemeProvider>
  </BrowserRouter>,
  document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();

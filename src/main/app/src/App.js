import React from 'react';
import PropTypes from 'prop-types';
import CssBaseline from '@material-ui/core/CssBaseline';
import {withStyles} from '@material-ui/core/styles';
import {Sidebar} from "./components/sidebar";
import {Header} from "./components/header";
import styles from './App.styles'
import {Route} from "react-router-dom";
import {Ocr} from "./scenes/ocr";
import {Home} from "./scenes/home";

class App extends React.Component {

  render() {
    const {classes} = this.props;

    return (
      <div className={classes.root}>
        <CssBaseline/>
        <Header/>
        <Sidebar/>
        <main className={classes.content}>
          <div className={classes.toolbar}/>
          <Route exact path='/home' component={Home}/>
          <Route exact path='/ocr' component={Ocr}/>
        </main>
      </div>
    );
  }
}

App.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles, {withTheme: true})(App);

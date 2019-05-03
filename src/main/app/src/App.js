import React from 'react';
import PropTypes from 'prop-types';
import CssBaseline from '@material-ui/core/CssBaseline';
import { withStyles } from '@material-ui/core/styles';
import {Sidebar} from "./sidebar";
import {Header} from "./header";
import styles from './App.styles'
import {Route} from "react-router-dom";
import {Ocr} from "./ocr";
import {Home} from "./home";

class App extends React.Component {

  render() {
    const { classes } = this.props;

    return (
      <div className={classes.root}>
        <CssBaseline />
        <Header></Header>
        <Sidebar></Sidebar>
        <main className={classes.content}>
          <div className={classes.toolbar} />
          <Route exact path='/home' component={Home}/>
          <Route path='/ocr' component={Ocr}/>
        </main>
      </div>
    );
  }
}

App.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles, { withTheme: true })(App);

import CssBaseline  from '@material-ui/core/CssBaseline'
import {withStyles} from '@material-ui/core/styles'
import PropTypes    from 'prop-types'
import React        from 'react'
import {Route}      from 'react-router-dom'
import styles       from './App.styles'
import {Header}     from './components/header'
import {Sidebar}    from './components/sidebar'
import {Home}       from './scenes/home'
import {Ocr}        from './scenes/ocr'

class App extends React.Component {

  render() {
    const {classes} = this.props

    // return (
    //   <div className={classes.root}>
    //     <CssBaseline/>
    //     <Header/>
    //     <Sidebar/>
    //     <main className={classes.content}>
    //       <div className={classes.toolbar}/>
    //       <Route exact path='/home' component={Home}/>
    //       <Route exact path='/ocr' component={Ocr}/>
    //     </main>
    //   </div>
    // )

    return (
      <div>
        {this.props.children}
      </div>
    )
  }

}

App.propTypes = {
  classes: PropTypes.object.isRequired,
  children: PropTypes.object.isRequired
}

export default withStyles(styles, {withTheme: true})(App)

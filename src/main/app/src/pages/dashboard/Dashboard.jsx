import {withStyles} from '@material-ui/core'
import CssBaseline  from '@material-ui/core/CssBaseline'
import PropTypes    from 'prop-types'
import * as React   from 'react'
import {Route}      from 'react-router-dom'
import Header       from './components/header/Header'
import Sidebar      from './components/sidebar/Sidebar'
import Home         from './scenes/home/Home'
import Library      from './scenes/library/Library'
import Ocr          from './scenes/ocr/Ocr'
import Grammar      from './scenes/grammar/Grammar'
import Reports     from './scenes/reports/Reports'
import Settings     from './scenes/settings/Settings'
import {styles}     from './styles'

class Dashboard extends React.Component {

    render() {
        const {classes} = this.props

        return (
            <div className={classes.root}>
                <CssBaseline/>
                <Header/>
                <Sidebar/>
                <main className={classes.content}>
                    <div className={classes.toolbar}/>
                    <Route exact path='/home' component={Home}/>
                    <Route exact path='/library' component={Library}/>
                    <Route exact path='/ocr' component={Ocr}/>
                    <Route exact path='/grammar' component={Grammar}/>
                    <Route exact path='/reports' component={Reports}/>
                    <Route exact path='/settings' component={Settings}/>
                </main>
            </div>
        )
    }

}

Dashboard.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(Dashboard)

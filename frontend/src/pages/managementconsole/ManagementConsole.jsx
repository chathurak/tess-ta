import {withStyles} from '@material-ui/core/styles'
import PropTypes from 'prop-types'
import * as React from 'react'
import {styles} from './styles'
import Header from "../../components/header/Header";
import Sidebar from "../../components/sidebar/Sidebar";
import {lazy, Suspense} from "react";
import {Route, Router, Switch, withRouter} from 'react-router-dom'

const Home = lazy(() => import(/* webpackChunkName: "Home" */ '../home/Home'))
const Library = lazy(() => import(/* webpackChunkName: "Library" */ '../library/Library'))
const Ocr = lazy(() => import(/* webpackChunkName: "Ocr" */ '../ocr/Ocr'))
const Spellcheck = lazy(() => import(/* webpackChunkName: "Spellcheck" */ '../spellcheck/Spellcheck'))
const Reports = lazy(() => import(/* webpackChunkName: "Reports" */ '../reports/Reports'))
const Settings = lazy(() => import(/* webpackChunkName: "Settings" */ '../settings/Settings'))

class ManagementConsole extends React.Component {

    constructor(props) {
        super(props)
    }

    render() {
        const {classes} = this.props

        return (
            <Suspense fallback={<div>Loading...</div>}>
                <div className={classes.adminpanel}>
                    <Header/>
                    <Sidebar/>
                    <main className={classes.content}>
                        <div className={classes.toolbar}/>
                        <Suspense fallback={<div>Loading...</div>}>
                            <Switch>
                                <Route path='/home' component={Home}/>
                                <Route path='/library' component={Library}/>
                                <Route path='/ocr' component={Ocr}/>
                                <Route path='/spellcheck' component={Spellcheck}/>
                                <Route path='/reports' component={Reports}/>
                                <Route path='/settings' component={Settings}/>
                                <Route path='/' component={Home}/>
                            </Switch>
                        </Suspense>
                    </main>
                </div>
            </Suspense>
        )
    }

}

ManagementConsole.propTypes = {
    classes: PropTypes.object.isRequired,
    theme: PropTypes.object.isRequired,
}

export default withRouter((withStyles(styles, {withTheme: true})(ManagementConsole)))

import {withStyles}     from '@material-ui/core/styles'
import Divider          from '@material-ui/core/Divider/index'
import Drawer           from '@material-ui/core/Drawer/index'
import Hidden           from '@material-ui/core/Hidden/index'
import List             from '@material-ui/core/List/index'
import ListItem         from '@material-ui/core/ListItem/index'
import ListItemIcon     from '@material-ui/core/ListItemIcon/index'
import ListItemText     from '@material-ui/core/ListItemText/index'
import ListSubheader    from '@material-ui/core/ListSubheader/index'
import FindInPageIcon   from '@material-ui/icons/FindInPage'
import HomeIcon         from '@material-ui/icons/Home'
import LibraryBooksIcon from '@material-ui/icons/LibraryBooks'
import ListAltIcon      from '@material-ui/icons/ListAlt'
import QueueIcon        from '@material-ui/icons/Queue'
import SettingsIcon     from '@material-ui/icons/Settings'
import ExitToAppIcon    from '@material-ui/icons/ExitToApp'
import SpellcheckIcon   from '@material-ui/icons/Spellcheck'
import PropTypes        from 'prop-types'
import * as React       from 'react'
import {Link}           from 'react-router-dom'
import {styles}         from './styles'
import {ACCESS_TOKEN}   from '../../constants/auth.constants'


class Sidebar extends React.Component {

    state = {
        mobileOpen: false
    }

    handleDrawerToggle = () => {
        this.setState(state => ({mobileOpen: !state.mobileOpen}))
    }

    handleLogout = () => {
        localStorage.removeItem(ACCESS_TOKEN)
        this.setState({
            authenticated: false,
            currentUser  : null
        })
        console.log('You are safely logged out!')
    }

    render() {
        const {classes, theme} = this.props

        const drawer = (
            <div>
                <List
                    component="nav"
                    subheader={<ListSubheader component="div" style={{color: 'white'}}>Tess-TA</ListSubheader>}
                >
                    <ListItem className={classes.drawerButton} button component={Link} to="/home">
                        <ListItemIcon>
                            <HomeIcon className={classes.drawerIcon}/>
                        </ListItemIcon>
                        <ListItemText primary="Home"/>
                    </ListItem>
                    <ListItem className={classes.drawerButton} button component={Link} to="/library">
                        <ListItemIcon>
                            <LibraryBooksIcon className={classes.drawerIcon}/>
                        </ListItemIcon>
                        <ListItemText primary="Library"/>
                    </ListItem>
                    <ListItem className={classes.drawerButton} button component={Link} to="/queue">
                        <ListItemIcon>
                            <QueueIcon className={classes.drawerIcon}/>
                        </ListItemIcon>
                        <ListItemText primary="Queue"/>
                    </ListItem>
                    <Divider/>
                    <ListItem className={classes.drawerButton} button component={Link} to="/ocr">
                        <ListItemIcon>
                            <FindInPageIcon className={classes.drawerIcon}/>
                        </ListItemIcon>
                        <ListItemText primary="OCR"/>
                    </ListItem>
                    <ListItem className={classes.drawerButton} button component={Link} to="/spellcheck">
                        <ListItemIcon>
                            <SpellcheckIcon className={classes.drawerIcon}/>
                        </ListItemIcon>
                        <ListItemText primary="Spell Check"/>
                    </ListItem>
                    <Divider/>
                    <ListItem className={classes.drawerButton} button component={Link} to="/reports">
                        <ListItemIcon>
                            <ListAltIcon className={classes.drawerIcon}/>
                        </ListItemIcon>
                        <ListItemText primary="Reports"/>
                    </ListItem>
                    <Divider/>
                    <ListItem className={classes.drawerButton} button component={Link} to="/settings">
                        <ListItemIcon>
                            <SettingsIcon className={classes.drawerIcon}/>
                        </ListItemIcon>
                        <ListItemText primary="Settings"/>
                    </ListItem>
                    <Divider/>
                    <ListItem className={classes.signOutButton} button component={Link} to="/test">
                        <ListItemIcon>
                            <ExitToAppIcon className={classes.signOutIcon}/>
                        </ListItemIcon>
                        <ListItemText primary="Sign Out"/>
                    </ListItem>
                </List>
            </div>
        )

        return (
            <nav className={classes.drawer}>
                {/* The implementation can be swapped with js to avoid SEO duplication of links. */}
                <Hidden smUp implementation="css">
                    <Drawer
                        // container={this.props.container}
                        variant="temporary"
                        anchor={theme.direction === 'rtl' ? 'right' : 'left'}
                        open={this.state.mobileOpen}
                        onClose={this.handleDrawerToggle}
                        classes={{
                            paper: classes.drawerPaper
                        }}
                    >
                        {drawer}
                    </Drawer>
                </Hidden>
                <Hidden xsDown implementation="css">
                    <Drawer
                        classes={{
                            paper: classes.drawerPaper
                        }}
                        variant="permanent"
                        open
                    >
                        {drawer}
                    </Drawer>
                </Hidden>
            </nav>
        )
    }

}

Sidebar.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired
}

export default withStyles(styles, {withTheme: true})(Sidebar)

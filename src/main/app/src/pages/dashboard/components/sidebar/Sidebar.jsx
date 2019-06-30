import {withStyles}     from '@material-ui/core'
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
import SpellcheckIcon   from '@material-ui/icons/Spellcheck'
import PropTypes        from 'prop-types'
import * as React       from 'react'
import {Link}           from 'react-router-dom'
import {styles}         from './styles'


class Sidebar extends React.Component {

    state = {
        mobileOpen: false,
    }

    handleDrawerToggle = () => {
        this.setState(state => ({mobileOpen: !state.mobileOpen}))
    }

    render() {
        const {classes, theme} = this.props

        const drawer = (
            <div>
                <List
                    component="nav"
                    subheader={<ListSubheader component="div">Tess-TA</ListSubheader>}
                >
                    <ListItem button component={Link} to="/home">
                        <ListItemIcon>
                            <HomeIcon/>
                        </ListItemIcon>
                        <ListItemText primary="Home"/>
                    </ListItem>
                    <ListItem button component={Link} to="/library">
                        <ListItemIcon>
                            <LibraryBooksIcon/>
                        </ListItemIcon>
                        <ListItemText primary="Library"/>
                    </ListItem>
                    <ListItem button component={Link} to="/queue">
                        <ListItemIcon>
                            <QueueIcon/>
                        </ListItemIcon>
                        <ListItemText primary="Queue"/>
                    </ListItem>
                    <Divider/>
                    <ListItem button component={Link} to="/ocr">
                        <ListItemIcon>
                            <FindInPageIcon/>
                        </ListItemIcon>
                        <ListItemText primary="OCR"/>
                    </ListItem>
                    <ListItem button component={Link} to="/grammar">
                        <ListItemIcon>
                            <SpellcheckIcon/>
                        </ListItemIcon>
                        <ListItemText primary="Grammar"/>
                    </ListItem>
                    <Divider/>
                    <ListItem button component={Link} to="/reports">
                        <ListItemIcon>
                            <ListAltIcon/>
                        </ListItemIcon>
                        <ListItemText primary="Reports"/>
                    </ListItem>
                    <Divider/>
                    <ListItem button component={Link} to="/settings">
                        <ListItemIcon>
                            <SettingsIcon/>
                        </ListItemIcon>
                        <ListItemText primary="Settings"/>
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
                            paper: classes.drawerPaper,
                        }}
                    >
                        {drawer}
                    </Drawer>
                </Hidden>
                <Hidden xsDown implementation="css">
                    <Drawer
                        classes={{
                            paper: classes.drawerPaper,
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
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(Sidebar)

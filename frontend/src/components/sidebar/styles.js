export const styles = theme => ({
    drawer       : {
        [theme.breakpoints.up('sm')]: {
            width     : theme.app.dashboard.layout.sidebarWidth,
            flexShrink: 0
        }
    },
    drawerPaper  : {
        width          : theme.app.dashboard.layout.sidebarWidth,
        backgroundColor: '#2f3640',
        color          : '#ffffff'
    },
    drawerIcon   : {
        color: '#ffffff'
    },
    signOutIcon  : {
        color: '#000000'
    },
    drawerButton : {
        '&:hover': {
            backgroundColor: '#3a424b'
        }
    },
    signOutButton: {
        backgroundColor: '#e1b12c',
        color          : 'black',
        '&:hover'      : {
            backgroundColor: '#f0c02c'
        }
    }
})

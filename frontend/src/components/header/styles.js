export const styles = theme => ({
    appBar    : {
        marginLeft                  : theme.app.dashboard.layout.sidebarWidth,
        [theme.breakpoints.up('sm')]: {
            width: `calc(100% - ${theme.app.dashboard.layout.sidebarWidth}px)`
        }
    },
    menuButton: {
        marginRight                 : 20,
        [theme.breakpoints.up('sm')]: {
            display: 'none'
        }
    }
})

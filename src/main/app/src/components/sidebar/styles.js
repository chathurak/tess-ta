export const styles = theme => ({
    drawer     : {
        [theme.breakpoints.up('sm')]: {
            width     : theme.app.dashboard.layout.sidebarWidth,
            flexShrink: 0,
        },
    },
    drawerPaper: {
        width: theme.app.dashboard.layout.sidebarWidth,
    },
})
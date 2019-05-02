const styles = theme => ({
  root: {
    display: 'flex'
  },
  appBar: {
    marginLeft: theme.layout.sidebarWidth,
    [theme.breakpoints.up('sm')]: {
      width: `calc(100% - ${theme.layout.sidebarWidth}px)`,
    },
  },
  menuButton: {
    marginRight: 20,
    [theme.breakpoints.up('sm')]: {
      display: 'none',
    },
  },
  toolbar: theme.mixins.toolbar,
  content: {
    flexGrow: 1,
    padding: theme.spacing.unit * 3,
  },
});

export default styles

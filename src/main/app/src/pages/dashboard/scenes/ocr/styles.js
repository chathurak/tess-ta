const styles = theme => ({
    root: {
        display      : 'flex',
        flexDirection: 'row',
    },
    taskPanel: {
        display      : 'inline-flex',
        flexDirection: 'column',
        flexGrow     : 1,

        border          : theme.border.defaultBorder,
        borderRightWidth : 0
    },
    optionPanel: {
        display  : 'inline-flex',
        flexDirection: 'row',
        flexBasis: '350px',
        flexGrow : 0,

        border: theme.border.defaultBorder,
    }
})

export default styles

const styles = theme => ({
    ocrRoot: {
        height: '95%',

        display      : 'flex',
        flexDirection: 'row',
    },
    taskPanel: {
        height: '100%',

        display      : 'inline-flex',
        flexDirection: 'column',
        flexGrow     : 1,

        border          : theme.border.defaultBorder,
        borderRightWidth : 0
    },
    optionPanel: {
        height: '100%',

        display  : 'inline-flex',
        flexBasis: '350px',
        flexGrow : 0,

        border: theme.border.defaultBorder
    }
})

export default styles

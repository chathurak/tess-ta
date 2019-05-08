const styles = theme => ({
    ocrRoot: {
        height: '95%',

        display      : 'flex',
        flexDirection: 'row',
    },

    contentPane: {
        height: '100%',

        display      : 'inline-flex',
        flexDirection: 'column',
        flexGrow     : 1,
    },
    actionPanel: {
        display  : 'inline-flex',
        flexBasis: '60px',
        flexGrow : 0,

        border           : theme.border.defaultBorder,
        borderBottomWidth: 0,
        borderRightWidth : 0
    },
    taskPanel  : {
        display : 'inline-flex',
        flexGrow: 1,

        border          : theme.border.defaultBorder,
        borderRightWidth: 0
    },

    optionsPane: {
        height: '100%',

        display  : 'inline-flex',
        flexBasis: '350px',
        flexGrow : 0,

        border: theme.border.defaultBorder
    }
})

export default styles

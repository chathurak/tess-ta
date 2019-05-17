const styles = theme => ({
    root: {
        display      : 'flex',
        flexDirection: 'row',
    },
    taskPanel: {
        display      : 'inline-flex',
        flexDirection: 'column',
        flexGrow     : 1,

        border          : theme.app.border.defaultBorder,
        borderRightWidth : 0
    },
    optionPanel: {
        display  : 'inline-flex',
        flexDirection: 'row',
        flexBasis: theme.app.ocr.layout.optionPanelWidth,
        flexGrow : 0,

        border: theme.app.border.defaultBorder,
    }
})

export default styles

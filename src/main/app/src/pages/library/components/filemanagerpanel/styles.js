export const styles = (theme) => ({
    documentPane: {
        display      : 'flex',
        flexDirection: 'column',

        marginTop: theme.spacing(2),
    },

    documentPaneTitle: {
        display : 'inline-flex',
        flexGrow: 0,

        fontSize: '0.855rem',

        paddingLeft: theme.spacing(2),
    },

    documentPaneTitleName: {
        marginTop: 0
    },

    document: {
        display      : 'inline-flex',
        flexDirection: 'row',
        flexGrow     : 0,

        alignItems: 'center',

        marginBottom: '-1px',

        border     : theme.app.common.border.defaultBorder,
        borderLeft : 0,
        borderRight: 0,

        fontSize: '0.855rem',
        color   : '#717171',

        userSelect: 'None',
        cursor    : 'pointer',

        '&:hover': {
            backgroundColor: '#FAFAFA'
        }
    },

    documentIcon: {
        display : 'inline-flex',
        flexGrow: 0,

        marginLeft : theme.spacing(2),
        marginRight: theme.spacing(2),
    },

    documentName: {
        display : 'inline-flex',
        flexGrow: 1,

        paddingLeft: theme.spacing(1),

        fontWeight: '500'
    },

    documentDeleteIcon: {
        display : 'inline-flex',
        flexGrow: 0,

        marginLeft : theme.spacing(2),
        marginRight: theme.spacing(2),

        borderRadius: '50%',

        fontSize  : '1.7rem',
        fontWeight: '500',

        '&:hover': {
            backgroundColor: '#BDBDBD',

            color: '#212121'
        }
    },
})
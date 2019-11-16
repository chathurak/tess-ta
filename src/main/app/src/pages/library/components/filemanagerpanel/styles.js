const styles = {
    expansionPanel: {
        root: {
            border: '1px solid rgba(0,0,0,.125)',
            boxShadow: 'none',
            '&:not(:last-child)': {
                borderBottom: 0,
            },
            '&:before': {
                display: 'none',
            },
        },
        expanded: {
            margin: 'auto',
        },
    },
    expansionPanelSummary: {
        root: {
            backgroundColor: 'rgba(0,0,0,.03)',
            borderBottom: '1px solid rgba(0,0,0,.125)',
            marginBottom: -1,
            minHeight: 56,
            '&$expanded': {
                minHeight: 56,
            },
        },
        content: {
            '&$expanded': {
                margin: '12px 0',
            },
        },
        expanded: {},
    },
    expansionPanelDetails: {
        root: {
            padding: 20,
        },
    },
    table: {
        root: {
            width: '100%',
            marginTop: 30,
            overflowX: 'auto',
        },
        table: {
            minWidth: 700,
        },
    },
    buttonBox: {
        display: 'flex',
        flexDirection: 'row'
    },
    button: {
        marginLeft: 5,
    }
}

export default styles
export const styles = (theme) => ({
    paper : {
        marginTop    : theme.spacing(8),
        display      : 'flex',
        flexDirection: 'column',
        alignItems   : 'center',
        padding: theme.spacing(3),
    },
    avatar: {
        margin         : theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form  : {
        width    : '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    submit: {
        marginTop: theme.spacing(3),
    },
})
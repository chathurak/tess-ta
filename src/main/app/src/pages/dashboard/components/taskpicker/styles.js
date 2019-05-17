export const styles = (theme) => ({
    root          : {
        width: `${600 + (theme.spacing.unit * 2 * 4)}px`
    },
    section1      : {
        padding      : theme.spacing.unit * 2,
        paddingBottom: 0
    },
    section2      : {
        padding: theme.spacing.unit * 2
    },
    datetimepicker: {
        width: '150px',

        marginRight: theme.spacing.unit * 4,
    },
    select        : {
        width: '300px',

        marginRight: theme.spacing.unit * 2,
    }
})
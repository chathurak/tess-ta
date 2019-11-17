import {spacing} from '../shared'

export const layout = {
    root          : {
        display      : 'flex',
        flexDirection: 'row',

        backgroundColor: '#FFFFFF'
    },
    workspacePanel: {
        display      : 'inline-flex',
        flexDirection: 'column',
        flexGrow     : 1,

        padding: spacing(1)
    },
    optionPanel   : {
        width: 350,

        display      : 'inline-flex',
        flexDirection: 'row',
        flexBasis    : 350,
        flexGrow     : 0
    }
}

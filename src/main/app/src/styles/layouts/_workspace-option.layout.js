import {layout as common} from '../_common'

export const layout = {
    root          : {
        display      : 'flex',
        flexDirection: 'row',
    },
    workspacePanel: {
        display      : 'inline-flex',
        flexDirection: 'column',
        flexGrow     : 1,

        border          : common.border.defaultBorder,
        borderRightWidth: 0
    },
    optionPanel   : {
        width: 350,

        display      : 'inline-flex',
        flexDirection: 'row',
        flexBasis    : 350,
        flexGrow     : 0,

        border: common.border.defaultBorder,
    }
}
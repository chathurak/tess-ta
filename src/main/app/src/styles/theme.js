import {createMuiTheme}                from '@material-ui/core'
import {lightBlue}                     from '@material-ui/core/colors/index'
import {layout as common}              from './_common'
import {layout as fileWorkspaceLayout} from './layouts/_workspace-option.layout'

export const theme = createMuiTheme({
    palette   : {
        primary  : lightBlue,
        secondary: {
            main: '#1976d2',
        },
    },
    typography: {
        useNextVariants: true,
        fontSize       : 13,
    },
    app       : {
        common,

        layout: {
            fileWorkspaceLayout
        },

        dashboard: {
            layout: {
                sidebarWidth: 240
            }
        },
    }
})
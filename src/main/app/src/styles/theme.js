import {createMuiTheme}                from '@material-ui/core'
import {lightBlue}                     from '@material-ui/core/colors/index'
import {layout as common}              from './common'
import {layout as fileWorkspaceLayout} from './layouts/workspace-option.layout'

export const theme = createMuiTheme({
    palette   : {
        primary  : lightBlue,
        secondary: {
            main: '#1976d2',
        },
    },
    typography: {
        // useNextVariants: true,
        fontSize: 12,
    },
    spacing   : factor => `${0.5 * factor}rem`,
    app       : {
        common,

        layout: {
            fileWorkspaceLayout
        },

        dashboard: {
            layout: {
                sidebarWidth: 200
            }
        },
    }
})
import {createMuiTheme}                from '@material-ui/core'
import {lightBlue}                     from '@material-ui/core/colors/index'
import {spacing}                       from './shared'
import {layout as fileWorkspaceLayout} from './layouts/workspace-option.layout'

export const theme = createMuiTheme({
    palette   : {
        primary  : lightBlue,
        secondary: {
            main: '#1976d2'
        }
    },
    typography: {
        // useNextVariants: true,
        fontSize: 12
    },
    spacing   : spacing,
    app       : {
        common: {
            border: {
                defaultBorder: 'solid #CFD8DC 1px'
            }
        },

        layout: {
            fileWorkspaceLayout
        },

        // custom
        dashboard: {
            layout: {
                sidebarWidth: 200
            }
        }
    }
})

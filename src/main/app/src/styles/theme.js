import {createMuiTheme}                from '@material-ui/core/styles'
import {spacing}                       from './shared'
import {layout as fileWorkspaceLayout} from './layouts/workspace-option.layout'

export const theme = createMuiTheme({
    palette   : {
        primary  : {
            main: '#0097e6'
        },
        secondary: {
            main: '#718093'
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
                sidebarWidth: 230
            }
        }
    }
})

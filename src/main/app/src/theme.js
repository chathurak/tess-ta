import {createMuiTheme} from '@material-ui/core'
import {lightBlue}      from '@material-ui/core/colors'

const theme = createMuiTheme({
    palette   : {
        primary  : lightBlue,
        secondary: {
            main: '#1976d2',
        },
    },
    typography: {
        useNextVariants: true,
        fontSize: 12,
    },
    app: {
        border    : {
            defaultBorder: 'solid #CFD8DC 1px',
        },

        dashboard : {
            layout    : {
                sidebarWidth: 240
            }
        },
        ocr : {
            layout : {
                optionPanelWidth: 350
            }
        }
    }
})

export default theme

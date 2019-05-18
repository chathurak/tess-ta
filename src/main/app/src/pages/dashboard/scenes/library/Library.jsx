import {withStyles} from '@material-ui/core'
import * as React   from 'react'
import TaskPanel    from './components/filemanagerpanel/FileManagerPanel'
import OptionPanel  from './components/optionpanel/OptionPanel'
import Grid         from '@material-ui/core/Grid';
import {styles}     from './styles'

class Library extends React.Component {

    render() {
        const {classes} = this.props

        return (
            <div className={classes.root}>
                <Grid container spacing={24}>
                    <Grid item xs={8}>
                        <TaskPanel className={classes.taskPanel}/>
                    </Grid>
                    <Grid item xs={4}>
                        <OptionPanel className={classes.optionPanel}/>
                    </Grid>
                </Grid>
            </div>
        )
    }

}

export default withStyles(styles, {withTheme: true})(Library)

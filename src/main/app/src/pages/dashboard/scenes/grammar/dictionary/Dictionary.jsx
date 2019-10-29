import {withStyles}  from '@material-ui/core'
import * as React    from 'react'
import {styles}      from './styles'
import ListItem      from '@material-ui/core/ListItem';
import ListItemText  from '@material-ui/core/ListItemText';
import { FixedSizeList} from 'react-window';
import Paper         from '@material-ui/core/Paper';
import AddIcon       from '@material-ui/icons/Add';
import Icon          from '@material-ui/core/Icon';
import DeleteIcon    from '@material-ui/icons/Delete';
import IconButton    from '@material-ui/core/IconButton';


class Dictionary extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            mainDictionary: {
                words: ["Word 1", "Word 2", "Word 3"],   // TODO: Temp
                size : 3
            }
        };
    }

    mainDictionaryWords = (props) => {
        const { index, style } = props;
        return (
            <ListItem button style = {style} key = {index}>
                <ListItemText
                    primary = {this.state.mainDictionary.words[index]}
                />
            </ListItem>
        );
    };

    handleAdd = (event) => {
        // Add item
    };

    handleDelete = (event) => {
        // Delete item
    };

    handleEdit = (event) => {
        // Edit item
    };

    render() {
        const { classes } = this.props;

        return (
            <div>
                <div   className = {classes.root}>
                <Paper className = {classes.paper}>
                        <center>
                            <h3>Main</h3>
                        </center>
                        <FixedSizeList
                            height    = {400}
                            itemSize  = {46}
                            itemCount = {this.state.mainDictionary.size}
                        >
                            {this.mainDictionaryWords}
                        </FixedSizeList>

                        <div className = {classes.buttonBar}>
                            <IconButton
                                aria-label = "Delete"
                                className  = {classes.margin}
                                onClick    = {this.handleAdd}
                            >
                                <AddIcon fontSize = "large" />
                            </IconButton>
                            <IconButton
                                aria-label = "Delete"
                                className  = {classes.margin}
                                onClick    = {this.handleEdit}
                            >
                                <Icon>edit_icon</Icon>
                            </IconButton>
                            <IconButton
                                aria-label = "Delete"
                                className  = {classes.margin}
                                onClick    = {this.handleDelete}
                            >
                                <DeleteIcon fontSize = "large" />
                            </IconButton>
                        </div>
                    </Paper>
                </div>
            </div>
        );
    }
}

export default withStyles(styles, { withTheme: true })(Dictionary);

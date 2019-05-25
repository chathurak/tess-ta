import { withStyles } from "@material-ui/core";
import * as React     from "react";
import { styles }     from "./styles";
import AppBar         from "@material-ui/core/AppBar";
import Tabs           from "@material-ui/core/Tabs";
import Tab            from "@material-ui/core/Tab";
import GrammarChecker from "./grammarchecker/GrammarChecker";
import Dictionary     from "./dictionary/Dictionary";
import Rules          from "./rules/Rules";

class Grammar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            selectedTab: 0
        };
    }

    handleChange = (event, value) => {
        this.setState({ selectedTab: value });
    };

    render() {
        const { classes } = this.props;

        return (
            <div className={classes.root}>
                <AppBar position="static" color="default">
                    <Tabs value={this.state.selectedTab} onChange={this.handleChange}>
                        <Tab label="Grammar Checker" />
                        <Tab label="Dictionary" />
                        <Tab label="Rules" />
                    </Tabs>
                </AppBar>
                {this.state.selectedTab === 0 && <GrammarChecker />}
                {this.state.selectedTab === 1 && <Dictionary />}
                {this.state.selectedTab === 2 && <Rules />}
            </div>
        );
    }
}

export default withStyles(styles, { withTheme: true })(Grammar);

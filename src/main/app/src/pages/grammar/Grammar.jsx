import { withStyles } from "@material-ui/core";
import * as React     from "react";
import { styles }     from "./styles";
import AppBar         from "@material-ui/core/AppBar";
import Tabs           from "@material-ui/core/Tabs";
import Tab            from "@material-ui/core/Tab";
import GrammarChecker from "./grammarchecker/GrammarChecker";
import Dictionary     from "./dictionary/Dictionary";
import Exblock        from "./exblock/Exblock";
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
                        <Tab label="Word Inventory" />
                        <Tab label="Extended Block" />
                        <Tab label="Rules" />
                    </Tabs>
                </AppBar>

                {/* Grammar tab content */}
                <div className={this.state.selectedTab !== 0 ? classes.hidden : ''}>
                    <GrammarChecker />
                </div>

                {/* Dictionary tab content */}
                <div className={this.state.selectedTab !== 1 ? classes.hidden : ''}>
                    <Dictionary />
                </div>


                {/* Exblock tab content */}
                <div className={this.state.selectedTab !== 2 ? classes.hidden : ''}>
                    <Exblock />
                </div>

                {/* Rules tab content */}
                <div className={this.state.selectedTab !== 3 ? classes.hidden : ''}>
                    <Rules />
                </div>
            </div>
        );
    }
}

export default withStyles(styles, { withTheme: true })(Grammar);

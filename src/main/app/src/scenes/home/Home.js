import * as React from "react";
import styles from "./Home.styles";
import {withStyles} from "@material-ui/core";

class Home extends React.Component{

  render() {
    return(
      <h1>Home</h1>
    )
  }

}

export default withStyles(styles, { withTheme: true })(Home);

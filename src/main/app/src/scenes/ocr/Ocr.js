import * as React from "react";
import styles from "./Ocr.styles";
import {withStyles} from "@material-ui/core";

class Ocr extends React.Component{

  render() {
    return(
      <h1>Ocr</h1>
    )
  }

}

export default withStyles(styles, { withTheme: true })(Ocr);

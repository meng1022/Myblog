import {Grid} from "@mui/material";
import * as React from "react";
import JavaMail from "./JavaMail";

function Demo(){
    return(
        <Grid item md={8} sm={11} xs={11} sx={{mr:'2em',mt:'2em',ml:'0.2em'}}>
            <JavaMail/>
        </Grid>
    );

}

export default Demo;
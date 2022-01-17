import {Typography,Grid} from "@mui/material";

function ErrorPage(){
    return(
        <Grid item md={8}>
            <Typography variant={"h5"} sx={{mt:'3em', textAlign:'center'}}>
                Sorry, this page is lost.
            </Typography>
        </Grid>
    );
}
export default ErrorPage;
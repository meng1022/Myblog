import {Box,Link, Grid, Typography, List, ListItem, Paper, Stack, Divider} from "@mui/material";

import * as React from "react";
import {Link as RouterLink} from 'react-router-dom';

function FunctionList(){
    if(sessionStorage.getItem("Userid")==="84921724")
        return(
            <Box>
                <Divider/>
                <List>
                    <ListItem><Link sx={{textDecoration:'none'}} href={"#"}>Post an article</Link></ListItem>
                    <ListItem><Link sx={{textDecoration:'none'}} href={"#"}>Create a module</Link></ListItem>
                </List>
            </Box>
        );
    return null;
}

function SideBar(props){
    const {modules,networks} = props;

    return(
        <Grid item md={3} sm={11} xs={11} sx={{mt:'1em',ml:'1em'}}>
            <Paper elevation={0} sx={{ p: 2, bgcolor: '#fbe9e7', mb:'2em'}}>
                <Typography variant="h6" gutterBottom>
                    About
                </Typography>
                <Typography>This blog post shows a few different types of content that are
                    supported and styled with Material styles.
                    Basic typography, images, and code are all supported.
                    You can extend these by modifying Markdown.js.</Typography>
            </Paper>
            <Divider/>
            <Typography  variant={"h6"}>
                Topics and Modules
            </Typography>
            <List>
            {modules.map((module)=>(
                <ListItem key={module.id}>
                    <Link sx={{textDecoration:'none'}}
                          component={RouterLink}
                          to={`/getmodulearticles/${module.id}`}>
                            {module.modulename}
                    </Link>
                </ListItem>
            ))}
            </List>
            <Divider/>
            <Typography variant={"h6"}>
                Other
            </Typography>
            <List>
            {networks.map((network)=>(
                <ListItem key={network.name}>
                    <Link
                        display="block"
                        variant="body1"
                        href={network.url}
                        target={"_blank"}
                        sx={{ mb: 0.5 ,textDecoration:'none'}}
                    >
                        <Stack direction="row" spacing={0.5} alignItems="center">
                            <network.icon/>
                            <span >{network.name}</span>
                        </Stack>
                    </Link>
                </ListItem>
            ))}
        </List>
            <FunctionList/>
        </Grid>
    );
}

export default SideBar;
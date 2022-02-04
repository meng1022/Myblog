import {
    Box,
    Link,
    Grid,
    Typography,
    List,
    ListItem,
    Paper,
    Stack,
    Divider,
    Button,
    DialogTitle,
    DialogActions, Dialog, TextField
} from "@mui/material";
import * as React from "react";
import {Link as RouterLink} from 'react-router-dom';
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";

function DeleteModule(props){
    const {moduleid} = props;
    const [open,setOpen] = React.useState(false);
    const handleClickOpen = ()=>{
        setOpen(true);
    };
    const handleClose = ()=>{
        setOpen(false);
    };

    function handleDelete(){
        (async()=>{
            const response = await fetch("/deletemodule",{
                method:'POST',
                headers:{
                    'Accept':'application/json',
                    'Content-Type':'application/json',
                },
                body: JSON.stringify({moduleid:moduleid})
            });
            const body = await response.json();
            console.log(body.data);
        })();
        setOpen(false);
    };

    if(sessionStorage.getItem("__USER_ID__")==="84921724")
        return (
            <Button>
                <DeleteOutlineIcon onClick={handleClickOpen}/>
                <Dialog open={open} onClose={handleClose}>
                    <DialogTitle>
                        Are you sure to delete this module?
                    </DialogTitle>
                    <DialogActions>
                        <Button onClick={handleDelete} href={"/homepage"}>Delete</Button>
                        <Button onClick={handleClose}>Dismiss</Button>
                    </DialogActions>
                </Dialog>
            </Button>
        );
    return null;
}

function FunctionList(){
    const [open,setOpen] = React.useState(false);
    const [modulename,setModuleName] = React.useState("");
    const handleOpen = ()=>{
        setOpen(true);
    };
    const handleClose = ()=>{
        setOpen(false);
    };
    const handleChange = (event)=>{
        setModuleName(event.target.value);
    };
    const handleSubmit = ()=>{
        (async()=>{
            const response = await fetch("/createmodule",{
                method:'POST',
                headers:{
                    'Accept':'application/json',
                    'Content-Type':'application/json',
                },
                body: JSON.stringify({modulename:modulename}),
            });
            const body = response.json();
            console.log(body.data);
        })();
        setOpen(false);
    }
    if(sessionStorage.getItem("__USER_ID__")==="84921724"){
        return(
            <Box>
                <Divider/>
                <Button sx={{ml:'0.5em',fontFamily:'MyFont2',textTransform:'none',fontSize:18}} component={RouterLink} to={"/writearticle"}>Post an article</Button>
                <Button sx={{ml:'0.5em',fontFamily:'MyFont2',textTransform:'none',fontSize:18}} onClick={handleOpen}>Create a module</Button>
                <Dialog open={open} onClose={handleClose}>
                    <DialogTitle>Please input the new module name:</DialogTitle>
                    <TextField sx={{ml:'1em',mr:'1em'}} id={"modulename"} label={"Module Name"} onChange={handleChange}/>
                    <DialogActions>
                        <Button onClick={handleSubmit}>Submit</Button>
                        <Button onClick={handleClose}>Dismiss</Button>
                    </DialogActions>
                </Dialog>
            </Box>
        );
    }
    return null;
}

function SideBar(props){
    const {modules,networks} = props;

    return(
        <Grid item md={3} sm={11} xs={11} sx={{mt:'1em'}}>
            <Paper elevation={0} sx={{ p: 2, bgcolor: '#fdf7ec', mb:'2em'}}>
                <Typography variant="h6" gutterBottom>
                    About
                </Typography>
                <Typography>I'm Meng Zhao, welcome to my blog, I just finished my graduate study in software.
                    This website is developed for the sake of writing down and show you guys some of the projects
                    i contributed to, some ideas that come to my mind and some inspirational knowledge i obtain.</Typography>
            </Paper>
            <Divider/>
            <Typography  variant={"h6"} color={"grey"} sx={{fontFamily:'MyFont2'}}>
                Topics and Modules
            </Typography>
            {/*<List>*/}
            {modules.map((module)=>(
                <Grid container>
                    <Grid item md={9}>
                        <Typography sx={{ml:'1em',mb:'0.5em'}}>
                            <Link sx={{textDecoration:'none'}}
                                  component={RouterLink}
                                  to={`/getmodulearticles/${module.id}`}>
                                    {module.modulename}
                            </Link>
                        </Typography>
                    </Grid>
                    <Grid item md={1}>
                        <DeleteModule moduleid={module.id}/>
                    </Grid>
                </Grid>
                // </ListItem>
            ))}
            {/*</List>*/}
            <Divider/>
            <Typography variant={"h6"} sx={{fontFamily:'MyFont2'}}>
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
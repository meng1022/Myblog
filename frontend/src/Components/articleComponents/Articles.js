import {
    Typography,
    Grid,
    Link,
    Dialog,
    DialogTitle,
    DialogActions,
    Button,
    Box,
    TextField,
    Container, Divider
} from "@mui/material";
import * as React from "react";
import{Link as RouterLink} from 'react-router-dom';
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';
import SearchIcon from '@mui/icons-material/Search';

function DeleteButton(props){
    const {articleid} = props;
    const [open,setOpen] = React.useState(false);
    const handleClickOpen = ()=>{
        setOpen(true);
    };
    const handleClose = ()=>{
        setOpen(false);
    };

    function handleDelete(){
        (async()=>{
            const response = await fetch("/deletearticle",{
                method:'POST',
                headers:{
                    'Accept':'application/json',
                    'Content-Type':'application/json',
                },
                body: JSON.stringify({articleid:articleid})
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
                        Are you sure to delete this article?
                    </DialogTitle>
                    <DialogActions>
                        <Button onClick={handleDelete} href={"/getarticles"}>Delete</Button>
                        <Button onClick={handleClose}>Dismiss</Button>
                    </DialogActions>
                </Dialog>
            </Button>
        );
    return null;
}

function Articles(){
    const [firstLoad,setLoad] = React.useState(true);
    const [articles, updateArticles] = React.useState([]);
    const [search,setSearchKey] = React.useState("");

    async function getData(){
        let response = await fetch("/api/getarticles");
        let body = await response.json();
        updateArticles(body.data);
    }

    if(firstLoad){
        // getModules();
        getData();
        setLoad(false);
    }

    const handleChange = (event)=>{
        setSearchKey(event.target.value);
    }

    function handleSearch(){
        (async()=>{
            const response = await fetch("/api/searcharticles",{
               method:'POST',
               headers:{
                   'Accept':'application/json',
                   'Content-Type':'application/json',
               },
               body: JSON.stringify({search:search}),
            });
            const body = await response.json();
            updateArticles(body.data);
        })();

    }

    return(
        <Grid item md={8} sm={11} xs={11} sx={{mr:'2em',mt:'1em'}}>
            <Container sx={{textAlign:'center',mb:'2em'}}>
                <TextField width={35} id={"search"} required={true}
                           label={"Search Here"} size={"small"} color={"grey"}
                           focused onChange={handleChange}/>
                <Button onClick={handleSearch}><SearchIcon/></Button>
            </Container>

            {articles.map((article)=>(
                <Grid sx={{mt: '1em'}} container>
                    <Grid item md={11} sm={11} xs={11} key={article.id}>
                        <Typography sx={{ml: '1em' }} component={"h3"} variant={"subtitle1"}>
                            <Link  sx={{textDecoration:'none'}} component={RouterLink} to={`/getarticle/${article.id}`} key={article.id}>
                                {article.title}
                            </Link>
                        </Typography>
                        <Divider/>
                    </Grid>
                    {/*<Grid item md={4} sm={6} xs={12} sx={{textAlign:'center'}}>*/}
                    {/*    /!*{article.createZoneDate}*!/*/}
                    {/*    <DeleteButton articleid={article.id}/>*/}
                    {/*</Grid>*/}
                    <Grid item md={1} sm={1} xs={1} xs={{mr:'1em'}}>
                        {/*{article.createZoneDate}*/}
                        <DeleteButton articleid={article.id}/>
                    </Grid>

                </Grid>

            ))}
        </Grid>
    ) ;
}

export default Articles;
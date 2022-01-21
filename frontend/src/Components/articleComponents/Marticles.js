import React from 'react';
import {Link, Typography, Grid, Box, Divider, TextField, Button, Container} from '@mui/material';
import {Link as RouterLink, useParams} from 'react-router-dom';
import SideBar from "../basicComponents/SideBar";
import Articles from "./Articles";
import {GitHub, LinkedIn, Twitter} from "@mui/icons-material";
import SearchIcon from "@mui/icons-material/Search";


function Marticles(){
    const{ moduleid } = useParams();
    const [mid,setmoduleid] = React.useState(0);
    const [articles, updateArticles] = React.useState([]);
    const [search,setSearchKey] = React.useState("");

    async function getData(){
        let response = await fetch("/api/getmodulearticles?moduleid="+moduleid);
        let body = await response.json();
        updateArticles(body.data);
    }

    if(mid!=moduleid){
        getData();
        setmoduleid(moduleid);
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

    return (
        <Grid item md={8} sm={11} xs={11} sx={{mr:'2em',mt:'1em'}}>
            <Container sx={{textAlign:'center',mb:'2em'}}>
                <TextField width={35} id={"search"} required={true}
                           label={"Search Here"} size={"small"} color={"grey"}
                           focused onChange={handleChange}/>
                <Button onClick={handleSearch}><SearchIcon/></Button>
            </Container>

            {articles.map((article)=>(
                <Grid sx={{mt: '1em'}} container>
                    <Grid item md={12} sm={12} xs={12} key={article.id}>
                        <Typography sx={{ml: '1em' }} component={"h3"} variant={"subtitle1"}>
                            <Link  sx={{textDecoration:'none'}} component={RouterLink} to={`/getarticle/${article.id}`} key={article.id}>
                                {article.title}
                            </Link>
                        </Typography>
                        <Divider/>
                    </Grid>

                    {/*<Grid item md={4} sm={6} xs={12} sx={{textAlign:'center'}}>*/}
                    {/*    {article.createZoneDate}*/}
                    {/*</Grid>*/}
                </Grid>
            ))}
        </Grid>

    );
}

export default Marticles;
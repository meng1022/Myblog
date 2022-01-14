import {Typography, Grid, Link} from "@mui/material";
import * as React from "react";
import{Link as RouterLink} from 'react-router-dom';
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';

function DeleteButton(){
    if(sessionStorage.getItem("Userid")==="84921724")
        return <Link href={"#"} sx={{ml:'0.5em',textDecoration:'none'}}> <DeleteOutlineIcon/> </Link>
    return null;
}
function Articles(){
    // const{articles} = props;
    const [firstLoad,setLoad] = React.useState(true);
    const [articles, updateArticles] = React.useState([]);

    async function getData(){
        let response = await fetch("/getarticles");
        let body = await response.json();
        updateArticles(body.data);
    }

    if(firstLoad==true){
        // getModules();
        getData();
        setLoad(false);
    }

    return(
        <Grid item md={8} sm={11} xs={11} sx={{mr:'2em'}}>
            {articles.map((article)=>(
                <Grid sx={{mt: '1em'}} container>
                    <Grid item md={8} sm={6} xs={12} key={article.id}>
                        <Typography sx={{ml: '3em' }} component={"h3"} variant={"subtitle1"}>
                            <Link  sx={{textDecoration:'none'}} component={RouterLink} to={`/getarticle/${article.id}`} key={article.id}>
                                {article.title}
                            </Link>
                        </Typography>
                    </Grid>
                    <Grid item md={4} sm={6} xs={12} sx={{textAlign:'center'}}>
                        {article.createZoneDate}
                        <DeleteButton/>
                    </Grid>
                </Grid>
            ))}
        </Grid>
    ) ;
}

export default Articles;
import React from 'react';
import {Link, Typography, Grid, Box} from '@mui/material';
import {Link as RouterLink, useParams} from 'react-router-dom';
import SideBar from "../basicComponents/SideBar";
import Articles from "./Articles";
import {GitHub, LinkedIn, Twitter} from "@mui/icons-material";


function Marticles(){
    const{ moduleid } = useParams();
    const [firstLoad,setLoad] = React.useState(true);
    const [mid,setmoduleid] = React.useState(0);
    const [articles, updateArticles] = React.useState([]);

    async function getData(){
        let response = await fetch("/getmodulearticles?moduleid="+moduleid);
        let body = await response.json();
        updateArticles(body.data);
    }

    // if(firstLoad==true){
    //     getData();
    //     setLoad(false);
    // }
    if(mid!=moduleid){
        getData();
        setmoduleid(moduleid);
    }

    return (
        <Grid item md={8} sx={{mr:'2em'}}>
            {articles.map((article)=>(
                <Grid sx={{mt: '1em'}} container>
                    <Grid item md={8} key={article.id}>
                        <Typography sx={{ml: '3em' }} component={"h3"} variant={"subtitle1"}>
                            <Link  sx={{textDecoration:'none'}} component={RouterLink} to={`/getarticle/${article.id}`} key={article.id}>
                                {article.title}
                            </Link>
                        </Typography>
                    </Grid>
                    <Grid item md={4} > {article.createZoneTime}</Grid>
                </Grid>
            ))}
        </Grid>
        // <Box>
        //     {articles.map((article)=>(
        //         <Grid sx={{mt: '1em'}} container>
        //             <Grid item md={8} key={article.id}>
        //                 <Typography sx={{ml: '3em' }} component={"h3"} variant={"subtitle1"}>
        //                     <Link  sx={{textDecoration:'none'}} component={RouterLink} to={`/getarticle/${article.id}`} key={article.id}>
        //                         {article.title}
        //                     </Link>
        //                 </Typography>
        //             </Grid>
        //             <Grid item md={4} > {article.createZoneTime}</Grid>
        //         </Grid>
        //     ))}
        // </Box>

    );
}

export default Marticles;
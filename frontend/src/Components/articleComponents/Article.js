import React from 'react';
import { useParams } from 'react-router-dom';
import {Typography, Box, Grid, Link} from '@mui/material'
import EditIcon from '@mui/icons-material/Edit';

function EditButton(){
    if(sessionStorage.getItem("Userid")==="84921724")
        return (
            <Link href={"#"} sx={{ml:'0.5em',textDecoration:'none',textAlign:'right'}}>
                <EditIcon/>
            </Link>
        );
    return null;
}

function Article(){
    const[article,setArticle] = React.useState([]);
    const[firstLoad,setLoad] = React.useState(true);
    const {articleid} = useParams();
    if(firstLoad){
        getArticle();
        setLoad(false);
    }
    async function getArticle(){
        let url = "/getarticle?articleid="+articleid;
        let response = await fetch(url);
        let atle = await response.json();
        setArticle(atle.data);
    }
    return(
          <Grid item md={8} sx={{ml:'2em', mt:'2em'}}>
              <Typography variant={"h5"}>
              {article.title}
                  <EditButton/>
              </Typography>
          <div>Write on {article.createZoneTime}</div>
          <div dangerouslySetInnerHTML={{__html:article.content}}></div>
          </Grid>


    );
}
export default Article;
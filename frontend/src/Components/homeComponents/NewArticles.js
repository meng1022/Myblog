import {Divider, Grid, Typography, Link} from "@mui/material";
import * as React from "react";
import {Link as RouterLink, useParams} from 'react-router-dom';


function NewArticles(){
    const[firstLoad,setLoad] = React.useState(true);
    const[newarticles,updateHot] = React.useState([]);
    // const [firstToken, setToken] = React.useState("");
    // const {code} = useParams();

    async function getNewArticles(){
        let response = await fetch("/homepage");
        let body = await response.json();
        updateHot(body.data);
    }
    // async function getToken(){
    //     const settings = {
    //         method: 'POST',
    //         headers: {
    //             Accept: 'application/json',
    //         }
    //     };
    //     const url = "https://github.com/login/oauth/access_token?client_id=a5eca1aecf53810e6a8e&client_secret=5f57b71ca6b1282d263221ae48a2cd72601896a7&code="+code;
    //     const fetchResponse = await fetch(url,settings);
    //     const tokendata = await fetchResponse.json();
    //     setToken(tokendata.access_token);
    //     console.log("Token:",tokendata.access_token);
    // }
    if(firstLoad){
        getNewArticles();
        setLoad(false);
    }
    // if(code!=null&&code!=""&&firstToken==""){
    //     getToken();
    // }

    return(
        <Grid item md={8} sx={{mr:'2em'}}>
            <Typography sx={{mb:'1em'}} variant={"h6"}>
                Newly Posted Articles
            </Typography>
            <Divider/>
            {newarticles.map((article)=>(
                <div key={article.id}>
                    <Typography variant={"h5"} >
                        <Link sx={{textDecoration:'none' }} component={RouterLink} to={`/getarticle/${article.id}`}>
                            {article.title}
                        </Link>
                    </Typography>
                    <div>Write on {article.createZoneDate}</div>
                    <div dangerouslySetInnerHTML={{__html:article.content}}></div>
                    <Divider/>
                </div>
            ))}
        </Grid>
    );
}

export default NewArticles;
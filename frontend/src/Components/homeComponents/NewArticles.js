import {Divider, Grid, Typography, Link, Card, CardContent, CardActions, Box} from "@mui/material";
import * as React from "react";
import {Link as RouterLink, useParams} from 'react-router-dom';
import ReactMarkdown from "react-markdown";
import {Prism as SyntaxHighlighter} from "react-syntax-highlighter";
import * as Color from "react-syntax-highlighter/dist/cjs/styles/prism";
import HomePageImg from "./homePageImg";

function NewArticles(){
    const[firstLoad,setLoad] = React.useState(true);
    const[newarticles,updateHot] = React.useState([]);
    // const [firstToken, setToken] = React.useState("");
    // const {code} = useParams();

    async function getNewArticles(){
        let response = await fetch("/api/homepage");
        let body = await response.json();
        updateHot(body.data);
    }
    if(firstLoad){
        getNewArticles();
        setLoad(false);
    }

    return(
        <Grid item md={8} sm={11} xs={11} sx={{mr:'2em',mt:'1em'}} >
            <HomePageImg/>
            <Typography sx={{mb:'1em',fontFamily:'MyFont2'}} variant={"h5"} color={"primary"}>
                Newly Posted Articles
            </Typography>
            {newarticles.map((article)=>(
                <Card sx={{mb:'2em',ml:'0.2em'}} elevation={3}>
                    <CardContent>
                        <Typography variant={"h5"}>
                            <Link sx={{textDecoration:'none' }} component={RouterLink} to={`/getarticle/${article.id}`}>
                                {article.title}
                            </Link>
                        </Typography>
                        <Typography color={"text.secondary"}>Write on {article.createZoneDate}</Typography>
                        <div>
                            <ReactMarkdown children={article.content}
                                           components={{
                                               code({node, inline, className, children, ...props}) {
                                                   const match = /language-(\w+)/.exec(className || '')
                                                   return !inline && match ? (
                                                       <SyntaxHighlighter
                                                           children={String(children).replace(/\n$/, '')}
                                                           style={Color.prism}
                                                           language={match[1]}
                                                           PreTag="div"
                                                           {...props}
                                                       />
                                                   ) : (
                                                       <code className={className} {...props}>
                                                           {children}
                                                       </code>
                                                   )
                                               }
                                           }}
                            />
                        </div>
                    </CardContent>
                    <CardActions>
                        <Typography sx={{ml:'auto',mr:'1em'}}>
                            <Link sx={{textDecoration:'none'}} component={"a"} href={`/getarticle/${article.id}`}>
                                Read More...
                            </Link>
                        </Typography>
                    </CardActions>
                </Card>
            ))}

            {/*<Divider/>*/}
            {/*{newarticles.map((article)=>(*/}
            {/*    <div key={article.id}>*/}
            {/*        <Typography variant={"h5"} >*/}
            {/*            <Link sx={{textDecoration:'none' }} component={RouterLink} to={`/getarticle/${article.id}`}>*/}
            {/*                {article.title}*/}
            {/*            </Link>*/}
            {/*        </Typography>*/}
            {/*        <Typography color={"text.secondary"}>Write on {article.createZoneDate}</Typography>*/}


            {/*        /!*<div dangerouslySetInnerHTML={{__html:article.content}}></div>*!/*/}
            {/*        <div>*/}
            {/*            <ReactMarkdown children={article.content}*/}
            {/*                           components={{*/}
            {/*                               code({node, inline, className, children, ...props}) {*/}
            {/*                                   const match = /language-(\w+)/.exec(className || '')*/}
            {/*                                   return !inline && match ? (*/}
            {/*                                       <SyntaxHighlighter*/}
            {/*                                           children={String(children).replace(/\n$/, '')}*/}
            {/*                                           style={Color.prism}*/}
            {/*                                           language={match[1]}*/}
            {/*                                           PreTag="div"*/}
            {/*                                           {...props}*/}
            {/*                                       />*/}
            {/*                                   ) : (*/}
            {/*                                       <code className={className} {...props}>*/}
            {/*                                           {children}*/}
            {/*                                       </code>*/}
            {/*                                   )*/}
            {/*                               }*/}
            {/*                           }}*/}
            {/*            />*/}
            {/*        </div>*/}
            {/*        <Divider/>*/}
            {/*    </div>*/}
            {/*))}*/}

        </Grid>
    );
}

export default NewArticles;
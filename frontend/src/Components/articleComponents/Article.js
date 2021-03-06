import React from 'react';
import { useParams } from 'react-router-dom';
import {Typography, Box, Grid, Link, ButtonGroup, Button, Divider} from '@mui/material'
import EditIcon from '@mui/icons-material/Edit';
import {Prism as SyntaxHighlighter} from "react-syntax-highlighter";
import * as Color from "react-syntax-highlighter/dist/cjs/styles/prism";
import ReactMarkdown from "react-markdown";
import Comment from "./Comment";

function EditButton(props){
    const{articleid} = props;
    if(sessionStorage.getItem("__USER_ID__")==="84921724")
        return (
            <Link href={"/editarticle/"+articleid} sx={{ml:'0.5em',textDecoration:'none',textAlign:'right'}}>
                <EditIcon/>
            </Link>
        );
    return null;
}

function Article(){
    const[firstLoad,setLoad] = React.useState(true);
    const[article,setArticle] = React.useState([]);
    const[modules,setModules] = React.useState([]);
    const [comments,setComments] = React.useState([]);
    const {articleid} = useParams();

    //able to comment
    // const [able,setAble] = React.useState(false);
    // if(sessionStorage.getItem("__USER_ID__")){
    //     setAble(true);
    // }

    if(firstLoad){
        (async()=>{
            let response = await fetch("/api/getarticle?articleid="+articleid);
            let body = await response.json();
            setArticle(body.data.article);
            setModules(body.data.modulenames);
        })();

        // (async()=>{
        //     let response = await fetch("/api/getcomments?articleid="+articleid);
        //     let body = await response.json();
        //     setComments(body.data);
        // })();
        // getArticle();
        // getComments();
        setLoad(false);
    }

    // async function getArticle(){
    //     let url = "/api/getarticle?articleid="+articleid;
    //     let response = await fetch(url);
    //     let body = await response.json();
    //     setArticle(body.data.article);
    //     setModules(body.data.modulenames);
    // }
    //
    // async function getComments(){
    //     let response = await fetch("/api/getcomments?articleid="+articleid);
    //     let body = await response.json();
    //     setComments(body.data);
    // }

    return(
          <Grid item md={8} xs={11} sx={{ml:'0em',mr:'2em', mt:'2em'}}>
              <Typography variant={"h5"}>
                  {article.title}<EditButton articleid={article.id}/>
              </Typography>
              <ButtonGroup variant="text"
                           aria-label="text button group"
                           sx={{mt:'1em',mb:'1em'}}>
                  {modules.map((module)=>(
                   <Button href={`/getmodulearticles/${module.id}`}>{module.modulename}</Button>
                  ))}
              </ButtonGroup>
              <div>Write on {article.createZoneTime}</div>
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
                                     },
                                     img:({node,...props})=>
                                         <img style={{maxWidth:'90%'}}{...props}/>
                                 }}
                  />
              </div>
              <Divider sx={{mt:'2em',mb:'1em'}}/>
              <Comment articleid={articleid}/>
          </Grid>


    );
}
export default Article;
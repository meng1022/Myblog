import * as React from 'react';
import ErrorPage from "../ErrorPage";
import {Grid, FormControl, TextField, InputLabel, Select, MenuItem, Button, Paper} from "@mui/material";
import ReactMarkdown  from 'react-markdown';
import {Prism as SyntaxHighlighter} from 'react-syntax-highlighter'
import * as Color from 'react-syntax-highlighter/dist/esm/styles/prism'
import {useParams} from "react-router-dom";
import UploadImg from "../uploadImgComponents/UploadImg";

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 250,
        },
    },
};
function EditComponent(){
    const [firstLoad,setLoad] = React.useState(true);
    const [title,setTitle] = React.useState("");
    const [modules,updateModules] = React.useState([]);
    const [moduleids,setModules] = React.useState([]);
    const [content,setContent] = React.useState("");
    const {articleid} = useParams();

    const TitleChange = (event)=>{
        setTitle(event.target.value);
    };

    const ModulesChange = (event)=>{
        const{
            target: {value},
        } = event;
        setModules(
            typeof value === 'string' ? value.split(','):value,
        );
    };

    const ContentChange = (event)=>{
        setContent(event.target.value);
    };

    function submit(){
        console.log(title,moduleids,content);
        (async ()=>{
            const response = await fetch("/editarticle",{
                method:'POST',
                headers:{
                    'Accept':'application/json',
                    'Content-Type':'application/json',
                },
                body: JSON.stringify({articleid:articleid,title:title,moduleids:moduleids,content:content})
            });
            const cont = await response.json();
            console.log(cont.data);
        })();
    }

    if(sessionStorage.getItem("__USER_ID__")==="84921724"){
        async function getData(){
            let response = await fetch("/editarticle?articleid="+articleid);
            let body = await response.json();
            setTitle(body.data.article.title);
            setContent(body.data.article.content);
            updateModules(body.data.totalModules);
            //set default modules, original module list of this article
            setModules(body.data.modules);
        }

        if(firstLoad){
            getData();
            setLoad(false);
        }

        return(
            <Grid item md={8}>
                <TextField fullWidth={true}
                           id={"title"} label={"Title"}
                           onChange={TitleChange}
                           value={title}/>
                <FormControl sx={{mt:'2em'}} fullWidth={true}>
                    <InputLabel id={"input_modules"}>Modules</InputLabel>
                    <Select
                        fullWidth={true}
                        MenuProps={MenuProps}
                        labelId="input_modules"
                        id="modules"
                        multiple
                        value={moduleids}
                        label="Modules"
                        onChange={ModulesChange}
                    >
                        {modules.map((module)=>(
                            <MenuItem value={module.id}>{module.modulename}</MenuItem>
                        ))}
                    </Select>
                </FormControl>
                <UploadImg/>
                <TextField fullWidth
                           id={"content"} label={"Please type in your content here"}
                           rows={20} multiline
                           onChange={ContentChange}
                           value={content}/>
                <Paper sx={{mt:'2em',pl:'1em',pr:'1em'}} variant={"outlined"} >
                    <ReactMarkdown children={content}
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
                </Paper>
                <Button sx={{mt:'2em'}} onClick={submit} href={"/getarticles"}> Update </Button>
            </Grid>
        );
    }
    return(<ErrorPage/>);
}

function EditArticle(){
    return(
        <EditComponent/>
    );
}
export default EditArticle;
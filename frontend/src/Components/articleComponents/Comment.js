import * as React from "react";
import MessageRoundedIcon from '@mui/icons-material/MessageRounded';
import ReplyIcon from '@mui/icons-material/Reply';
import {
    Avatar, Box,
    Button,
    Dialog,
    DialogActions,
    DialogTitle, Divider,
    Grid,
    IconButton,
    Paper,
    TextField, Tooltip,
    Typography
} from "@mui/material";

function CommentComponent(props){
    const {comment} = props;

    return(
        <Grid container>
            <Grid item md={1} xs={12}>
                <Avatar src={comment.fromavatar}
                        alt={comment.fromusername}
                        sx={{ml:'0.5em',mt:'0.5em',display:'inline-flex'}}/>
            </Grid>
            <Grid item md={10} xs={12} sx={{ml:'1em',mt:'0.5em'}}>
                <Typography sx={{fontFamily:'MyFont2',fontSize:15}} color={"text.secondary"}>
                    {comment.fromusername}
                </Typography>
                <Typography sx={{fontFamily:'MyFont2',fontSize:13}} color={"text.secondary"}>
                    {comment.createZoneTime}
                </Typography>
                <Typography sx={{fontFamily:'MyFont2',fontSize:15,mt:'0.5em'}}>
                    {comment.content}
                </Typography>
            </Grid>
        </Grid>
    );

}

function CommentBoard(props){
    const {originalComment} = props;
    const [firstLoad,setLoad] = React.useState(true);
    const [comment,setComment] = React.useState({leadComment:null,comments:[]});
    const [open,setOpen] = React.useState(false);
    const [alert,setAlert] = React.useState(false);
    const [toUserName,setDefaultToUser] = React.useState("");
    const [toUserid,setToUserid] = React.useState("");
    const [content,setContent] = React.useState("");
    const [alertMessage,setMsg] = React.useState("");

    if(firstLoad){
        setComment(originalComment);
        setLoad(false);
    }

    const handleChange = (event)=>{
        setContent(event.target.value);
    }
    const handleClose = ()=>{
        setOpen(false);
    }
    function handleAlertClose(){
        setAlert(false);
    }

    function handleSubComment(){
        if(content===""){
            setMsg("Comment is not allowed to be empty!");
            setAlert(true);
        }else{
            (async()=>{
                let response = await fetch("/api/addsubcomment",{
                    method:'POST',
                    headers:{
                        'Accept':'application/json',
                        'Content-Type':'application/json',
                    },
                    body:JSON.stringify({articleid:parseInt(comment.leadComment.articleid),
                                                commentid:comment.leadComment.id, content:content,
                                                fromuser:sessionStorage.getItem("__USER_ID__"),
                                                fromusername:sessionStorage.getItem("__USER_NAME__"),
                                                fromavatar:sessionStorage.getItem("__USER_AVATAR__"),
                                                to:toUserid.toString()
                    })
                })
                let body = await response.json();
                const clone_comment = [...comment.comments];
                clone_comment.push(body.data);
                setComment({leadComment: comment.leadComment,comments: clone_comment});
            })();
            setOpen(false);
        }
    }

    return(
        <Box>
            <CommentComponent comment={comment.leadComment}/>
            <Grid contianer>
                <Grid item md={12} xs={12} sx={{textAlign:'right',ml:'1.5em'}}>
                    <Tooltip title={"reply"}>
                        <IconButton onClick={()=>{
                            if(!sessionStorage.getItem("__USER_ID__")){
                                setMsg("Please log in before commenting!");
                                setAlert(true);
                            }else{
                                setDefaultToUser("@"+comment.leadComment.fromusername+" ");
                                setToUserid(comment.leadComment.fromuser);
                                setOpen(true);
                            }
                            }}>
                            <ReplyIcon color={"primary"}/>
                        </IconButton>
                    </Tooltip>
                    <Dialog open={open} onClose={handleClose} fullWidth>
                        <DialogTitle>Comment</DialogTitle>
                        <TextField id={"content"} multiline rows={8} sx={{pl:4,pr:4}} onChange={handleChange} defaultValue={toUserName}>
                        </TextField>
                        <DialogActions>
                            <Button onClick={handleSubComment}>Comment</Button>
                            <Button onClick={handleClose}>Dismiss</Button>
                        </DialogActions>
                    </Dialog>
                    <Dialog open={alert} onClose={handleAlertClose}>
                        <DialogTitle>
                            <Typography color={"error"}>
                                {alertMessage}
                            </Typography>
                        </DialogTitle>
                    </Dialog>
                </Grid>
            </Grid>
            <Grid container>
                <Grid item md={1} xs={1}/>
                <Grid item md={11} xs={11}>
                    {comment.comments.map((subcomment)=>(
                        <Box>
                            <CommentComponent comment={subcomment}/>
                            <Grid item md={12} xs={12} sx={{textAlign:'right'}}>
                                <Tooltip title={"reply"}>
                                    <IconButton onClick={()=>{
                                        if(!sessionStorage.getItem("__USER_ID__")){
                                            setMsg("Please log in before commenting!");
                                            setAlert(true);
                                        }else{
                                            setDefaultToUser("@"+subcomment.fromusername+" ");
                                            setToUserid(subcomment.fromuser);
                                            setOpen(true);
                                        }
                                        }}>
                                        <ReplyIcon color={"primary"}/>
                                    </IconButton>
                                </Tooltip>
                            </Grid>
                        </Box>
                    ))}
                </Grid>
            </Grid>
        </Box>

    );
}

function Comment(props){
    // const {comments,articleid} = props
    const {articleid} = props
    const [firstLoad,setLoad] = React.useState(true);
    const [comments,setComments] = React.useState([]);
    const [content,setContent] = React.useState("");
    const [open,setOpen] = React.useState(false);
    const [alert,setAlert] = React.useState(false);
    const [alertMessage,setMsg] = React.useState("");

    if(firstLoad){
        (async()=>{
            let response = await fetch("/api/getcomments?articleid="+articleid);
            let body = await response.json();
            setComments(body.data);
        })();
        setLoad(false);
    }

    const handleOpen = ()=>{
        if(!sessionStorage.getItem("__USER_ID__")){
            setMsg("Please log in before commenting!");
            setAlert(true);
        }else{
            setOpen(true);
        }
    }
    const handleClose = ()=>{
        setOpen(false);
    }
    const handleAlertClose = ()=>{
        setAlert(false);
    }

    const handleChange = (event)=>{
        setContent(event.target.value);
    }

    function handleLeadComment(){
        if(content===""){
            setMsg("Comment is not allowed to be empty!");
            setAlert(true);
        }
        else{
            (async()=>{
                let response = await fetch("/api/addleadcomment",{
                    method:'POST',
                    headers:{
                        'Accept':'application/json',
                        'Content-Type':'application/json',
                    },
                    body: JSON.stringify({articleid:parseInt(articleid),commentid:0,content:content,
                                                fromuser:sessionStorage.getItem("__USER_ID__"),
                                                fromusername:sessionStorage.getItem("__USER_NAME__"),
                                                fromavatar:sessionStorage.getItem("__USER_AVATAR__"),
                                                to:"84921724"})
                })
                let body = await response.json();

                const clone_comments = [...comments];
                clone_comments.push(body.data);
                setComments(clone_comments);
            })();
            setOpen(false);
        }
    }

    // if(sessionStorage.getItem("__USER_ID__")){
        return (
            <div>
                <Box sx={{textAlign:'right',mb:'0.5em'}}>
                    <Tooltip title={"comment"}>
                        <Button onClick={handleOpen}>
                            <MessageRoundedIcon/>
                        </Button>
                    </Tooltip>
                </Box>
                <Dialog open={open} onClose={handleClose} fullWidth>
                    <DialogTitle>Comment</DialogTitle>
                    <TextField id={"content"} multiline rows={8} sx={{pl:4,pr:4}} onChange={handleChange}>
                    </TextField>
                    <DialogActions>
                        <Button onClick={handleLeadComment}>Comment</Button>
                        <Button onClick={handleClose}>Dismiss</Button>
                    </DialogActions>
                </Dialog>
                <Dialog open={alert} onClose={handleAlertClose}>
                    <DialogTitle>
                        <Typography color={"error"}>
                            {alertMessage}
                        </Typography>
                    </DialogTitle>
                </Dialog>
                {comments.map((comment)=>(
                    <Paper sx={{mb:'2em',bgcolor:'#fdf9f1',p:'1em'}} elevation={0} >
                        <CommentBoard originalComment={comment}/>
                    </Paper>
                ))}
            </div>

        );

}


export default Comment;
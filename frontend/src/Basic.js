import React from 'react';
import {
    Toolbar,
    Button,
    Typography,
    Link,
    Box,
    Container,
    Grid,
    Badge, Avatar, Tooltip, IconButton, List, ListItem, Menu, MenuItem, Divider
} from '@mui/material'
import { Outlet, Link as RouterLink,useParams } from 'react-router-dom';
import SideBar from "./Components/basicComponents/SideBar";
import {GitHub, LinkedIn} from "@mui/icons-material";
import MailRoundedIcon from '@mui/icons-material/MailRounded';
import CottageOutlinedIcon from '@mui/icons-material/CottageOutlined';
import MessageRoundedIcon from '@mui/icons-material/MessageRounded';

const networks = [
    {name:'GitHub',icon: GitHub,url:"https://github.com/meng1022"},
    {name: 'LinkedIn', icon: LinkedIn,url:"https://www.linkedin.com/in/meng-zhao-764152228/"},
    {name: 'Email',icon: MailRoundedIcon,url:"mailto:zhaomeng0903@outlook.com"},
];

function MyButton(props) {
    const {userid,username,avatar} = props;
    const [firstLoad,setLoad] = React.useState(true);
    // const [anchorEl, setAnchorEl] = React.useState(null);
    // const [notifications,setNotify] = React.useState([]);
    const [msgNo, setMsgNo] = React.useState(0);
    let lockReconnect = false;
    let socket;
    let socketUrl = "wss://www.meng-zhao.com/websocket/"+userid;
    // let socketUrl = "ws://localhost:8080/websocket/"+userid;

    if(firstLoad&&userid!=null&&userid!==""){
        // socket = new WebSocket("wss://www.meng-zhao.com/websocket/"+userid);
        // socket.onmessage = function(msg){
        //     const count = parseInt(msg.data);
        //     setMsgNo(count);
        // };
        // socket.onopen = function(){
        //     fetch("/api/getnotificationcount?userid="+userid);
        // };
        // socket.onclose = function (){
        //
        // };
        createWebSocket(socketUrl);
        setLoad(false);
    }

    function createWebSocket(url){
        socket = new WebSocket(url);
        initEventHandle();
    }

    function initEventHandle(){
        socket.onmessage = function(msg){
            heartCheck.reset().start();
            if(msg.data!=="success"){
                const count = parseInt(msg.data);
                setMsgNo(count);
            }
            console.log(msg.data);
        }
        socket.onopen = function(){
            fetch("/api/getnotificationcount?userid="+userid);
            heartCheck.reset().start();
        }
        socket.onclose = function(){
            reConnect(socketUrl);
        }
        socket.onerror = function(){
            reConnect(socketUrl);
            console.log("websocket connection error");
        }
    }

    function reConnect(url){
        //if is not connected there would be tons of requests
        // set a timer to avoid this.
        if(lockReconnect) return;
        lockReconnect = true;
        setTimeout(function(){
            createWebSocket(url);
            console.log("trying to reconnect");
            lockReconnect = false;
        },2000);
    }

    // send a test message to backend every minute
    const heartCheck = {
        timeOut: 60000,
        timeOutObj: null,
        serverTimeoutObj: null,
        reset: function(){
            clearTimeout(this.timeOutObj);
            clearTimeout(this.serverTimeoutObj);
            return this;
        },
        start: function(){
            let self = this;
            this.timeOutObj = setTimeout(function(){
                socket.send("test");
                self.serverTimeoutObj = setTimeout(function(){
                    socket.close();
                },self.timeOut);
            },this.timeOut);
        }
    }


    const handleLogOut = ()=>{
        sessionStorage.clear();
    };

    return((userid!=null&&userid!=="")?(
        <Box>
            <IconButton component={RouterLink} to={"/notifications"}>
                <Badge badgeContent={msgNo} color={"error"}>
                    <MessageRoundedIcon color={"primary"}/>
                </Badge>
            </IconButton>
            <Tooltip title={"log out"}>
                <IconButton onClick={handleLogOut} href={"/homepage"} sx={{textTransform: "none",fontFamily:'MyFont2', fontSize:15}}>
                    <Avatar src={avatar} alt={username}/>
                </IconButton>
            </Tooltip>
        </Box>):(
        <Tooltip title={"log in"}>
            <IconButton href="https://github.com/login/oauth/authorize?client_id=a5eca1aecf53810e6a8e"
                        sx={{fontFamily:'MyFont2'}}>
                        {/*target={"_blank"}*/}
                <GitHub color={"primary"} fontSize={"large"}/>
            </IconButton>
        </Tooltip>
    ));
}

function Basic(props) {
    const { sections, title } = props;
    const [firstLoad,setLoad] = React.useState(true);
    const [userid, setUserid] = React.useState(sessionStorage.getItem("__USER_ID__"));
    const [username, setUsername] = React.useState(sessionStorage.getItem("__USER_NAME__"));
    const [avatar,setAvatar] = React.useState(sessionStorage.getItem("__USER_AVATAR__"));
    const [modules, updateModules] = React.useState([]);

    const queryString = document.location.search;

    async function getModules(){
        let response = await fetch("/getmodules");
        let body = await response.json();
        updateModules(body.data);
    }

    async function getUserinfo(){
        console.log(document.location);
        let params = new URLSearchParams(queryString);
        let code = params.get("code");
        let user_response = await fetch("/api/getUserinfo?code="+code);
        let body = await user_response.json();
        if(body.message==="ok"){
            setUserid(body.data.userid);
            setUsername(body.data.username);
            setAvatar(body.data.avatar);
            // setMsgNo(body.data.msgno);
            sessionStorage.setItem("__USER_ID__",body.data.userid);
            sessionStorage.setItem("__USER_NAME__",body.data.username);
            sessionStorage.setItem("__USER_AVATAR__",body.data.avatar);

        }

    }

    if(firstLoad){
        getModules();
        setLoad(false);
    }

    if(queryString!=null&&queryString!==""&&(userid==null||userid==="")){
        getUserinfo();
    }

    return (
        <Box sx={{bgcolor:"rgba(255, 222, 173,.05)"}}>
        <Container maxWidth={'lg'} >
            <Toolbar sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Button size="small" component={RouterLink} color="inherit" to={"/homepage"}>
                    <CottageOutlinedIcon fontSize={"large"} color={"primary"}/>
                    <Typography color={"primary"} sx={{textTransform: "none",mt:'0.5em',fontFamily:'MyFont2',fontSize:15}}>
                         Meng-Zhao
                    </Typography>
                </Button>
                <Typography
                    component="h2"
                    variant="h5"
                    color="inherit"
                    align="center"
                    noWrap
                    sx={{ flex: 1 }}
                >
                    {title}
                </Typography>
                <MyButton userid={userid} username={username} avatar={avatar}/>
            </Toolbar>
            <Toolbar
                component="nav"
                variant="dense"
                sx={{ justifyContent: 'space-between',overflow:'auto',whiteSpace:'nowarp' }}
            >
                {sections.map((section) => (
                    <Link
                        color="inherit"
                        noWrap
                        key={section.title}
                        variant="subtitle1"
                        to={section.url}
                        component={RouterLink}
                        sx={{ p: 1, flexShrink: 0, textDecoration:'none', }}
                    >
                        <Typography sx={{fontFamily: 'MyFont2',fontSize:18}} color={"primary"}>
                        {section.title}
                        </Typography>
                    </Link>
                ))}
            </Toolbar>
            <Grid container sx={{ml:'1.5em', minHeight: '90vh',overflow:"hidden"}}>
                <Outlet/>
                <SideBar modules={modules} networks={networks}/>
                <Grid item md={8} sm={12} xs={12}>
                    <Box sx={{top:"100vh",position:"sticky"}}>
                        <Typography sx={{ mt:'2em',mb:'2em' ,textAlign:'center' }} >
                            <Link component={"a"} href={"#"} sx={{textDecoration:'none'}}> go back to top</Link>
                            <Typography color={"text.secondary"}>Copyright ©
                                <Link component={"a"} href={"/"} sx={{textDecoration:'none'}}> meng-zhao </Link>2022.
                            </Typography>
                        </Typography>
                    </Box>
                </Grid>
            </Grid>
        </Container>
        </Box>
    );
}
export default Basic;
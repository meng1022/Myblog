import React from 'react';
import {Toolbar, Button, Typography, Link, Box, Container, Grid} from '@mui/material'
import { Outlet, Link as RouterLink,useParams } from 'react-router-dom';
import SideBar from "./Components/basicComponents/SideBar";
import {GitHub, LinkedIn, Twitter} from "@mui/icons-material";


const networks = [
    {name:'GitHub',icon: GitHub,url:"https://github.com/meng1022"},
    {name: 'LinkedIn', icon: LinkedIn,url:"https://www.linkedin.com/in/meng-zhao-764152228/"},
    {name: 'Twitter',icon: Twitter,url:"#"},
];

function MyButton(props) {
    // const { Userid } = props;
    const {userid,username} = props;
    const handleLogOut = ()=>{
        // sessionStorage.removeItem("user");
        // sessionStorage.setItem("user",null);
        sessionStorage.removeItem("__USER_ID__");
        sessionStorage.removeItem("__USER_NAME__");
    };
    // if(Userid!==""&&Userid!=null)
    if(userid!=null&&userid!="")
        return(
            <Button onClick={handleLogOut} href={"/homepage"}>
                Logout, {username}
                {/*Logout, {user.username}*/}
            </Button>
        );
    else
        return(
            // target={"_blank"}
            <Button variant="outlined" size="small"  href="https://github.com/login/oauth/authorize?client_id=a5eca1aecf53810e6a8e">
                <GitHub sx={{mr:'0.5em'}}/>
                <span >Sign In</span>
            </Button>
        );
}

function Basic(props) {
    const { sections, title } = props;
    const [firstLoad,setLoad] = React.useState(true);
    const [Token, setToken] = React.useState("");
    const [userid, setUserid] = React.useState(sessionStorage.getItem("__USER_ID__"));
    const [username, setUsername] = React.useState(sessionStorage.getItem("__USER_NAME__"));
    // const [user, setUser] = React.useState(sessionStorage.getItem("user"));
    const [modules, updateModules] = React.useState([]);
    const queryString = document.location.search;

    async function getModules(){
        let response = await fetch("/getmodules");
        let body = await response.json();
        updateModules(body.data);
    }

    async function getToken(){
        let params = new URLSearchParams(queryString);
        let code = params.get("code");
        console.log("code",code);

        let request = new XMLHttpRequest();
        request.onreadystatechange = function(){
            if(request.readyState == XMLHttpRequest.DONE){
                // console.log(request.response.access_token);
                console.log(request.responseType);
                let json = JSON.parse(request.responseText);
                setToken(json.access_token);
            }
        }
        request.open('POST',
            'https://cors-anywhere.herokuapp.com/https://github.com/login/oauth/access_token',
            true
            );
        request.setRequestHeader('Accept','application/json');
        request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        request.send("client_id=a5eca1aecf53810e6a8e"+
                            "&client_secret=5f57b71ca6b1282d263221ae48a2cd72601896a7"+
                            "&code="+code);
    }

    async function getToken_bak(){
        let params = new URLSearchParams(queryString);
        let code = params.get("code");
        let token_response = await fetch("/api/getToken?code="+code);
        let body = await token_response.json();
        if(body.message==="ok"){
            console.log(body.data);
            setToken(body.data);
        }
    }

    async function getUserinfo(){
        let params = new URLSearchParams(queryString);
        let code = params.get("code");
        let user_response = await fetch("/api/getUserinfo?code="+code);
        let body = await user_response.json();
        if(body.message==="ok"){
            // console.log(body.data);
            setUserid(body.data.userid);
            setUsername(body.data.username);
            // setUser(body.data);
            sessionStorage.setItem("__USER_ID__",body.data.userid);
            sessionStorage.setItem("__USER_NAME__",body.data.username);
        }
    }

    if(firstLoad){
        getModules();
        setLoad(false);
    }

    // if(queryString!=null&&queryString!=""&&(Userid===""||Userid==null)){
    //     getUserinfo();
    // }
    if(queryString!=null&&queryString!==""&&(userid==null||userid==="")){
        getUserinfo();
    }

    return (
        <Container maxWidth={'lg'} sx={{mb:'2em'}}>
            <Toolbar sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Button size="small" component={RouterLink} color="inherit" to={"/homepage"}>Home</Button>
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
                {/*<MyButton Userid={Userid}></MyButton>*/}
                <MyButton userid={userid} username={username}/>
            </Toolbar>
            <Toolbar
                component="nav"
                variant="dense"
                sx={{ justifyContent: 'space-between', overflowX: 'auto' }}
            >
                {sections.map((section) => (
                    <Link
                        color="inherit"
                        noWrap
                        key={section.title}
                        variant="subtitle1"
                        to={section.url}
                        component={RouterLink}
                        sx={{ p: 1, flexShrink: 0, textDecoration:'none' }}
                    >
                        {section.title}
                    </Link>
                ))}
            </Toolbar>
            <Grid container sx={{ml:'1.5em',mr:'0em'}}>
                <Outlet/>
                <SideBar modules={modules} networks={networks}></SideBar>
                <Grid item md={8} sm={11} xs={11} >
                <Typography sx={{ mt:'2em',mb:'2em', textAlign:'center'}}>
                    <Link component={"a"} href={"#"} sx={{textDecoration:'none'}}> go back to top</Link>
                </Typography>
                </Grid>
            </Grid>

        </Container>
    );
}
export default Basic;
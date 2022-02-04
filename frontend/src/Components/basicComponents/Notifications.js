import {
    Avatar,
    Box,
    Divider,
    Grid,
    Link,
    List,
    ListItem,
    ListItemButton,
    ListItemText, Paper,
    Stack,
    Typography
} from "@mui/material";
import React from "react";
import {Link as RouterLink} from 'react-router-dom';

function NotificationComponent(props){
    const {msg} = props;
    return(
        <ListItem>
            <ListItemButton component={RouterLink} to={`/getarticle/${msg.articleid}`}>
                <Grid container>
                    <Grid item md={1} xs={12} >
                        <Avatar src={msg.fromavatar}
                                alt={msg.fromusername}
                                sx={{ml:'0.5em',mt:'0.5em',display:'inline-flex'}}/>
                    </Grid>
                    <Grid item md={10} xs={12} sx={{ml:'1em',mt:'0.5em'}}>
                        <Typography sx={{fontFamily:'MyFont2',fontSize:15}} color={"primary"}>
                            {msg.fromusername}
                        </Typography>
                        <Typography sx={{fontFamily:'MyFont2',fontSize:13}} color={"text.secondary"}>
                            {msg.createZoneTime}
                        </Typography>
                        <Typography sx={{fontFamily:'MyFont2',fontSize:15,mt:'0.5em'}}>
                            {msg.content.substr(0,100)+"..."}
                        </Typography><Divider sx={{mt:'1em'}}/>
                    </Grid>
                </Grid>
            </ListItemButton>
        </ListItem>
    );
}

function Notifications(){
    const [firstLoad,setLoad] = React.useState(true);
    const [userid,setUser] = React.useState(sessionStorage.getItem("__USER_ID__"));
    const [messages,setMsg] = React.useState([]);
    const [notifications,setNotify] = React.useState([]);

    if(firstLoad){
        getNotifications();
        setLoad(false);
    }
    function getNotifications(){
        (async()=>{
            let response = await fetch("/api/getallnotifications?userid="+userid);
            let body = await response.json();
            setNotify(body.data.notifications);
            setMsg(body.data.comments);
            // console.log(body.data.comments);
        })();

    }

    return (
        <Grid item md={8} xs={11} sx={{ml:'0em',mr:'2em', mt:'2em'}}>
            <Typography variant={"h5"} sx={{fontFamily:'MyFont2',ml:'2em'}}>Notifications</Typography>
            <nav>
                <List>
                {notifications.map((notification)=>(
                    <Paper sx={{bgcolor: '#f1f7fd'}} elevation={0}>
                        <NotificationComponent msg={notification}/>
                    </Paper>
                ))}
                {messages.map((message)=>(
                    <Paper sx={{}} elevation={0}>
                        <NotificationComponent msg={message}/>
                    </Paper>

                ))}
                </List>
            </nav>
        </Grid>
    );
}
export default Notifications;
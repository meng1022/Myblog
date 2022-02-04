import {Button, Card, CardContent, FormControl, Grid, Link, TextField, Typography} from "@mui/material";
import * as React from "react";
import validator from "validator/es";
import {Link as RouterLink} from 'react-router-dom';

function JavaMail(){
    const [email,updateEmail] = React.useState("");
    const [sendButton,updateButton] = React.useState("Send");
    const [trueCode,setCode] = React.useState("");
    const [enteredCode,updateCode] = React.useState("");
    const [msg,setMsg] = React.useState("");
    const [idle,setIdle] = React.useState(true);

    const handleChangeEmail = (event)=>{
        updateEmail(event.target.value);
    }

    function sendEmail(){
        setCode("");
        if(email===""||!validator.isEmail(email)){
            setMsg("Invalid Email Address");
            updateButton("Send");
        }
        else if(idle===false){
            setMsg("Wait a Moment, Click too Frequently")
        }
        else{
            setMsg("");
            setIdle(false);
            (async()=>{
                const response = await fetch("/api/sendemail?email="+email);
                const body = await response.json();
                setCode(body.data);
                updateButton("Sent");
                setIdle(true);
                // console.log(body);
            })();
        }
    }

    const handleChangeCode = (event)=>{
        updateCode(event.target.value);
    }

    function handleVerify(){
        if(trueCode==="")
            setMsg("Verification Code not Sent Yet")
        else if(enteredCode===trueCode&&enteredCode!=="")
            setMsg("Verified Successfully");
        else if(enteredCode!==trueCode&&enteredCode!=="")
            setMsg("The Verification Code is incorrect");
    }

    return (
        <Card elevation={2}>
            <CardContent>
                <Typography variant={"h6"} sx={{fontFamily:'MyFont2'}} color={'primary'}>
                    <Link component={RouterLink} to={"/getarticle/33"} sx={{textDecoration:'none'}}>
                        Sending Emails via JavaMail
                    </Link>
                </Typography>
                <FormControl>
                <Grid container>
                    <Grid item md={12}>
                        <Typography sx={{mt:'1em',mr:'1em',fontFamily:'MyFont2',fontSize:18}} color={"inherit"}>
                            Enter your email here:
                        </Typography>
                    </Grid>
                    <Grid item md={12}>
                        <TextField id={"email"} placeholder={"Email Address"} onChange={handleChangeEmail}
                                   sx={{mt:'1em'}} size={"small"} type={"email"} required={true}/>
                        <Button sx={{mt:'1.5em',textTransform:'none'}} onClick={sendEmail}>{sendButton}</Button>
                    </Grid>
                    <Grid item md={12}>
                        <Typography sx={{mt:'1em',mr:'1em',fontFamily:'MyFont2',fontSize:18}} color={"inherit"}>
                            Enter your received verification code here:
                        </Typography>
                    </Grid>
                    <Grid item md={12}>
                        <TextField id={"enteredCode"} placeholder={"Verification Code"} onChange={handleChangeCode}
                                   sx={{mt:'1em'}} size={"small"} required={true}/>
                        <Button sx={{mt:'1.5em',textTransform:'none'}} onClick={handleVerify}>Verify</Button>
                    </Grid>
                    <Grid item md={12} sx={{mt:'1em'}} color={"red"}>
                        {msg}
                    </Grid>
                </Grid>
                </FormControl>
            </CardContent>
        </Card>
    );
}

export default JavaMail;
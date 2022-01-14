import {Avatar, Card, CardActionArea, CardContent, CardHeader, Grid, Typography} from "@mui/material";
import * as React from "react";
import Insupr_LOGO from "../static/images/Insupr_LOGO.png";

function Experience(){
    return (
        <Grid item md={8} sx={{mr:'2em',mt:'2em'}}>
            <Card sx={{bgcolor: 'grey.50',mb:'2em'}}>
                <CardActionArea href={""} target={"_blank"}>
                    <CardHeader avatar={<Avatar alt="University of Waterloo" src={Insupr_LOGO} variant={"square"} sx={{ width: 150, height: 100,}}/>}></CardHeader>
                </CardActionArea>
                <CardContent>
                    <Typography variant={"body1"}>
                        I was hired by Inspur Software Co., Ltd as a software development engineer internship
                        for 3 months. During that time, I was mainly engaged in the development and programming of 2 projects:
                        <i>Think Tank</i>, and <i>Intelligent Manufacturing</i>
                    </Typography>
                    <Typography sx={{mt:'1em'}}>
                        As for <i><a href="http://znzz.ckcest.cn/" target="_blank"> Intelligent Manufacturing</a></i>,
                        it is developed based on the back-end framework of SpringMVC, I took the responsibility of
                        coding the interfaces of interaction between front-end and back-end.
                    </Typography>
                    <Typography sx={{mt:'1em'}}>
                        For <i>Think Tank</i>, it is developed based on SpringCloud microservices architecture
                        (SpringBoot, consul, ZooKeeper, Kafka, redis, mysql). My principal job was to implement the function of sending notification
                        emails and text messages to users.
                    </Typography>
                    <Typography sx={{mt:'1em'}}>
                        Over the whole internship period, I learned and got familiar with lots of popular industrial technologies, and my communication
                        skills got largely improved. It means a lot to me and inspires me to make decision to work in software development fields.
                    </Typography>
                </CardContent>
            </Card>
        </Grid>

    );
}
export default Experience;
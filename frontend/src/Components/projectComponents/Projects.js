import {Card, CardContent, Typography, Grid, CardActions, Button} from "@mui/material";
import * as React from "react";

const projects = [
    {name:'CampusLine',technologies:'Java, JavaScript, HTML, SpringBoot, Vue.js, Mybatis, JUnit, Agile Development',description:'A social networking and knowledge-sharing web application',url:'https://github.com/meng1022/CampusLine',disabled:false},
    {name:'Online Examination System',technologies:'Java, JavaScript, HTML, SpringBoot, Vue.js, Mybatis, Redis, Druid',description:'An examination system based on web providing online examination environment with high security and reliability',url:'https://github.com/meng1022/OnlineExamSystem',disabled:false},
    {name:'SelfBlog',technologies: 'Java, JavaScript, HTML, SpringMVC, React.js, Material-UI, HikariCP, Hibernate',description: 'A self blog',url:'https://github.com/meng1022/Myblog',disabled:false},
    {name:'Grade Management System',technologies:'Java, JavaScript, HTML, SpringMVC',description:'A student grade management system for students and teachers to check and upload grades',url:'https://github.com/meng1022/GradeManagementSystem',disabled:false},
    {name:'Search Engine',technologies:'Java, Retrieval System, TREC, Inverted Index',description:'A search engine using TREC Data, Boolean AND and BM25 retrieval methods',url:'https://github.com/meng1022/SearchEngine',disabled:false},
    {name:'Football Club Management System',technologies: 'C++, socketProgramming, C/S', description:'A system making it convenient to manage club members and football players among clubs',url:'',disabled:true},
    {name:'Distributed Temperature Control System', technologies: 'Java, JavaScript, HTML, SSM, websocket',description: 'A distributed temperature control system that can smartly generate bills and manage temperature of rooms',url:'',disabled:true},
    {name:'Software-Based Operating System', technologies: 'C++, Operating System',description: 'A software using C++ with basic functions of an operating system like process management, file system, memory management, interruption and UI',url:'',disabled:true},
];

function Projects(){
    // render(){
    //
    // }
    return(
        <Grid item md={8} sx={{mr:'2em',mt:'2em'}}>
            {projects.map((project)=> (
                <Grid item sx={{mb:'2em'}} md={12} xs={12} >
                    <Card sx={{bgcolor: '#fdf7ec'}}>
                        <CardContent>
                            <Typography variant={"h6"}>
                                {project.name}
                            </Typography>
                            <Typography variant={"subtitle2"} sx={{mb:2}} color={"text.secondary"}>
                                {project.technologies}
                            </Typography>
                            <Typography variant={"body1"}>
                                {project.description}
                            </Typography>
                        </CardContent>
                        <CardActions>
                            <Button size={"small"}
                                    href={project.url}
                                    target={"_blank"}
                                    disabled={project.disabled}>
                                    GitHub
                            </Button>
                        </CardActions>
                    </Card>
                </Grid>
            ))}
        </Grid>
    ) ;
}

export default Projects;
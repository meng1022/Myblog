import {
    Grid,
    Avatar,
    Tabs,
    Tab,
    Box,
    Typography,
    CardContent,
    Card,
    CardMedia,
    CardHeader,
    CardActions, CardActionArea
} from "@mui/material";
import * as React from "react";
import avatarMe from "../static/images/avatarMe.jpg";
import avatarMe1 from "../static/images/avatarMe1.png";
import BUPT_LOGO from "../static/images/BUPT_LOGO.png";
import UW_LOGO from "../static/images/UW_LOGO.png";


function TabPanel(props) {
    const { children, value, index, ...other } = props;
    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`vertical-tabpanel-${index}`}
            aria-labelledby={`vertical-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{ p: 3 }}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}

function a11yProps(index) {
    return {
        id: `vertical-tab-${index}`,
        'aria-controls': `vertical-tabpanel-${index}`,
    };
}

function AboutMe(){
    const[value,setValue] = React.useState(0);
    const handleChange = (event,newValue)=>{
        setValue(newValue);
    };

    return(
        <Grid item md={8} sm={11} xs={11}>
            <Grid container sx={{mt:'2em'}} justifyContent="center">
                    <Grid item md={3} xs={5}>
                        <Avatar alt="Meng Zhao" src={avatarMe} sx={{ width: 150, height: 150, mb:'1em'}} />
                    </Grid>
                    <Grid item md={3} xs={5}>
                        <Avatar alt="Meng Zhao" src={avatarMe1} sx={{ width: 110, height: 110, mb:'1em'}} />
                    </Grid>
                    <Grid item md={10} xs={12}>
                        <Tabs value={value} onChange={handleChange} variant="scrollable" scrollButtons allowScrollButtonsMobile>
                            <Tab label={"AboutMe"} {...a11yProps(0)}/>
                            <Tab label={"Education"} {...a11yProps(1)}/>
                            <Tab label={"Programming Skills"} {...a11yProps(2)}/>
                            <Tab label={"Contact"} {...a11yProps(3)}/>
                        </Tabs>
                    </Grid>
                <Grid item md={11} sm={11} xs={12} sx={{ml:'0em',mt:'1em'}}>
                    <TabPanel value={value} index={0}>
                        <Card sx={{bgcolor: '#fdf7ec',mb:'2em'}}>
                            <CardContent>
                                <Typography variant={"body1"}>
                                    I'm Meng Zhao, welcome to my blog, I just finished my graduate study in software. This website is
                                    developed for the sake of writing down and show you guys some of the projects i contributed to, some ideas coming
                                    to my mind and some inspirational knowledge i obtain.
                                </Typography>
                            </CardContent>
                        </Card>
                    </TabPanel>
                    <TabPanel value={value} index={1}>
                        <Card sx={{bgcolor: '#fdf7ec',mb:'2em'}}>
                            <CardActionArea href={"https://uwaterloo.ca/"} target={"_blank"}>
                                <CardHeader avatar={<Avatar alt="University of Waterloo" src={UW_LOGO} sx={{ width: 100, height: 100,}}/>}></CardHeader>
                            </CardActionArea>
                            <CardContent>
                                <Typography variant={"body1"}>
                                    I finished my graduate study in University of Waterloo, Ontario, Canada in 2021,
                                    and got my master of Engineering degree in Electrical & Computer Engineering with
                                    software specialization.
                                </Typography>
                            </CardContent>
                        </Card>
                        <Card sx={{bgcolor: '#fdf7ec'}}>
                            <CardActionArea href={"https://www.bupt.edu.cn/"} target={"_blank"}>
                                <CardHeader avatar={<Avatar alt="BUPT" src={BUPT_LOGO} sx={{ width: 100, height: 100,}}/>}></CardHeader>
                            </CardActionArea>
                            <CardContent>
                                <Typography variant={"body1"}>
                                    I pursued my undergraduate study in Beijing University of Posts and Telecommunications,
                                    Beijing, China between 2016 and 2020, and got bachelor's degree of Engineering in Network Engineering.
                                </Typography>
                            </CardContent>
                        </Card>
                    </TabPanel>
                    <TabPanel value={value} index={2}>
                        <Card sx={{bgcolor: '#fdf7ec',mb:'2em'}}>
                            <CardContent>
                                <Typography variant={"body1"}>
                                    Languages: Java, SQL, Python, C/C++, JavaScript, JSP
                                </Typography>
                            </CardContent>
                        </Card>
                        <Card sx={{bgcolor: '#fdf7ec',mb:'2em'}}>
                            <CardContent>
                                <Typography variant={"body1"}>
                                    Frameworks: Spring-related frameworks, Hibernate, Mybatis, Django, React.js, Vue.js, JUnit, Shiro
                                </Typography>
                            </CardContent>
                        </Card>
                        <Card sx={{bgcolor: '#fdf7ec',mb:'2em'}}>
                            <CardContent>
                                <Typography variant={"body1"}>
                                    Developer tools: IntelliJ IDEA, Visual Studio Code, PyCharm, WebStorm, Maven, Git, Eclipse
                                </Typography>
                            </CardContent>
                        </Card>
                        <Card sx={{bgcolor: '#fdf7ec',mb:'2em'}}>
                            <CardContent>
                                <Typography variant={"body1"}>
                                    Other: REST API, JavaMail, Redis, HikariCP, Druid
                                </Typography>
                            </CardContent>
                        </Card>
                    </TabPanel>
                    <TabPanel value={value} index={3}>
                        <Typography xs={{mb:'5em'}} textAlign={"center"}>
                            Email: zhaomeng0903@outlook.com
                        </Typography>
                    </TabPanel>

                </Grid>
            </Grid>
        </Grid>
    );
}
export default AboutMe;
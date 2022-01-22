import {Box, Grid, Paper, Typography,Link} from "@mui/material";
import {Link as RouterLink} from 'react-router-dom';
import homeImg from '../../static/images/homeImg1.jpg';

function HomePageImg(){
    return(
    <Paper elevation={0}
        sx={{
            position: 'relative',
            backgroundColor: 'grey.200',
            color: '#fff',
            mb: 4,
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat',
            backgroundPosition: 'center',
            backgroundImage: `url(${homeImg})`,
        }}
    >
        {/* Increase the priority of the hero background image
        src={post.image} alt={post.imageText}*/}

        {<img style={{ display: 'none' }}  />}
        <Box sx={{
                position: 'absolute',
                top: 0,
                bottom: 0,
                right: 0,
                left: 0,
                backgroundColor: 'rgba(205, 155, 155,.3)',
            }}
        />
        <Grid container>
            <Grid item md={8}>
                <Box sx={{
                        position: 'relative',
                        p: { xs: 3, md: 6 },
                        pr: { md: 0 },
                    }}
                >
                    <Typography component="h1" variant="h3" color="grey" gutterBottom>
                        Welcome to my Blog
                    </Typography>

                    <Link component={RouterLink} to={`/getarticles`} sx={{textDecoration:'none'}}>
                        <Typography variant={"h6"} sx={{mt:'3em'}} color={"white"}>
                            Read More...
                        </Typography>

                    </Link>
                </Box>
            </Grid>
        </Grid>
    </Paper>
    );
}
export default HomePageImg;
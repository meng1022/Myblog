import * as React from 'react';
import ImageIcon from '@mui/icons-material/Image';
import {Box, Button, Dialog, DialogActions, DialogTitle, Typography} from "@mui/material";

function ImageName(props){
    const{imgName} = props;
    if(imgName===""||imgName==null)
        return null;
    return(
      <Typography sx={{ml:'2em'}}>
          Image URL (./api/getImg/{imgName})
      </Typography>
    );
}

function UploadImg(){
    const [open,setOpen] = React.useState(false);
    const [img,setImg] = React.useState([]);
    const [imgName,setImgName] = React.useState("");

    const handleOpen = ()=>{
        setOpen(true);

    };
    const handleClose = ()=>{
        setOpen(false);
        setImgName("");
    };
    const handleChange = (event)=>{
        setImg(event.target.files[0]);
        console.log(event.target.files[0]);
    };
    function handleSubmit(){
        const formData = new FormData();
        formData.append(
            "myImage",
            img,
            img.name
        );
        (async ()=>{
            const response = await fetch("/api/uploadImg",{
                method:'POST',
                    'Content-Type':'multipart/form-data',
                body: formData,
            });
            const body = await response.json();
            setImgName(body.data);
        })();
    };

    return(
        <Box>
            <Button onClick={handleOpen} sx={{mt:'0.5em',mb:'0.5em'}}>
                <ImageIcon/> Upload Image
            </Button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Upload Image</DialogTitle>
                <Typography sx={{ml:'2em',mb:'2em'}}>
                    <input type={"file"} id={"img"} accept={"image/png,image/jpg,image/jpeg"} onChange={handleChange}/>
                </Typography>
                <ImageName imgName={imgName}/>
                <DialogActions>
                    <Button onClick={handleSubmit}>Upload</Button>
                    <Button onClick={handleClose}>Dismiss</Button>
                </DialogActions>
            </Dialog>
        </Box>
    );

}
export default UploadImg;
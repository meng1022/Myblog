package backend.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;

@Component
public class imgUtil {
    public static final String rootPath = "D:/BlogImages/work/Tomcat/localhost/ROOT";
    // under D:/BlogImages/work/Tomcat/localhost/ROOT
    public static final String prefixPath = "/prefix.txt";

    private final ResourceLoader resourceLoader;

    @Autowired
    public imgUtil(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    public int getPrefix() throws Exception{
        File file = new File(rootPath+prefixPath);

        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        int index = Integer.valueOf(br.readLine());
        br.close();

        FileOutputStream fos = new FileOutputStream(file);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(String.valueOf(index+1));
        bw.close();

        return index;
    }

    public String uploadImg(MultipartFile img) throws Exception{
//        String truePath = rootPath+"/work/Tomcat/localhost/ROOT/";
//        File file = new File(truePath);
//        if(!file.exists())
//            file.mkdirs();
//        if(!rootDir.exists())
//            rootDir.mkdir();
        String originalName = img.getOriginalFilename();
        String suffix = originalName.substring(originalName.indexOf("."));
        String imgName = getPrefix() +suffix;
        img.transferTo(new File(imgName));
        return imgName;
    }

    public Resource getImg(String imgName) {
        String imgPath = Paths.get(rootPath,imgName).toString();
        Resource resource = resourceLoader.getResource("file:" + imgPath);
        return resource;
    }
}

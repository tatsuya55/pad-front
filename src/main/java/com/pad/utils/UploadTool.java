package com.pad.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class UploadTool {
    public static String uploadImage(String type,MultipartFile imageFile) throws IOException {
        System.out.println(imageFile);
        String newImageName = null;
        if (!imageFile.isEmpty()){//传入图片非空
            //图片存储路径
            //例如  D:\shixun\images\item\categoryPicture
            ///C:/Users/1/Desktop/pad-front/target/
            /*String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();*/

            String realPath = "D:\\QQPCmgr\\Desktop\\12shixun\\pad-front\\src\\main\\resources\\static\\images\\" + type;
            //获取图片扩展名
            String extension = FilenameUtils.getExtension(imageFile.getOriginalFilename());
            //对图片重命名
            newImageName = UUIDUtils.getUUID() + "." + extension;
            //把图片文件转移至全路径
            imageFile.transferTo(new File(realPath+"/"+newImageName));
        }
        //返回新图片名
        return newImageName;
    }
}

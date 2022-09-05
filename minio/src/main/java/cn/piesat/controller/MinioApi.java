package cn.piesat.controller;

import cn.piesat.model.Fileinfo;
import cn.piesat.model.MinioProp;
import cn.piesat.service.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhouxp
 */

@RestController
@RequestMapping("/api/file")
@Slf4j
public class MinioApi {

    @Resource
    private MinioService minioService;

    @Resource
    private MinioProp minioProperties;

    @GetMapping("/list")
    public Object list() {

        return minioService.listObjects(minioProperties.getBucketName());

    }

    @PostMapping("/upload")
    public Object uploadFile(MultipartFile file) {
        Map<String ,Object> result =new HashMap<>();
        try {
            String fileName = file.getOriginalFilename();
            assert fileName != null;

            // 根据业务设计，设置存储路径：按天创建目录
            String objectName = new SimpleDateFormat("yyyy-MM-dd/").format(new Date())
                    + UUID.randomUUID().toString()
                    + fileName.substring(fileName.lastIndexOf("."));

            minioService.upload(file);
            log.info("文件格式为:{}", file.getContentType());
            log.info("文件原名称为:{}", fileName);
            log.info("文件对象路径为:{}", objectName);
            result.put("code",200);
            result.put("url",minioService.getFileUrl(fileName));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code",500);
            result.put("msg","上传失败");
            return result;
        }
    }

    @DeleteMapping("/delete")
    public Object deleteFile(@RequestBody Fileinfo fileinfo) {
        Map<String ,Object> result =new HashMap<>();
        boolean status = minioService.delFile(minioProperties.getBucketName(), fileinfo.getFilename());
        if(status){
            result.put("code",200);
            result.put("msg","删除成功");
        }else {
            result.put("code",500);
            result.put("msg","上传失败");
        }
        return result;
    }
}

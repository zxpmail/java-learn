package cn.piesat.service.impl;

import cn.piesat.model.Fileinfo;
import cn.piesat.service.MinioService;
import cn.piesat.utils.MinioUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MinioServiceImpl implements MinioService {
    @Resource
    private MinioUtils minioUtils;

    @Override
    public Boolean bucketExists(String bucketName) {
        return minioUtils.bucketExists(bucketName);
    }

    @Override
    public void makeBucket(String bucketName) {
        minioUtils.makeBucket(bucketName);
    }

    @SneakyThrows
    @Override
    public List<Fileinfo> listObjects(String bucketName) {
        return minioUtils.listObjects(bucketName);
    }

    @Override
    public Boolean upload(MultipartFile multipartFile) {
        return minioUtils.putObject(multipartFile);
    }

    @Override
    public void upload(MultipartFile[] multipartFile) {
        minioUtils.putObject(multipartFile);
    }

    @Override
    public Boolean delFile(String bucketName,String fileName) {
        return minioUtils.removeObject(bucketName, fileName);
    }

    @Override
    public String getFileUrl(String fileName) {
        return minioUtils.getObjectUrl(fileName);
    }
}

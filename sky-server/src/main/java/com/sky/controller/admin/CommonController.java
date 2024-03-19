package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * Common controller
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "Common Controller API")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    @ApiOperation("Upload the file")
    public Result<String> upload(MultipartFile file) {
        log.info("Upload the file: {}", file);
        try {
            // Get the original file name
            String originalFilename = file.getOriginalFilename();
            // cut the file name xxxx.png
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = UUID.randomUUID() + extension;
            // Upload the file to the OSS
            String url = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(url);
        } catch (IOException e) {
            log.error("Upload the file error: {}", e.getMessage());
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}

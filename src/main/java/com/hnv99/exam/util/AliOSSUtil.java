package com.hnv99.exam.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;


/**
 * Utility class for interacting with Alibaba Cloud OSS.
 * Provides methods for uploading files to OSS and validating file properties.
 */
@Component
public class AliOSSUtil {

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.access-key-id}")
    private String accessKeyId;

    @Value("${oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${oss.bucket-name}")
    private String bucketName;

    /**
     * Uploads a file to Alibaba Cloud OSS.
     *
     * @param file the file to be uploaded
     * @return the URL of the uploaded file
     * @throws IOException if an I/O error occurs
     */
    public String upload(MultipartFile file) throws IOException {
        // Get the input stream of the uploaded file
        InputStream inputStream = file.getInputStream();

        // Avoid file overwriting by generating a unique file name
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IOException("Failed to get the file name of the uploaded file, it is null.");
        }
        String fileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));

        // Upload file to OSS
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, inputStream);

        // Construct the file access URL
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;

        // Close the OSS client
        ossClient.shutdown();

        // Return the URL of the uploaded file
        return url;
    }

    /**
     * Checks if the given filename has a common image extension.
     *
     * @param filename the name of the file
     * @return true if the file has a common image extension, false otherwise
     */
    public boolean isImage(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        String[] imageExtensions = {"png", "jpg", "jpeg", "bmp"};
        return Arrays.asList(imageExtensions).contains(extension);
    }

    /**
     * Checks if the size of the given file exceeds 50KB.
     *
     * @param file the file to check
     * @return true if the file size exceeds 50KB, false otherwise
     */
    public boolean isOverSize(MultipartFile file) {
        return file.getSize() > 50 * 1024;
    }
}

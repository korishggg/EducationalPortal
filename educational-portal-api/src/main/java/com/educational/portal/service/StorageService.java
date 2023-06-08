package com.educational.portal.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.educational.portal.repository.ResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class StorageService {
	@Value("${application.bucket.name}")
	private String bucketName;

	private AmazonS3 s3Client;

	public StorageService(AmazonS3 s3Client) {
		this.s3Client = s3Client;
	}

	public byte[] downloadFile(String fileName) {
		S3Object s3Object = s3Client.getObject(bucketName, fileName);
		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		try {
			byte[] content = IOUtils.toByteArray(inputStream);
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String deleteFile(String fileName) {
		s3Client.getObject(bucketName, fileName);
		return fileName + " removed";
	}

	private String generateFileName(MultipartFile multipartFile) {
		return System.nanoTime() + "-" + multipartFile.getOriginalFilename();
	}

	public String uploadFile(MultipartFile file, String pathPrefix) {
		String fileName = pathPrefix + generateFileName(file);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		try {
			InputStream inputStream = file.getInputStream();
			var putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, metadata).withCannedAcl(CannedAccessControlList.Private);
			s3Client.putObject(putObjectRequest);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return fileName;
	}
}

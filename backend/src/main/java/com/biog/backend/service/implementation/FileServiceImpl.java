package com.biog.backend.service.implementation;

import com.biog.backend.service.FileService;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Value("${gcp.bucket.name}")
    private String bucketName;

    @Autowired
    Storage storage;

    @Override
    public List<String> listOfFiles() {

        List<String> list = new ArrayList<>();
        Page<Blob> blobs = storage.list(bucketName);
        for (Blob blob : blobs.iterateAll()) {
            list.add(blob.getName());
        }
        return list;
    }

    @Override
    public ByteArrayResource downloadFile(String fileName) {

        Blob blob = storage.get(bucketName, fileName);
        ByteArrayResource resource = new ByteArrayResource(blob.getContent());

        return resource;
    }

    @Override
    public boolean deleteFile(String fileName) {

        Blob blob = storage.get(bucketName, fileName);
        return blob.delete();
    }

    @Override
    public void uploadFiles(MultipartFile[] files) throws IOException {
        for (int i = 0; i < files.length; i++) {
            BlobId blobId = BlobId.of(bucketName, files[i].getOriginalFilename());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(files[i].getContentType()).build();
            storage.create(blobInfo, files[i].getBytes());
        }
    }

    @Override
    public String getFileURL(String fileName) {
        return "https://storage.googleapis.com/" + bucketName + "/" + fileName;
    }

}

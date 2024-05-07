package com.biog.backend.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GoogleDriveService {
    Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException;

    Drive getInstance() throws GeneralSecurityException, IOException;

    String getfiles() throws IOException, GeneralSecurityException;

    String uploadFile(MultipartFile file);

}

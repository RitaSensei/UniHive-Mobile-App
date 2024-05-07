package com.biog.backend.service;

import com.biog.backend.model.Request;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

public interface RequestService {
    List<Request> getAll(UUID... schoolId) throws AccessDeniedException;

    Request updateRequest(UUID id, Request newrequest, UUID... schoolId) throws AccessDeniedException;

    void deleteRequest(UUID id, UUID... schoolId) throws AccessDeniedException;

    Request getRequest(UUID id, UUID... schoolId) throws AccessDeniedException;
}

package com.biog.unihiveandroid.model;

public class PasswordUpdateRequest {
    private final String oldPassword;
    private final String newPassword;

    public PasswordUpdateRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}


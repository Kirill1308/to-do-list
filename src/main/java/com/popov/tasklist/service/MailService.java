package com.popov.tasklist.service;

import com.popov.tasklist.domain.MailType;
import com.popov.tasklist.domain.user.User;

import java.util.Properties;

public interface MailService {

    void sendEmail(User user, MailType mailType, Properties params);
}

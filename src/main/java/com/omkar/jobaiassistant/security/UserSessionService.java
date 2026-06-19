package com.omkar.jobaiassistant.security;

import com.omkar.jobaiassistant.entity.User;
import com.omkar.jobaiassistant.entity.UserSession;
import com.omkar.jobaiassistant.repository.UserSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserSessionService {

    private final UserSessionRepository sessionRepository;

    public UserSessionService(UserSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public UserSession createSession(User user, String refreshToken, String device, String ip) {

        UserSession session = new UserSession();
        session.setSessionId(UUID.randomUUID().toString());
        session.setUser(user);
        session.setRefreshToken(refreshToken);
        session.setDeviceInfo(device);
        session.setIpAddress(ip);
        session.setActive(true);

        return sessionRepository.save(session);
    }

    public List<UserSession> getActiveSessions(User user) {
        return sessionRepository.findByUserAndActiveTrue(user);
    }

    public void deactivateSession(String refreshToken) {
        sessionRepository.findByRefreshToken(refreshToken).ifPresent(session -> {
            session.setActive(false);
            sessionRepository.save(session);
        });
    }

    public void logoutAll(User user) {
        List<UserSession> sessions = sessionRepository.findByUserAndActiveTrue(user);

        for (UserSession s : sessions) {
            s.setActive(false);
        }

        sessionRepository.saveAll(sessions);
    }
}
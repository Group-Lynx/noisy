package com.noisy.noisy.repository;

import com.noisy.noisy.model.LoginSession;
import com.noisy.noisy.model.LoginSessionKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoginRepository extends JpaRepository<LoginSession, LoginSessionKey> {
    LoginSession findByToken(UUID token);
}

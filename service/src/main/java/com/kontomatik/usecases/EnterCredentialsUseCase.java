package com.kontomatik.usecases;

import com.kontomatik.usecases.entity.OwnerSession;
import com.kontomatik.usecases.entity.SessionGateway;
import com.kontomatik.models.Credentials;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EnterCredentialsUseCase {

  private final SessionGateway sessionGateway;

  public EnterCredentialsUseCase(SessionGateway sessionGateway) {
    this.sessionGateway = sessionGateway;
  }

  public void run(String sessionId, String[] credentials) {
    OwnerSession current = sessionGateway.getById(UUID.fromString(sessionId));
    current.session.executeEnterCredentialsCommand(
      new Credentials(orNull(credentials, 0), orNull(credentials, 1), orNull(credentials, 2))
    );
  }

  private String orNull(String[] credentials, int index) {
    if (credentials.length > index)
      return credentials[index];
    return null;
  }

}

package ro.bynaus.nohs.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterResponse {
    private String accessToken;
    private String message;
    private Boolean successfulCompletion;
    private UserDTO registeredUserDTO;
}

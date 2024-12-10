package dmd.clientmanagement.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class OAuth2TokenRequest {

    private String code;

}

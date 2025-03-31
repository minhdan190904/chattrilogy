package org.example.chattrilogy.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.chattrilogy.domain.User;

@Setter
@Getter
public class ResLoginDTO {
    private String token;

    private User user;

}

package fintech.toyproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private String name;
    private String email;
    private String password;
    private String auth;
    private Integer balance;

}

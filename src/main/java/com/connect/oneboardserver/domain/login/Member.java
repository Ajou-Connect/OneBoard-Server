package com.connect.oneboardserver.domain.login;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class Member {

    private Long id;

    @NotEmpty
    private String name;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;
    @NotNull
    private int user_type;
    @NotEmpty
    private String university;
    @NotEmpty
    private String major;
    @NotEmpty
    private String student_num;
    private String lecture_id;


}

package com.example.myjpawork.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class UserRequest{

    @NotNull(message = "userId必传")
    @Min(value = 1, message = "userId格式错误")
    private Long userId;

    @NotNull(message = "name必传")
    @NotBlank(message = "name格式错误")
    private String name;

    @Valid
    @NotEmpty(message = "至少传一个girlFriend")
    private List<UserRequest> girlFriends;

}
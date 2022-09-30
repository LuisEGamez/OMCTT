package com.omctt.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ToDoDto {

    private String title;
    private Boolean completed;
    private String userName;
}

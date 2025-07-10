package com.inf.graduation.dto.thesisDefense;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DefenseSessionStudentsDto {

    private LocalDate defenseDate;

    private List<Long> committeeIds;
    private List<String> committeeNames;

    private List<Long> studentIds = new ArrayList<>();
    private List<String> studentNames;
}

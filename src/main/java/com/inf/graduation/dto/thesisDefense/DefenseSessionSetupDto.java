package com.inf.graduation.dto.thesisDefense;

import com.inf.graduation.util.validation.annotation.UniqueCommitteeMembers;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class DefenseSessionSetupDto {

    @NotNull(message = "Defense date must be selected")
    @FutureOrPresent(message = "Defense date must be today or in the future")
    private LocalDate defenseDate;

    @UniqueCommitteeMembers
    private List<Long> committeeIds = new ArrayList<>(Arrays.asList(null, null, null));
    private List<String> committeeNames;

}

package com.inf.graduation.dto.thesisDefense;

import com.inf.graduation.dto.thesis.ThesisSummaryDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DefenseSummaryDTO {

    private Long id;
    private LocalDate defenseDate;
    private List<ThesisSummaryDTO> theses;
}

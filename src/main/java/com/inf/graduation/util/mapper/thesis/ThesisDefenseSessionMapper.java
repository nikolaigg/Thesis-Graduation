package com.inf.graduation.util.mapper.thesis;

import com.inf.graduation.dto.thesisDefense.DefenseSessionSetupDto;
import com.inf.graduation.dto.thesisDefense.DefenseSessionStudentsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ThesisDefenseSessionMapper {

    DefenseSessionStudentsDto toDefenseSessionStudentsDto(DefenseSessionSetupDto setupDto);
}

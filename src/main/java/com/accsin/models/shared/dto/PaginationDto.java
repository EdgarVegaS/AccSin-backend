package com.accsin.models.shared.dto;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaginationDto {
    
    private Optional<String> sortBy;
    private Optional<Integer> page;
    private Optional<Integer> quantity;
}

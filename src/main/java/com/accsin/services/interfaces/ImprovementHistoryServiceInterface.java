package com.accsin.services.interfaces;

import java.util.List;

import com.accsin.models.shared.dto.ImprovementHistoryDto;

public interface ImprovementHistoryServiceInterface {

    List<ImprovementHistoryDto> getAll();
    List<ImprovementHistoryDto> getByUser(String userId);
    void createImprovementHistory(ImprovementHistoryDto request);
    
}

package com.accsin.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.accsin.entities.ImprovementHistoryEntity;
import com.accsin.entities.UserEntity;
import com.accsin.models.shared.dto.ImprovementHistoryDto;
import com.accsin.repositories.ImprovementHistoryRepository;
import com.accsin.repositories.UserRepository;
import com.accsin.services.interfaces.ImprovementHistoryServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ImprovementHistoryService implements ImprovementHistoryServiceInterface{

    private ModelMapper mapper;
    private ImprovementHistoryRepository improvementHistoryRepository;
    private UserRepository userRepository;

    public ImprovementHistoryService(ModelMapper mapper, ImprovementHistoryRepository improvementHistoryRepository, UserRepository userRepository){
        this.improvementHistoryRepository = improvementHistoryRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<ImprovementHistoryDto> getAll() {
        List<ImprovementHistoryDto> listDto = new ArrayList<>();
        Iterable<ImprovementHistoryEntity> entities = improvementHistoryRepository.findAll();
        for (ImprovementHistoryEntity improvementHistoryEntity : entities) {
            listDto.add(mapper.map(improvementHistoryEntity, ImprovementHistoryDto.class));
        }
        return listDto;
    }

    @Override
    public List<ImprovementHistoryDto> getByUser(String userId) {
        List<ImprovementHistoryDto> listDto = new ArrayList<>();
        UserEntity user = userRepository.findByUserId(userId);
        List<ImprovementHistoryEntity> entities = improvementHistoryRepository.findByUserId(user.getId());
        for (ImprovementHistoryEntity improvementHistoryEntity : entities) {
            listDto.add(mapper.map(improvementHistoryEntity, ImprovementHistoryDto.class));
        }
        return listDto;
    }

    @Override
    public void createImprovementHistory(ImprovementHistoryDto request) {
        
        UserEntity user = userRepository.findByUserId(request.getUserId());
        ImprovementHistoryEntity entity = new ImprovementHistoryEntity();
        entity.setDate(request.getDate());
        entity.setImprovementsNumber(request.getImprovementsNumber());
        entity.setJsonImprovements(request.getJsonImprovements());
        entity.setImprovementHistoryId(UUID.randomUUID().toString());
        entity.setUserId(user.getId());

        improvementHistoryRepository.save(entity);
        
    }
    
}

package com.accsin.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.accsin.entities.CheckListEntity;
import com.accsin.entities.ContractEntity;
import com.accsin.entities.ContractTypeEntity;
import com.accsin.entities.UserEntity;
import com.accsin.models.shared.dto.ContractCreateDto;
import com.accsin.models.shared.dto.ContractDto;
import com.accsin.models.shared.dto.ContractTypeDto;
import com.accsin.models.shared.dto.PaginationDto;
import com.accsin.repositories.CheckListRepository;
import com.accsin.repositories.ContractRepository;
import com.accsin.repositories.ContractTypeRepository;
import com.accsin.repositories.UserRepository;
import com.accsin.services.interfaces.ContractServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ContractService implements ContractServiceInterface {

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    ContractTypeRepository contractTypeRepository;
    
    @Autowired
    CheckListService checkListService;
    
    @Autowired
    CheckListRepository checkListReposirory;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceService serviceService;

    @Autowired
    ModelMapper mapper;

    @Override
    public ContractDto createContract(ContractCreateDto contract) throws Exception {
        ContractTypeEntity typeEntity = contractTypeRepository.findByName(contract.getContractType());
        UserEntity userEntity = userRepository.findByUserId(contract.getContractorCompany());
        
        boolean existPreviusContract = contractRepository.findByUserId(userEntity.getId()).isEmpty();
        if(!existPreviusContract) {
        	 throw new Exception("Ya existe un contrato vigente para el Cliente");
        }
        ContractEntity entity = new ContractEntity();
        entity.setRequiredCheckList(contract.isRequiredCheckList());
        entity.setActive(contract.isActive());
        entity.setBasePrice(contract.getBasePrice());
        entity.setContractId(UUID.randomUUID().toString());
        entity.setContractType(typeEntity);
        entity.setContractorCompany(contract.getContractorCompany());
        entity.setCreateAt(new Date());
        entity.setFinalPrice(contract.getFinalPrice());
        entity.setUser(userEntity);
        contractRepository.save(entity);
        
        contract.getServices().forEach(s -> {
            serviceService.createService(s, entity);
        });
        if (contract.getCheckList() != null) {
            contract.getCheckList().setContractId(entity.getContractId());
            checkListService.createCheckList(contract.getCheckList());
        }
        Optional<ContractEntity> entityCreated = contractRepository.findById(entity.getId());
        return mapper.map(entityCreated.get(), ContractDto.class);
    }

    @Override
    public ContractDto updateContract(ContractCreateDto contract,String contractId) {
        ContractTypeEntity typeEntity = contractTypeRepository.findByName(contract.getContractType());
        UserEntity userEntity = userRepository.findByEmail("");
        ContractEntity entity = contractRepository.findByContractId(contractId);
        CheckListEntity checkListEntity = checkListReposirory.getCheckListbyContractId((int)entity.getId());
        entity.setRequiredCheckList(contract.isRequiredCheckList());
        entity.setActive(contract.isActive());
        entity.setBasePrice(contract.getBasePrice());
        entity.setContractType(typeEntity);
        entity.setContractorCompany(contract.getContractorCompany());
        entity.setFinalPrice(contract.getFinalPrice());
        entity.setUser(userEntity);
        checkListEntity.setJsonList(contract.getCheckList().getJsonList());
        checkListReposirory.save(checkListEntity);
        contractRepository.save(entity);
        return mapper.map(entity, ContractDto.class);
    }

    @Override
    public void deleteContract(String id) {
        ContractEntity entity = contractRepository.findByContractId(id);
        entity.setUser(null);
        contractRepository.delete(entity);
        
    }

    @Override
    public List<ContractDto> getAllContracts() {
        List<ContractDto> listDto = new ArrayList<>();
        Iterable<ContractEntity> contracts = contractRepository.findAll();
        contracts.forEach(c -> listDto.add(mapper.map(c, ContractDto.class)));
        return listDto;
    }

    @Override
    public List<ContractDto> getContractByUserId(String userId) {
        List<ContractDto> listDto = new ArrayList<>();
        UserEntity userEntity = userRepository.findByUserId(userId);
        List<ContractEntity> listContracts = contractRepository.findByUserId(userEntity.getId());
        listContracts.forEach(c -> listDto.add(mapper.map(c, ContractDto.class)));
        return listDto;
    }
    @Override
    public List<ContractTypeDto> getContractTypes(){
    	List<ContractTypeDto> listDto = new ArrayList<>();
    	Iterable<ContractTypeEntity> contractsTypes = contractTypeRepository.findAll();
    	contractsTypes.forEach(c -> listDto.add(mapper.map(c, ContractTypeDto.class)));
    	return listDto;
    }

    @Override
    public List<ContractDto> getContractPagination(PaginationDto dto) {
        List<ContractDto> listReturn = new ArrayList<>();
        Iterable<ContractEntity> listEntities = contractRepository.findAll(PageRequest.of(dto.getPage().orElse(0), dto.getQuantity().orElse(5),Sort.Direction.ASC,dto.getSortBy().orElse("id")));
        for (ContractEntity contractEntity : listEntities) {
            listReturn.add(mapper.map(contractEntity,ContractDto.class));
        }
        return listReturn;
    }

}
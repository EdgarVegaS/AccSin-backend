package com.accsin.services.interfaces;

import java.util.List;

import com.accsin.models.shared.dto.ContractCreateDto;
import com.accsin.models.shared.dto.ContractDto;
import com.accsin.models.shared.dto.ContractTypeDto;

public interface ContractServiceInterface {
    
    public ContractDto createContract(ContractCreateDto contract);
    public ContractDto updateContract(ContractCreateDto contract,String contractId);
    public void deleteContract(String id);
    public List<ContractDto> getAllContracts();
    public List<ContractDto> getContractByUserId(String userId);
	public List<ContractTypeDto> getContractTypes();
}

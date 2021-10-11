package com.accsin.utils;

import com.accsin.models.responses.CheckListResponse;
import com.accsin.models.responses.ServiceResponse;
import com.accsin.models.responses.UserResponse;
import com.accsin.models.shared.dto.CheckListDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;

public final class MethodsUtils {
    
    public static void setCreateServiceResponse(ServiceResponse response){
        response.getUser().setService(null);
        response.getMonthlyPayment().forEach(m -> m.setService(null));
    }

    public static CheckListResponse setCheckListResponse(CheckListDto dto, ObjectMapper objMapper,Logger logger,ModelMapper mapper){
        CheckListResponse response = new CheckListResponse();
        try {
            JsonNode nodeList = objMapper.readTree(dto.getJsonList());
            JsonNode nodeMejoras = objMapper.readTree(dto.getJsonMejoras());
            response.setJsonList(nodeList);
            response.setJsonMejoras(nodeMejoras);
        } catch (Exception e) {
            logger.error("");
        }
        response.setUser(mapper.map(dto.getUser(), UserResponse.class));
        response.setCheckListId(dto.getCheckListId());
        return response;
    }
}

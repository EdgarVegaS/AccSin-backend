package com.accsin.services;

import static com.accsin.utils.DateTimeUtils.parseStringToDateTime;
import static com.accsin.utils.DateTimeUtils.getStringActualDate;
import static com.accsin.utils.DateTimeUtils.getStringNextMonth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.accsin.entities.ScheduleEntity;
import com.accsin.entities.views_entities.ScheduleMonthView;
import com.accsin.models.responses.DatesScheduleResponse;
import com.accsin.models.responses.ScheduleProfesionalResponse;
import com.accsin.models.shared.dto.ScheduleDto;
import com.accsin.repositories.ScheduleMonthViewRepository;
import com.accsin.repositories.ScheduleRepository;
import com.accsin.repositories.UserRepository;
import com.accsin.services.interfaces.ScheduleServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService implements ScheduleServiceInterface {

    private UserRepository userRepository;
    private ScheduleRepository scheduleRepository;
    private ModelMapper mapper;
    private ScheduleMonthViewRepository monthViewRepository;

    public ScheduleService(UserRepository userRepository, ScheduleRepository scheduleRepository, ModelMapper mapper,
            ScheduleMonthViewRepository monthViewRepository) {
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
        this.mapper = mapper;
        this.monthViewRepository = monthViewRepository;
    }

    @Override
    public ScheduleEntity createScheduleForServiceRequest(String date, String professionaId) {

        ScheduleEntity entity = new ScheduleEntity();
        entity.setScheduleId(UUID.randomUUID().toString());
        entity.setDate(parseStringToDateTime(date));
        entity.setProfessional(userRepository.findByUserId(professionaId));
        scheduleRepository.save(entity);
        return entity;
    }

    @Override
    public List<ScheduleDto> getScheduleNextMonth() {
        List<ScheduleDto> listDto = new ArrayList<>();
        List<ScheduleEntity> listEntities = scheduleRepository.findByDateRange(getStringActualDate(),
                getStringNextMonth());
        listEntities.forEach(s -> {
            listDto.add(mapper.map(s, ScheduleDto.class));
        });
        return listDto;
    }

    @Override
    public void testView() {
        List<ScheduleMonthView> list = monthViewRepository.getAllRecord();
        List<ScheduleProfesionalResponse> listResponse = new ArrayList<>();
        for (ScheduleMonthView smv : list) {
            Optional<ScheduleProfesionalResponse> spr = listResponse.stream().filter(r -> r.getProfessionalId().equals(smv.getProfessionalId())).findFirst();
            if (!spr.isEmpty()) {
                Optional<DatesScheduleResponse> dsr = spr.get().getDates().stream().filter(d -> d.getDate().equals(smv.getScheduleDay())).findFirst();
                if (!dsr.isEmpty()) {
                    System.out.println("f");
                }else{
                    DatesScheduleResponse dsr1 = new DatesScheduleResponse();
                    dsr1.setDate(smv.getScheduleDay());
                    spr.get().getDates().add(dsr1);
                }
            }else{
                ScheduleProfesionalResponse spr1 = new ScheduleProfesionalResponse();
                spr1.setProfessionalEmail(smv.getProfessionalEmail());
                spr1.setProfessionalId(smv.getProfessionalId());
                spr1.setProfessionalName(smv.getProfessionalName()); 
                DatesScheduleResponse dsr = new DatesScheduleResponse();
                dsr.setDate(smv.getScheduleDay());
                spr1.getDates().add(dsr);
                listResponse.add(spr1);
            }
            
        }
        list.forEach(s -> System.out.println(s));
    }
}
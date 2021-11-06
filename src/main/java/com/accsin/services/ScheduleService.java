package com.accsin.services;

import static com.accsin.utils.DateTimeUtils.parseStringToDateTime;
import static com.accsin.utils.DateTimeUtils.getStringActualDate;
import static com.accsin.utils.DateTimeUtils.getStringNextMonth;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.accsin.entities.ScheduleEntity;
import com.accsin.entities.views_entities.AvailableDaysView;
import com.accsin.entities.views_entities.ScheduleMonthView;
import com.accsin.models.shared.dto.DateAvailableDto;
import com.accsin.models.shared.dto.ScheduleDto;
import com.accsin.repositories.AvailableDaysRepository;
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
    private AvailableDaysRepository availableDaysRepository;

    public ScheduleService(UserRepository userRepository, ScheduleRepository scheduleRepository, ModelMapper mapper,
            ScheduleMonthViewRepository monthViewRepository, AvailableDaysRepository availableDaysRepository) {
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
        this.mapper = mapper;
        this.monthViewRepository = monthViewRepository;
        this.availableDaysRepository = availableDaysRepository;
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
        list.forEach(s -> System.out.println(s));
    }

    @Override
    public List<DateAvailableDto> getAvailableDays() {
        List<DateAvailableDto> listReturn = new ArrayList<>();
        List<AvailableDaysView> listDays = availableDaysRepository.getAllRecords();
        for (AvailableDaysView availableDaysView : listDays) {
            if (availableDaysView.getDatesCount() >= availableDaysView.getProfessionalTotal()) {
                String year = availableDaysView.getDates().substring(0,4);
                String month = availableDaysView.getDates().substring(5,7);
                String day = availableDaysView.getDates().substring(8,10);
                DateAvailableDto dto = DateAvailableDto.builder().day(day).month(month).year(year).build();
                listReturn.add(dto);
            }
        }
        return listReturn;
    }
}

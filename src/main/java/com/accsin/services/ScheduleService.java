package com.accsin.services;

import static com.accsin.utils.DateTimeUtils.parseStringToDateTime;
import static com.accsin.utils.DateTimeUtils.getStringActualDate;
import static com.accsin.utils.DateTimeUtils.getStringNextMonth;
import static com.accsin.utils.DateTimeUtils.getHourString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import com.accsin.entities.ScheduleEntity;
import com.accsin.entities.UserEntity;
import com.accsin.entities.views_entities.AvailableDaysView;
import com.accsin.entities.views_entities.ProfessionalScheduleView;
import com.accsin.entities.views_entities.ScheduleMonthView;
import com.accsin.models.shared.dto.DateAvailableDto;
import com.accsin.models.shared.dto.ScheduleDto;
import com.accsin.repositories.AvailableDaysRepository;
import com.accsin.repositories.ProfessionalScheduleRepository;
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
    private ProfessionalScheduleRepository professionalScheduleRepository;

    public ScheduleService(UserRepository userRepository, ScheduleRepository scheduleRepository, ModelMapper mapper,
            ScheduleMonthViewRepository monthViewRepository, AvailableDaysRepository availableDaysRepository,ProfessionalScheduleRepository professionalScheduleRepository) {
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
        this.mapper = mapper;
        this.monthViewRepository = monthViewRepository;
        this.availableDaysRepository = availableDaysRepository;
        this.professionalScheduleRepository = professionalScheduleRepository;
    }

    @Override
    public ScheduleEntity createScheduleForServiceRequest(String date) {

        String profesionalId =findProfessional(date);
        UserEntity userEntity = userRepository.findByUserId(profesionalId);
        String dateHours = getHoursAssigned(userEntity.getId(),date);
        

        ScheduleEntity entity = new ScheduleEntity();
        entity.setScheduleId(UUID.randomUUID().toString());
        entity.setDate(parseStringToDateTime(dateHours));
        entity.setProfessional(userRepository.findByUserId(profesionalId));
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

    private String findProfessional(String date){
        Random random = new Random();
        List<UserEntity> listUserProfessional =  userRepository.getUsersByRole(4);
        List<ProfessionalScheduleView> listProfessionals = professionalScheduleRepository.getByDate(date);

        if (listUserProfessional.size() == listProfessionals.size()) {
            Optional<ProfessionalScheduleView> professional = listProfessionals.stream().min(Comparator.comparing(ProfessionalScheduleView::getConteo));
            return professional.get().getProfessionalId();
        }else{
            List<UserEntity> listFiltered = getDiferences(listUserProfessional, listProfessionals);
            UserEntity user = listFiltered.get(random.nextInt(listFiltered.size()));
            return user.getUserId();
        }
    }


    private List<UserEntity> getDiferences(List<UserEntity> listUser, List<ProfessionalScheduleView> listProf){

        List<UserEntity> listReturn = new ArrayList<>();
        for (UserEntity user : listUser) {
            boolean exist = false;
            for (ProfessionalScheduleView professional : listProf) {
                if (user.getUserId().equals(professional.getProfessionalId())) {
                    exist = true;
                }
            }
            if (!exist) {
                listReturn.add(user);
            }
        }
        return listReturn;
    }

    private String getHoursAssigned(long userId, String date ){

        List<ScheduleEntity> listEntities = scheduleRepository.findByUserAndDate(userId, date);
        boolean nineHour = false;
        boolean twelveHour = false;
        for (ScheduleEntity scheduleEntity : listEntities) {
            String hour = getHourString(scheduleEntity.getDate());
            if (hour.equals("09:00:00")) {
                nineHour = true;
            }
            if (hour.equals("12:00:00")) {
                twelveHour = true;
            }

        }
        
        if (!nineHour) {
            return date + " " + "09:00:00";
        }else if(!twelveHour){
            return date + " " + "12:00:00";
        } else{
            return date + " " + "15:00:00";
        }
    }
}

package com.accsin.services;

import static com.accsin.utils.DateTimeUtils.getDateFormatFromDate;
import static com.accsin.utils.DateTimeUtils.getActualYearMonthNumber;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.accsin.entities.ScheduleEntity;
import com.accsin.entities.ServiceRequestEntity;
import com.accsin.entities.TrainingInformationEntity;
import com.accsin.entities.UserEntity;
import com.accsin.entities.views_entities.ScheduleServiceRequestView;
import com.accsin.models.request.CreateServiceRequestRequest;
import com.accsin.models.request.UpdateServiceRequest;
import com.accsin.models.shared.dto.ScheduleNextMonthDto;
import com.accsin.models.shared.dto.TrainingInformationDto;
import com.accsin.repositories.ScheduleNextMonthRepository;
import com.accsin.repositories.ServiceRepository;
import com.accsin.repositories.ServiceRequestRepository;
import com.accsin.repositories.TrainingInformationRepository;
import com.accsin.repositories.UserRepository;
import com.accsin.services.interfaces.ScheduleServiceInterface;
import com.accsin.services.interfaces.ServiceRequestServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class ServiceRequestService implements ServiceRequestServiceInterface {

    private ServiceRequestRepository serviceRequestRepository;
    private UserRepository userRepository;
    private ServiceRepository serviceRepository;
    private ScheduleServiceInterface scheduleService;
    private ScheduleNextMonthRepository nextMonthRepository;
    private TrainingInformationRepository informationRepository;
    private ModelMapper mapper;
    private JavaMailSender emailSender;

    public ServiceRequestService(ServiceRequestRepository serviceRequestRepository, UserRepository userRepository,
            ServiceRepository serviceRepository, ScheduleServiceInterface scheduleService,ScheduleNextMonthRepository nextMonthRepository,
            ModelMapper mapper, TrainingInformationRepository trainingInformationRepository,JavaMailSender emailSender) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.scheduleService = scheduleService;
        this.nextMonthRepository = nextMonthRepository;
        this.mapper = mapper;
        this.informationRepository = trainingInformationRepository;
        this.emailSender = emailSender;
    }

    @Override
    public void createServiceRequest(CreateServiceRequestRequest request) throws Exception {

        UserEntity clientEntity = userRepository.findByUserId(request.getClientId());
        ServiceRequestEntity serviceRequestEntity = new ServiceRequestEntity();
        serviceRequestEntity.setServiceRequestId(UUID.randomUUID().toString());
        serviceRequestEntity.setClient(clientEntity);
        serviceRequestEntity.setService(serviceRepository.findByServiceId(request.getServiceId()));
        serviceRequestEntity.setCreateAt(new Date());
        if(request.getServiceId().equalsIgnoreCase("27c2c51c-d700-41c9-8c01-b1c0541727db")){
        	serviceRequestEntity.setCompleted(true);
        	serviceRequestEntity.setObservations("Cargo automático, Solicitado por el usuario ID: " + request.getClientId());
            ScheduleEntity scheduleEntity = scheduleService.createScheduleForServiceRequestNoProfessional(request.getDateSelected());
            serviceRequestEntity.setSchudule(scheduleEntity);
            serviceRequestRepository.save(serviceRequestEntity);
        }else if(request.getServiceId().equalsIgnoreCase("062e0e7a-dc5a-44d7-a61d-107c9684b70e")){
            ScheduleEntity scheduleEntity = scheduleService.createScheduleForServiceRequest(request.getDateSelected());
            serviceRequestEntity.setSchudule(scheduleEntity);
            serviceRequestRepository.save(serviceRequestEntity);
            TrainingInformationEntity trainingEntity = new TrainingInformationEntity();
            trainingEntity.setTrainingInformationId(UUID.randomUUID().toString());
            trainingEntity.setAssistants(request.getAssistants());
            trainingEntity.setMaterials(request.getMaterials());
            trainingEntity.setServiceRequestId(serviceRequestEntity.getId());
            informationRepository.save(trainingEntity);
        }else if(request.getServiceId().equalsIgnoreCase("5f516e39-aa1d-42bc-9d3c-286417e27b1b")){
            ScheduleEntity scheduleEntity = scheduleService.createScheduleForServiceRequest(request.getDateSelected());
            serviceRequestEntity.setSchudule(scheduleEntity);
            serviceRequestRepository.save(serviceRequestEntity);
            UserEntity professionalEtntiy = userRepository.findById(scheduleEntity.getProfessional().getId()).get();
            sentEmailAccidentRequest(professionalEtntiy.getFirstName()+" "+professionalEtntiy.getLastName(), professionalEtntiy.getEmail(), clientEntity.getFirstName() +" "+ clientEntity.getLastName(), clientEntity.getEmail());

        }else{
            ScheduleEntity scheduleEntity = scheduleService.createScheduleForServiceRequest(request.getDateSelected());
            serviceRequestEntity.setSchudule(scheduleEntity);
            serviceRequestRepository.save(serviceRequestEntity);
        }
        
    }

    @Override
    public List<ScheduleNextMonthDto> getNextMonthServices(String date) {
        List<ScheduleNextMonthDto> listDto = new ArrayList<>();
        List<ScheduleServiceRequestView> listEntities = nextMonthRepository.getAll(date);
        for (ScheduleServiceRequestView scheduleNextMonthView : listEntities) {
            listDto.add(mapper.map(scheduleNextMonthView, ScheduleNextMonthDto.class));
        }
        return listDto;
    }

    @Override
    public void deleteServiceRequest(String id) {
        ServiceRequestEntity entity = serviceRequestRepository.findByServiceRequestId(id);
        serviceRequestRepository.delete(entity);
        scheduleService.deleteSchedule(entity.getSchudule().getId());
    }

    @Override
    public void updateServiceRequest(UpdateServiceRequest request) {
        
        ServiceRequestEntity entity = serviceRequestRepository.findByServiceRequestId(request.getServiceRequestId());
        entity.setCompleted(request.isCompleted());
        entity.setObservations(request.getObservations());
        entity.setCheckListCompleted(request.isCheckListCompleted());
        entity.setService(serviceRepository.findByServiceId(request.getServiceId()));
        serviceRequestRepository.save(entity);
        
    }

    private boolean compareDatesFromRequest(String dateRequest, Date dateSchedule){

        String dateScheduleString = getDateFormatFromDate(dateSchedule);
        if (dateRequest.equals(dateScheduleString)) {
            return true;
        }
        return false;
    }

    @Override
    public List<ScheduleNextMonthDto> getNextMonthServicesByUser(String userId,String type) {
        List<ScheduleNextMonthDto> listDto = new ArrayList<>();
        List<ScheduleServiceRequestView> listEntities = new ArrayList<>();
        if (type.equalsIgnoreCase("user")) {
            listEntities = nextMonthRepository.getByUser(userId);
        }else{
            listEntities = nextMonthRepository.getByProfessional(userId);
        }
        
        for (ScheduleServiceRequestView scheduleNextMonthView : listEntities) {
            listDto.add(mapper.map(scheduleNextMonthView, ScheduleNextMonthDto.class));
        }
        return listDto;
    }

    @Override
    public List<ScheduleNextMonthDto> getDailyServicesByUser(String userId,String type) {
        List<ScheduleNextMonthDto> listDto = new ArrayList<>();

        List<ScheduleServiceRequestView> listEntities = new ArrayList<>();
        if (type.equalsIgnoreCase("user")) {
            listEntities = nextMonthRepository.getDailyByUser(userId);
        }else{
            listEntities = nextMonthRepository.getDailyByProfessional(userId);
        }
        for (ScheduleServiceRequestView scheduleNextMonthView : listEntities) {
            listDto.add(mapper.map(scheduleNextMonthView, ScheduleNextMonthDto.class));
        }
        return listDto;
    }

    @Override
    public List<ScheduleNextMonthDto> getDateServicesByUser(String userId, String date,String type) {
        
        List<ScheduleNextMonthDto> listDto = new ArrayList<>();
        List<ScheduleServiceRequestView> listEntities = new ArrayList<>();
        if (type.equalsIgnoreCase("user")) {
            listEntities = nextMonthRepository.getDateByUser(userId,date);
        }else{
            listEntities = nextMonthRepository.getDateByProfessional(userId,date);
        }
        //List<ScheduleServiceRequestView> listEntities = nextMonthRepository.getDateByUser(userId,date);
        for (ScheduleServiceRequestView scheduleNextMonthView : listEntities) {
            listDto.add(mapper.map(scheduleNextMonthView, ScheduleNextMonthDto.class));
        }
        return listDto;
    }

    @Override
    public List<ScheduleNextMonthDto> getBetweenDateServicesByUser(String userId, String dateStart,String dateFinish,String type) {
        
        List<ScheduleNextMonthDto> listDto = new ArrayList<>();
        List<ScheduleServiceRequestView> listEntities = new ArrayList<>();
        if (type.equalsIgnoreCase("user")) {
            listEntities = nextMonthRepository.getBetweenDateByUser(userId,dateStart,dateFinish);
        }else{
            listEntities = nextMonthRepository.getBetweenDateByProfessional(userId,dateStart,dateFinish);
        }
        //List<ScheduleServiceRequestView> listEntities = nextMonthRepository.getBetweenDateByUser(userId,dateStart,dateFinish);
        for (ScheduleServiceRequestView scheduleNextMonthView : listEntities) {
            listDto.add(mapper.map(scheduleNextMonthView, ScheduleNextMonthDto.class));
        }
        return listDto;
    }

    @Override
    public ScheduleNextMonthDto getServiceRequestByServiceRequestId(String serviceRequestId) {
        
        ScheduleServiceRequestView entity = nextMonthRepository.getByServiceRequestId(serviceRequestId);
        return mapper.map(entity, ScheduleNextMonthDto.class);
    }

    @Override
    public void createCheckListServiceRequestUpdate(String userId) {

        ServiceRequestEntity serviceRequestEntity = new ServiceRequestEntity();
        serviceRequestEntity.setServiceRequestId(UUID.randomUUID().toString());
        serviceRequestEntity.setClient(userRepository.findByUserId(userId));
        serviceRequestEntity.setService(serviceRepository.findByName("Modificación CheckList"));
        ScheduleEntity scheduleEntity = scheduleService.createScheduleForServiceRequestNoProfessional(getActualYearMonthNumber()+"/01 09:00:00");
        serviceRequestEntity.setSchudule(scheduleEntity);
        serviceRequestEntity.setCreateAt(new Date());
        serviceRequestRepository.save(serviceRequestEntity);
    }

    @Override
    public TrainingInformationDto getTrainignByTrainingId(String trainigId) {
        TrainingInformationEntity entity = informationRepository.findByTrainingInformationId(trainigId);
        return mapper.map(entity, TrainingInformationDto.class);
    }

    @Override
    public TrainingInformationDto getTrainignByServiceRequest(String id) {
        ServiceRequestEntity sre = serviceRequestRepository.findByServiceRequestId(id);
        long idsre = sre.getId();
        
        TrainingInformationEntity entity = informationRepository.findByServiceRequestId(idsre);

        return mapper.map(entity, TrainingInformationDto.class);

    }

    public boolean sentEmailAccidentRequest(String nameProf, String email,String nameClient,String emailClient) throws Exception {
        try {
            javax.mail.internet.MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom("noreply@accsin.com");
            message.setTo(email);
            message.setSubject("AccSin - Solicitud por Accidente");
            String template = setTemplateRerpotAccident(nameProf, null,nameClient,emailClient);
            message.setText(template, true);
            emailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private String setTemplateRerpotAccident(String name, String mes,String nameClient,String emailClient) {

        String template = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\r\n"
                + "<head>\r\n"
                + "<!--[if gte mso 9]>\r\n"
                + "<xml>\r\n"
                + "  <o:OfficeDocumentSettings>\r\n"
                + "    <o:AllowPNG/>\r\n"
                + "    <o:PixelsPerInch>96</o:PixelsPerInch>\r\n"
                + "  </o:OfficeDocumentSettings>\r\n"
                + "</xml>\r\n"
                + "<![endif]-->\r\n"
                + "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"
                + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
                + "  <meta name=\"x-apple-disable-message-reformatting\">\r\n"
                + "  <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\r\n"
                + "  <title></title>\r\n"
                + "  \r\n"
                + "    <style type=\"text/css\">\r\n"
                + "      table, td { color: #000000; } a { color: #161a39; text-decoration: underline; }\r\n"
                + "@media only screen and (min-width: 620px) {\r\n"
                + "  .u-row {\r\n"
                + "    width: 600px !important;\r\n"
                + "  }\r\n"
                + "  .u-row .u-col {\r\n"
                + "    vertical-align: top;\r\n"
                + "  }\r\n"
                + "\r\n"
                + "  .u-row .u-col-100 {\r\n"
                + "    width: 600px !important;\r\n"
                + "  }\r\n"
                + "\r\n"
                + "}\r\n"
                + "\r\n"
                + "@media (max-width: 620px) {\r\n"
                + "  .u-row-container {\r\n"
                + "    max-width: 100% !important;\r\n"
                + "    padding-left: 0px !important;\r\n"
                + "    padding-right: 0px !important;\r\n"
                + "  }\r\n"
                + "  .u-row .u-col {\r\n"
                + "    min-width: 320px !important;\r\n"
                + "    max-width: 100% !important;\r\n"
                + "    display: block !important;\r\n"
                + "  }\r\n"
                + "  .u-row {\r\n"
                + "    width: calc(100% - 40px) !important;\r\n"
                + "  }\r\n"
                + "  .u-col {\r\n"
                + "    width: 100% !important;\r\n"
                + "  }\r\n"
                + "  .u-col > div {\r\n"
                + "    margin: 0 auto;\r\n"
                + "  }\r\n"
                + "}\r\n"
                + "body {\r\n"
                + "  margin: 0;\r\n"
                + "  padding: 0;\r\n"
                + "}\r\n"
                + "\r\n"
                + "table,\r\n"
                + "tr,\r\n"
                + "td {\r\n"
                + "  vertical-align: top;\r\n"
                + "  border-collapse: collapse;\r\n"
                + "}\r\n"
                + "\r\n"
                + "p {\r\n"
                + "  margin: 0;\r\n"
                + "}\r\n"
                + "\r\n"
                + ".ie-container table,\r\n"
                + ".mso-container table {\r\n"
                + "  table-layout: fixed;\r\n"
                + "}\r\n"
                + "\r\n"
                + "* {\r\n"
                + "  line-height: inherit;\r\n"
                + "}\r\n"
                + "\r\n"
                + "a[x-apple-data-detectors='true'] {\r\n"
                + "  color: inherit !important;\r\n"
                + "  text-decoration: none !important;\r\n"
                + "}\r\n"
                + "\r\n"
                + "</style>\r\n"
                + "  \r\n"
                + "  \r\n"
                + "\r\n"
                + "<!--[if !mso]><!--><link href=\"https://fonts.googleapis.com/css?family=Lato:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\r\n"
                + "\r\n"
                + "</head>\r\n"
                + "\r\n"
                + "<body class=\"clean-body u_body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #f9f9f9;color: #000000\">\r\n"
                + "  <!--[if IE]><div class=\"ie-container\"><![endif]-->\r\n"
                + "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\r\n"
                + "  <table style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #f9f9f9;width:100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
                + "  <tbody>\r\n"
                + "  <tr style=\"vertical-align: top\">\r\n"
                + "    <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n"
                + "    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #f9f9f9;\"><![endif]-->\r\n"
                + "    \r\n"
                + "\r\n"
                + "<div class=\"u-row-container\" style=\"padding: 0px;background-color: #f9f9f9\">\r\n"
                + "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #f9f9f9;\">\r\n"
                + "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n"
                + "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: #f9f9f9;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #f9f9f9;\"><![endif]-->\r\n"
                + "      \r\n"
                + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
                + "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
                + "  <div style=\"width: 100% !important;\">\r\n"
                + "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
                + "  \r\n"
                + "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
                + "  <tbody>\r\n"
                + "    <tr>\r\n"
                + "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:15px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
                + "        \r\n"
                + "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #f9f9f9;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n"
                + "    <tbody>\r\n"
                + "      <tr style=\"vertical-align: top\">\r\n"
                + "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n"
                + "          <span>&#160;</span>\r\n"
                + "        </td>\r\n"
                + "      </tr>\r\n"
                + "    </tbody>\r\n"
                + "  </table>\r\n"
                + "\r\n"
                + "      </td>\r\n"
                + "    </tr>\r\n"
                + "  </tbody>\r\n"
                + "</table>\r\n"
                + "\r\n"
                + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n"
                + "  </div>\r\n"
                + "</div>\r\n"
                + "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
                + "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n"
                + "    </div>\r\n"
                + "  </div>\r\n"
                + "</div>\r\n"
                + "\r\n"
                + "\r\n"
                + "\r\n"
                + "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
                + "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n"
                + "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n"
                + "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n"
                + "      \r\n"
                + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
                + "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
                + "  <div style=\"width: 100% !important;\">\r\n"
                + "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
                + "  \r\n"
                + "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
                + "  <tbody>\r\n"
                + "    <tr>\r\n"
                + "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:25px 10px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
                + "        \r\n"
                + "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n"
                + "  <tr>\r\n"
                + "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n"
                + "      \r\n"
                + "      <img align=\"center\" border=\"0\" src=\"images/image-1.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 29%;max-width: 168.2px;\" width=\"168.2\"/>\r\n"
                + "      \r\n"
                + "    </td>\r\n"
                + "  </tr>\r\n"
                + "</table>\r\n"
                + "\r\n"
                + "      </td>\r\n"
                + "    </tr>\r\n"
                + "  </tbody>\r\n"
                + "</table>\r\n"
                + "\r\n"
                + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n"
                + "  </div>\r\n"
                + "</div>\r\n"
                + "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
                + "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n"
                + "    </div>\r\n"
                + "  </div>\r\n"
                + "</div>\r\n"
                + "\r\n"
                + "\r\n"
                + "\r\n"
                + "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
                + "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #24860c;\">\r\n"
                + "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n"
                + "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #24860c;\"><![endif]-->\r\n"
                + "      \r\n"
                + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color: #3aaa35;width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
                + "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
                + "  <div style=\"background-color: #3aaa35;width: 100% !important;\">\r\n"
                + "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
                + "  \r\n"
                + "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
                + "  <tbody>\r\n"
                + "    <tr>\r\n"
                + "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:35px 10px 10px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
                + "        \r\n"
                + "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n"
                + "  <tr>\r\n"
                + "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n"
                + "      \r\n"
                + "      <img align=\"center\" border=\"0\" src=\"images/image-2.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 10%;max-width: 58px;\" width=\"58\"/>\r\n"
                + "      \r\n"
                + "    </td>\r\n"
                + "  </tr>\r\n"
                + "</table>\r\n"
                + "\r\n"
                + "      </td>\r\n"
                + "    </tr>\r\n"
                + "  </tbody>\r\n"
                + "</table>\r\n"
                + "\r\n"
                + "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
                + "  <tbody>\r\n"
                + "    <tr>\r\n"
                + "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
                + "        \r\n"
                + "  <div style=\"line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n"
                + "    <p style=\"font-size: 14px; line-height: 140%; text-align: center;\"><span style=\"font-size: 28px; line-height: 39.2px; color: #ffffff; font-family: Lato, sans-serif;\">Aviso de solicitud por accidente</span></p>\r\n"
                + "  </div>\r\n"
                + "\r\n"
                + "      </td>\r\n"
                + "    </tr>\r\n"
                + "  </tbody>\r\n"
                + "</table>\r\n"
                + "\r\n"
                + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n"
                + "  </div>\r\n"
                + "</div>\r\n"
                + "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
                + "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n"
                + "    </div>\r\n"
                + "  </div>\r\n"
                + "</div>\r\n"
                + "\r\n"
                + "\r\n"
                + "\r\n"
                + "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
                + "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n"
                + "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n"
                + "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\r\n"
                + "      \r\n"
                + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
                + "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
                + "  <div style=\"width: 100% !important;\">\r\n"
                + "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
                + "  \r\n"
                + "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
                + "  <tbody>\r\n"
                + "    <tr>\r\n"
                + "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 40px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
                + "        \r\n"
                + "  <div style=\"color: #1d1d1b; line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n"
                + "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Hola "
                + name + ".</span></p>\r\n"
                + "<p style=\"font-size: 14px; line-height: 140%;\">&nbsp;</p>\r\n"
                + "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">El cliente "+nameClient+" ha registrado un nuevo accidente."
                + "</span></p>\r\n"
                + "<p style=\"font-size: 14px; line-height: 140%;\">&nbsp;</p>\r\n"
                + "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Por favor contactarse con el cliente</span></p>\r\n"
                + "<p style=\"font-size: 14px; line-height: 140%;\">&nbsp;</p>\r\n"
                + "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Nombre: "+nameClient+"</span></p>\r\n"
                + "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Correo: "+emailClient+"</span></p>\r\n"
                + "  </div>\r\n"
                + "\r\n"
                + "      </td>\r\n"
                + "    </tr>\r\n"
                + "  </tbody>\r\n"
                + "</table>\r\n"
                + "\r\n"
                + "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
                + "  <tbody>\r\n"
                + "    <tr>\r\n"
                + "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 40px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
                + "        \r\n"
                + "  <div style=\"color: #1d1d1b; line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n"
                + "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #888888; font-size: 14px; line-height: 19.6px;\"><em><span style=\"font-size: 16px; line-height: 22.4px;\"></span></em></span><br /><span style=\"color: #888888; font-size: 14px; line-height: 19.6px;\"><em><span style=\"font-size: 16px; line-height: 22.4px;\">&nbsp;</span></em></span></p>\r\n"
                + "  </div>\r\n"
                + "\r\n"
                + "      </td>\r\n"
                + "    </tr>\r\n"
                + "  </tbody>\r\n"
                + "</table>\r\n"
                + "\r\n"
                + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n"
                + "  </div>\r\n"
                + "</div>\r\n"
                + "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
                + "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n"
                + "    </div>\r\n"
                + "  </div>\r\n"
                + "</div>\r\n"
                + "\r\n"
                + "\r\n"
                + "\r\n"
                + "<div class=\"u-row-container\" style=\"padding: 0px;background-color: #f9f9f9\">\r\n"
                + "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #1c103b;\">\r\n"
                + "    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\r\n"
                + "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: #f9f9f9;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #1c103b;\"><![endif]-->\r\n"
                + "      \r\n"
                + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color: #3aaa35;width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n"
                + "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
                + "  <div style=\"background-color: #3aaa35;width: 100% !important;\">\r\n"
                + "  <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\r\n"
                + "  \r\n"
                + "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
                + "  <tbody>\r\n"
                + "    <tr>\r\n"
                + "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:15px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
                + "        \r\n"
                + "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"0%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid ;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n"
                + "    <tbody>\r\n"
                + "      <tr style=\"vertical-align: top\">\r\n"
                + "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\r\n"
                + "          <span>&#160;</span>\r\n"
                + "        </td>\r\n"
                + "      </tr>\r\n"
                + "    </tbody>\r\n"
                + "  </table>\r\n"
                + "\r\n"
                + "      </td>\r\n"
                + "    </tr>\r\n"
                + "  </tbody>\r\n"
                + "</table>\r\n"
                + "\r\n"
                + "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n"
                + "  </div>\r\n"
                + "</div>\r\n"
                + "<!--[if (mso)|(IE)]></td><![endif]-->\r\n"
                + "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\r\n"
                + "    </div>\r\n"
                + "  </div>\r\n"
                + "</div>\r\n"
                + "\r\n"
                + "\r\n"
                + "    <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n"
                + "    </td>\r\n"
                + "  </tr>\r\n"
                + "  </tbody>\r\n"
                + "  </table>\r\n"
                + "  <!--[if mso]></div><![endif]-->\r\n"
                + "  <!--[if IE]></div><![endif]-->\r\n"
                + "</body>\r\n"
                + "\r\n"
                + "</html>\r\n"
                + "";

        return template;
    }
    
}
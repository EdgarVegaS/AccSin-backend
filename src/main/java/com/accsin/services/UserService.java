package com.accsin.services;

import static com.accsin.utils.DateTimeUtils.parseStringToDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.accsin.entities.BusinessUserEntity;
import com.accsin.entities.UserEntity;
import com.accsin.entities.recoveryPasswordEntity;
import com.accsin.exeptions.ExistEmailExeption;
import com.accsin.models.shared.dto.BusinessUserDto;
import com.accsin.models.shared.dto.UserDto;
import com.accsin.repositories.BusinessUserRepository;
import com.accsin.repositories.PositionRepository;
import com.accsin.repositories.RoleRepository;
import com.accsin.repositories.UserRepository;
import com.accsin.repositories.recoveryPasswordRepository;
import com.accsin.services.interfaces.UserServiceInterface;
import com.accsin.utils.DateTimeUtils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;


    @Autowired
    BusinessUserRepository businessUserRepository;

    @Autowired
    PositionRepository positionRepository;
    
    @Autowired
    recoveryPasswordRepository recoveryPasswordRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bcrypt;
    
    @Autowired
    ModelMapper mapper;
    
    @Autowired
	private JavaMailSender emailSender;

    @Override
    public UserDto createUser(UserDto user) {

        if (userRepository.findByEmail(user.getEmail()) != null){
            throw new ExistEmailExeption("El Correo ya existe");
        }
            
        UserEntity userEntity = new UserEntity();

        userEntity.setEncryptedPassword(bcrypt.encode(user.getPassword()));
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setRole(roleRepository.findByName(user.getRole().getName()));
        userEntity.setCreateAt(new Date());
        userEntity.setBirthDate(parseStringToDate(user.getBirthDate()));
        userEntity.setBusinessUser((user.getBusinessUser() == null) ? null : businessUserRepository.findById(Long.parseLong(user.getBusinessUser())).get());
        userEntity.setEmail(user.getEmail());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setParticularCondition((user.getParticularCondition() == null)? null:  user.getParticularCondition());
        userEntity.setPosition((user.getPosition() == null) ? null : positionRepository.findById(Long.parseLong(user.getPosition())).get());
        userEntity.setRut(user.getRut());
    
        UserEntity storedUserDetails = userRepository.save(userEntity);
        UserDto userToReturn = mapper.map(storedUserDetails, UserDto.class);

        return userToReturn;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }

    @Override
    public UserDto getUser(String email) {

        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        UserDto userToReturn = new UserDto();
        mapper.map(userEntity, userToReturn);
        userToReturn.setBusinessUser(userEntity.getBusinessUser() ==  null ? "0" : Long.toString(userEntity.getBusinessUser().getId()));
        userToReturn.setPassword(null);

        return userToReturn;
    }

    @Override
    public UserDto updateUser(UserDto user) {
        UserEntity userEntity =  userRepository.findByUserId(user.getUserId());
        
        if (userEntity == null) {
            throw new UsernameNotFoundException(user.getEmail());
        }
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setRut(user.getRut());
        userEntity.setEmail(user.getEmail());
        userEntity.setBirthDate(parseStringToDate(user.getBirthDate()));
        userEntity.setBusinessUser((user.getBusinessUser() == null) ? null : businessUserRepository.findById(Long.parseLong(user.getBusinessUser())).get());
        userEntity.setEmail(user.getEmail());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setRole(roleRepository.findByName(user.getRole().getName()));
        userEntity.setParticularCondition((user.getParticularCondition() == null)? null:  user.getParticularCondition());
        userEntity.setPosition((user.getPosition() == null) ? null : positionRepository.findById(Long.parseLong(user.getPosition())).get());
        userEntity.setRut(user.getRut());
        userRepository.save(userEntity);
        return user;
    }

    @Override
    public List<UserDto> getAllUser() {
        
        Iterable<UserEntity> listEntity = userRepository.findAll();
        List<UserDto> listReturn = new ArrayList<>();
        for (UserEntity userEntity : listEntity) {
            listReturn.add(mapper.map(userEntity, UserDto.class));
        }

        return listReturn;
    }

    @Override
    public UserDto getUserByUserId(String id) {
        UserEntity userEntity = userRepository.findByUserId(id);
        return mapper.map(userEntity, UserDto.class);
    }

    @Override
    public List<UserDto> getUsersByRole(int role) {
        List<UserDto> usersDto = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.getUsersByRole(role);
        userEntities.forEach(u ->{
            usersDto.add(mapper.map(u, UserDto.class));
        });
        return usersDto;
    }
    
    @Override
    public List<BusinessUserDto> getBusinessUser() {
        List<BusinessUserDto> businessUsersDto = new ArrayList<>();
        Iterable<BusinessUserEntity> businessUserEntities = businessUserRepository.findAll();
        		businessUserEntities.forEach(u ->{
        			businessUsersDto.add(mapper.map(u, BusinessUserDto.class));
        });
        return businessUsersDto;
    }

    @Override
    public boolean updatePasswordUser(String email,String password) {
        
        UserEntity userEntity = userRepository.findByEmail(email);
        userEntity.setEncryptedPassword(bcrypt.encode(password));
        UserEntity userUpdated = userRepository.save(userEntity);
        if (userUpdated != null) {
    		recoveryPasswordEntity request = recoveryPasswordRepository.findByEmail(email);
    		request.setEnable(false);
    		recoveryPasswordRepository.save(request);
            return true;
        }
        return false;
    }
    
    
       
    @Override
    public boolean sentEmailRecovery(String email) throws Exception {
    	   Date now = new Date();
    	   Date tomorrow = DateTimeUtils.addDays(now, 1);
    	   boolean sentMail = searchPasswordRequest(email);

    	   if(sentMail) {
    		   try {           		        
    			   javax.mail.internet.MimeMessage mimeMessage = emailSender.createMimeMessage();
    			   MimeMessageHelper message = new MimeMessageHelper(mimeMessage); 			   
    	            message.setFrom("noreply@accsin.com");
    	            String code = randomCodeGenerator();
    	            recoveryPasswordEntity passCode = new recoveryPasswordEntity();
    	            passCode.setEmail(email);
    	            passCode.setTemporalCode(code);
    	            passCode.setCreateAt(now);
    	            passCode.setExpiredAt(tomorrow);
    	            passCode.setEnable(true);
    	            message.setTo(email); 
    	            message.setSubject("AccSin - Recuperación de Contraseña"); 
    	            String template = setTemplate(email, code);
    	            message.setText(template, true);
    	            recoveryPasswordRepository.save(passCode);
    	            emailSender.send(mimeMessage);
    	    		return true;
    				
    			} catch (Exception e) {
    				e.printStackTrace();
    				return false;
    			}
    	   } else
    		   throw new Exception("Ya existe un código vigente");
    }
    
    @Override
	public boolean validateCode(String email, String code) {
    	Date now = new Date();
    	try {
    		recoveryPasswordEntity recovery = recoveryPasswordRepository.findByEmailAndCode(email, code, now);
    		recovery.getEmail();
    		return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	private boolean searchPasswordRequest(String email) {
    	try {
    		Date now = new Date();
    		//Si no encuentra un objeto, no existe ninguna solicitud, por lo que retorna true
    		recoveryPasswordEntity request = recoveryPasswordRepository.findByEmail(email);
    		if(request.getExpiredAt().before(now)) {
    			//Si existe, sin embargo la fecha de expiración ya ha sucedido, anula la solicitud anterior y retorna true
    			request.setEnable(false);
    			recoveryPasswordRepository.save(request);
    			return true;
    		} 
    		return false;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
    }
    
    private String randomCodeGenerator() {
        StringBuilder builder;
        int i = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"; 
        //create the StringBuffer
        builder = new StringBuilder(i); 
        for (int m = 0; m < i; m++) { 
            // generate numeric
            int myindex 
                = (int)(characters.length() 
                        * Math.random()); 
            // add the characters
            builder.append(characters 
                        .charAt(myindex)); 
        } 
        return builder.toString(); 
    	
    }
    
    private String setTemplate(String name, String code) {
    	
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
        		+ "    <p style=\"font-size: 14px; line-height: 140%; text-align: center;\"><span style=\"font-size: 28px; line-height: 39.2px; color: #ffffff; font-family: Lato, sans-serif;\">Restablecimiento de Contrase&ntilde;a</span></p>\r\n"
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
        		+ "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Hola " + name + " ,</span></p>\r\n"
        		+ "<p style=\"font-size: 14px; line-height: 140%;\">&nbsp;</p>\r\n"
        		+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Hemos recibido una solicitod para cambiar la contrase&ntilde;a.</span></p>\r\n"
        		+ "<p style=\"font-size: 14px; line-height: 140%;\">&nbsp;</p>\r\n"
        		+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Para restablecer la contrase&ntilde;a, utiliza el siguiente c&oacute;digo</span></p>\r\n"
        		+ "<p style=\"font-size: 14px; line-height: 140%;\">&nbsp;</p>\r\n"
        		+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">"+ code +  "</span></p>\r\n"
        		+ "<p style=\"font-size: 14px; line-height: 140%;\">&nbsp;</p>\r\n"
        		+ "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">e ingr&eacute;salo en el siguiente link, recuerda que solo tiene una vigencia de 1 d&iacute;a.</span></p>\r\n"
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
        		+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 40px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
        		+ "        \r\n"
        		+ "<div align=\"center\">\r\n"
        		+ "  <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;font-family:'Lato',sans-serif;\"><tr><td style=\"font-family:'Lato',sans-serif;\" align=\"center\"><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"http://localhost:4200/recovery\" style=\"height:51px; v-text-anchor:middle; width:204px;\" arcsize=\"2%\" stroke=\"f\" fillcolor=\"#3aaa35\"><w:anchorlock/><center style=\"color:#FFFFFF;font-family:'Lato',sans-serif;\"><![endif]-->\r\n"
        		+ "    <a href=\"http://localhost:4200/recovery\" target=\"_blank\" style=\"box-sizing: border-box;display: inline-block;font-family:'Lato',sans-serif;text-decoration: none;-webkit-text-size-adjust: none;text-align: center;color: #FFFFFF; background-color: #3aaa35; border-radius: 1px;-webkit-border-radius: 1px; -moz-border-radius: 1px; width:auto; max-width:100%; overflow-wrap: break-word; word-break: break-word; word-wrap:break-word; mso-border-alt: none;\">\r\n"
        		+ "      <span style=\"display:block;padding:15px 40px;line-height:120%;\"><span style=\"font-size: 18px; line-height: 21.6px;\">Restablecer Contraseña</span></span>\r\n"
        		+ "    </a>\r\n"
        		+ "  <!--[if mso]></center></v:roundrect></td></tr></table><![endif]-->\r\n"
        		+ "</div>\r\n"
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
        		+ "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #888888; font-size: 14px; line-height: 19.6px;\"><em><span style=\"font-size: 16px; line-height: 22.4px;\">Si no has solicitado un cambio de clave, puedes ignorar este correo.</span></em></span><br /><span style=\"color: #888888; font-size: 14px; line-height: 19.6px;\"><em><span style=\"font-size: 16px; line-height: 22.4px;\">&nbsp;</span></em></span></p>\r\n"
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
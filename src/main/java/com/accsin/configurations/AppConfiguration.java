package com.accsin.configurations;

import com.accsin.SpringAppContext;
import com.accsin.security.AppProperties;

import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {
    
    @Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder (){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringAppContext appContext(){
		return new SpringAppContext();
	}

	@Bean(name = "AppProperties")
	public AppProperties getAppProperties(){
		return new AppProperties();
	}

	@Bean(name = "ModelMapper")
	public ModelMapper getModelMapper(){
		ModelMapper mapper = new ModelMapper();

		//mapper.typeMap(UserDto.class, UserRest.class).addMappings( m -> m.skip(UserRest::setPost));

		return mapper;
	}

	@Bean(name = "restConnection")
	public RestTemplate restSapConnection(RestTemplateBuilder restTemplateBuilder){
		return restTemplateBuilder.build();
	}
}

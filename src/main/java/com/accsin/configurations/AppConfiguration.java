package com.accsin.configurations;

import com.accsin.SpringAppContext;
import com.accsin.models.responses.MonthlyPaymentResponse;
import com.accsin.models.shared.dto.MonthlyPaymentDto;
import com.accsin.security.AppProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfiguration implements WebMvcConfigurer {
    
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
		mapper.typeMap(MonthlyPaymentDto.class, MonthlyPaymentResponse.class).addMappings(m -> m.skip(MonthlyPaymentResponse::setService));

		return mapper;
	}

	@Bean(name = "restConnection")
	public RestTemplate restConnection(RestTemplateBuilder restTemplateBuilder){
		return restTemplateBuilder.build();
	}

	@Bean(name = "ObjectMapper")
	public ObjectMapper getObjectMapper(){
		return new ObjectMapper();
	}

	@Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**").allowedMethods("*").allowedOrigins("*").allowedHeaders("*");
    }
}

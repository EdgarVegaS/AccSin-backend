package com.accsin.models.shared.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessUserDto implements Serializable {

	private Long id;
    private String name;

}
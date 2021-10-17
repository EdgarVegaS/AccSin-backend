package com.accsin.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "action_type")
public class ActionTypeEntity implements Serializable{

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String action_type_id;

	@Column(nullable = false, length = 80)
	private Double duration;
	
    @Column(nullable = false)
    private Date created_at;

	@OneToMany(mappedBy = "actionType")
	private List<ActionEntity> actions;

	@Column(nullable = false)
	private Double price;

	@Column(nullable = false)
	private Double contract_price;

	@Column(nullable = false)
	private boolean enable;

	@Column(name= "action_type_name", nullable = false, length = 80)
	private String actionTypeName;

}

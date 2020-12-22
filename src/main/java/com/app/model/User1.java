package com.app.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;



import lombok.Data;

@Data
@Entity
@Table(name = "usertable")
public class User1{
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "uname")
	private String userName;
	@Column(name = "pwd")
	private String password;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "usertab",joinColumns = @JoinColumn(name="id"))
	@Column(name = "rl")
	private Set<String> role;

}

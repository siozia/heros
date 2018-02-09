package com.example.hero;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author siozia
 *
 */
@Data
public class Hero {
	private Integer id;
	private String name;
	private String desctiption;
	private Integer health;
	private Integer armour;
	private Integer shield;
	private String real_name;
	private Integer age;
	private Integer height;
	private String affiliation;
	private String base_of_operations;
	private Integer difficulty;
	private String url;
	private Role role;
	private List<Role> sub_roles;
	private List<Ability> abilities;
}

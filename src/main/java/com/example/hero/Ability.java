package com.example.hero;

import lombok.Data;

/**
 * "id": 5,
            "name": "Configuration: Recon",
            "description": "In Recon mode, Bastion is fully mobile, outfitted with a submachine gun that fires steady bursts of bullets at medium range.",
            "is_ultimate": false,
            "url": "https:\/\/overwatch-api.net\/api\/v1\/ability\/5"
 * @author siozia
 *
 */

@Data
public class Ability {
	private Integer id;
	private String name;
	private String description;
	private Boolean is_ultimate;
	private String url;	
	private Hero hero;
	
}

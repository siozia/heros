package com.example.hero;

import java.util.List;

import lombok.Data;

@Data
public class Page {
	
	private Integer total;
	private String first;
	private String next;
	private String previous;
	private String last;
	
	private List<?> data;

}

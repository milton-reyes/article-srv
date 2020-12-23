package com.yukaju.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "articles")
public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true, nullable = false)
	private String title;
	
	//@Column(nullable = false, length = 20000)
	@Column(length = 20000)
	private String richtext;
	
	//@Column(nullable = false, length = 20000)
	@Column(length = 20000)
	private String content;
	
	@Column(name = "article_date")
	private LocalDateTime articleDate;
	
	@Column(name = "article_updated")
	private LocalDateTime articleUpdated;
	
	@Column(unique = true, nullable = false)
	private String value;
	
	private String img;

}

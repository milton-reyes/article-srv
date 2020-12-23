package com.yukaju.dtos;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ArticleDto {
	
	private int id;
	private String title;
	private String richtext;
	private String content;
	private LocalDateTime articleDate;
	private LocalDateTime articleUpdated;
	private String value;
	private String img;

}

package com.yukaju.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yukaju.controllers.ContentController;
import com.yukaju.dtos.ArticleDto;
import com.yukaju.exceptions.InvalidArticleException;

@SpringBootTest
public class ContentControllerTest {
	
	@Autowired
	ContentController controller;
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	@Test
	public void exceedingMaxCharsAllowed_throwException() throws InvalidArticleException {
		ArticleDto articledto = new ArticleDto();
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < 22999; i++) {
			content.append("0"+i);
		}
		articledto.setContent(content.toString());
		articledto.setTitle("Test article");
		articledto.setValue("test value");

		InvalidArticleException e = assertThrows(InvalidArticleException.class, () -> {
			controller.addArticle(articledto);
		});
		
		String expectedMsg = "Exceding the maximum characters allowed.";
		String actualMsg = e.getMessage();
		
		assertTrue(actualMsg.contains(expectedMsg));
		
	}

}

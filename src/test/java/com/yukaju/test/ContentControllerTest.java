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
	
	private final String maxCharMsg = "Exceding the maximum characters allowed.";
	private String exceptionMsg;
	
	
	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	@Test
	public void exceedingMaxCharsAllowedContentField_throwsException() throws InvalidArticleException {
		

		InvalidArticleException e = assertThrows(InvalidArticleException.class, () -> {
			
			ArticleDto artContent = new ArticleDto();
			StringBuilder content = new StringBuilder();
			for (int i = 0; i < 22999; i++) {
				content.append("0");
			}
			artContent.setContent(content.toString());
			artContent.setTitle("Test article content");
			artContent.setValue("test value content");
			
			controller.addArticle(artContent);
		});
		
		this.setExceptionMsg(e.getMessage());
		
		assertTrue(this.getExceptionMsg().contains(maxCharMsg));
		
	}
	
	@Test
	public void exceedingMaxCharsAllowedRichTextField_throwsException() throws InvalidArticleException {
		

		InvalidArticleException e = assertThrows(InvalidArticleException.class, () -> {
			
			ArticleDto artrtext = new ArticleDto();
			StringBuilder notrtext = new StringBuilder();
			for (int i = 0; i < 22999; i++) {
				notrtext.append("0");
			}
			artrtext.setContent("test article content");
			artrtext.setRichtext(notrtext.toString());
			artrtext.setTitle("Test article content");
			artrtext.setValue("test value content");
			
			controller.addArticle(artrtext);
		});
		
		this.setExceptionMsg(e.getMessage());
		
		assertTrue(this.getExceptionMsg().contains(maxCharMsg));
		
	}
	
	@Test
	public void exceedingMaxCharsAllowedImgField_throwsException() throws InvalidArticleException {
		

		InvalidArticleException e = assertThrows(InvalidArticleException.class, () -> {
			
			ArticleDto artimg = new ArticleDto();
			StringBuilder imgtxt = new StringBuilder();
			for (int i = 0; i < 255; i++) {
				imgtxt.append("0");
			}
			artimg.setImg(imgtxt.toString());
			artimg.setContent("test article content");
			artimg.setRichtext("test img");
			artimg.setTitle("Test article content");
			artimg.setValue("test value content");
			
			controller.addArticle(artimg);
		});
		
		this.setExceptionMsg(e.getMessage());
		
		assertTrue(this.getExceptionMsg().contains(maxCharMsg));
		
	}
	
	@Test
	public void exceedingMaxCharsAllowedTitleField_throwsException() throws InvalidArticleException {
		

		InvalidArticleException e = assertThrows(InvalidArticleException.class, () -> {
			
			ArticleDto arttitle = new ArticleDto();
			StringBuilder titletxt = new StringBuilder();
			for (int i = 0; i < 255; i++) {
				titletxt.append("0");
			}
			arttitle.setTitle(titletxt.toString());
			arttitle.setImg("test title");
			arttitle.setContent("test article content");
			arttitle.setRichtext("test img");
			arttitle.setValue("test value content");
			
			controller.addArticle(arttitle);
		});
		
		this.setExceptionMsg(e.getMessage());
		
		assertTrue(this.getExceptionMsg().contains(maxCharMsg));
		
	}
	
	@Test
	public void exceedingMaxCharsAllowedValueField_throwsException() throws InvalidArticleException {
		

		InvalidArticleException e = assertThrows(InvalidArticleException.class, () -> {
			
			ArticleDto arttitle = new ArticleDto();
			StringBuilder titletxt = new StringBuilder();
			for (int i = 0; i < 255; i++) {
				titletxt.append("0");
			}
			arttitle.setTitle("title test");
			arttitle.setImg("test title");
			arttitle.setContent("test article content");
			arttitle.setRichtext("test img");
			arttitle.setValue(titletxt.toString());
			
			controller.addArticle(arttitle);
		});
		
		this.setExceptionMsg(e.getMessage());
		
		assertTrue(this.getExceptionMsg().contains(maxCharMsg));
		
	}
	
}

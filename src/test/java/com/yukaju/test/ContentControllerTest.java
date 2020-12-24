package com.yukaju.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.yukaju.controllers.ContentController;
import com.yukaju.dtos.ArticleDto;
import com.yukaju.exceptions.InvalidArticleException;
import com.yukaju.repositories.ArticleRepoIFace;
import com.yukaju.services.ContentService;

@SpringBootTest
@AutoConfigureMockMvc
class ContentControllerTest {
	
	@Autowired
	ContentController controller;
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	ContentService serivce;
	
	String emptyMsg = "Cannot leave empty.";
	String maxCharMsg = "Exceding the maximum characters allowed.";
	String exceptionMsg;
	StringBuilder strBld;
	
	ArticleDto artDto;

	String getExceptionMsg() {
		return exceptionMsg;
	}

	void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	
	@BeforeEach
	void setup() {
		this.artDto = new ArticleDto();
		this.strBld = new StringBuilder();
	}

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	@Test
	void shouldReturnJsonArticleList() throws Exception {
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON)).andReturn();
		
		assertEquals(200, result.getResponse().getStatus());
	}
	
	@Test
	void exceedingMaxCharsAllowedContentField_throwsException() throws InvalidArticleException {
		
		InvalidArticleException e = assertThrows(InvalidArticleException.class, () -> {
			
			for (int i = 0; i < 22999; i++) {
				strBld.append("0");
			}
			artDto.setContent(strBld.toString());
			artDto.setTitle("Test article content");
			artDto.setValue("test value content");
			
			controller.addArticle(artDto);
		});
		
		this.setExceptionMsg(e.getMessage());
		
		assertTrue(this.getExceptionMsg().contains(maxCharMsg));
		
	}
	
	@Test
	void exceedingMaxCharsAllowedRichTextField_throwsException() throws InvalidArticleException {
		

		InvalidArticleException e = assertThrows(InvalidArticleException.class, () -> {
			
			for (int i = 0; i < 22999; i++) {
				strBld.append("0");
			}
			artDto.setContent("test article content");
			artDto.setRichtext(strBld.toString());
			artDto.setTitle("Test article content");
			artDto.setValue("test value content");
			
			controller.addArticle(artDto);
		});
		
		this.setExceptionMsg(e.getMessage());
		
		assertTrue(this.getExceptionMsg().contains(maxCharMsg));
		
	}
	
	@Test
	void exceedingMaxCharsAllowedImgField_throwsException() throws InvalidArticleException {
		

		InvalidArticleException e = assertThrows(InvalidArticleException.class, () -> {
			
			for (int i = 0; i < 255; i++) {
				strBld.append("0");
			}
			artDto.setImg(strBld.toString());
			artDto.setContent("test article content");
			artDto.setRichtext("test img");
			artDto.setTitle("Test article content");
			artDto.setValue("test value content");
			
			controller.addArticle(artDto);
		});
		
		this.setExceptionMsg(e.getMessage());
		
		assertTrue(this.getExceptionMsg().contains(maxCharMsg));
		
	}
	
	@Test
	void exceedingMaxCharsAllowedTitleField_throwsException() throws InvalidArticleException {
		

		InvalidArticleException e = assertThrows(InvalidArticleException.class, () -> {
		
			for (int i = 0; i < 255; i++) {
				strBld.append("0");
			}
			artDto.setTitle(strBld.toString());
			artDto.setImg("test title");
			artDto.setContent("test article content");
			artDto.setRichtext("test img");
			artDto.setValue("test value content");
			
			controller.addArticle(artDto);
		});
		
		this.setExceptionMsg(e.getMessage());
		
		assertTrue(this.getExceptionMsg().contains(maxCharMsg));
		
	}
	
	@Test
	void exceedingMaxCharsAllowedValueField_throwsException() throws InvalidArticleException {
		

		InvalidArticleException e = assertThrows(InvalidArticleException.class, () -> {
			
			for (int i = 0; i < 255; i++) {
				strBld.append("0");
			}
			artDto.setTitle("8 mock test");
			artDto.setContent("test content");
			artDto.setImg("test title");
			artDto.setRichtext("test img");
			artDto.setValue(strBld.toString());
			
			controller.addArticle(artDto);
		});
		
		this.setExceptionMsg(e.getMessage());
		
		assertTrue(this.getExceptionMsg().contains(maxCharMsg));
		
	}
	
	@Test
	void nullArticleValue_throwsException() {
		
		InvalidArticleException e = assertThrows(InvalidArticleException.class, () -> {
			
			artDto.setTitle("test title");
			
			controller.addArticle(artDto);
			
		});
		
		this.setExceptionMsg(e.getMessage());
		
		assertTrue(this.getExceptionMsg().contains(emptyMsg));
	}
	
	@Test
	void nullArticleTitle_throwsException() {
		
		InvalidArticleException e = assertThrows(InvalidArticleException.class, () -> {
			
			artDto.setValue("test title");
			
			controller.addArticle(artDto);
			
		});
		
		this.setExceptionMsg(e.getMessage());
		
		assertTrue(this.getExceptionMsg().contains(emptyMsg));
	}
	
}

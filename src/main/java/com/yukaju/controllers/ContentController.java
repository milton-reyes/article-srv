package com.yukaju.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yukaju.dtos.ArticleDto;
import com.yukaju.exceptions.InvalidArticleException;
import com.yukaju.models.Article;
import com.yukaju.services.ContentService;

@RestController
//@RequestMapping("/topics")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.PUT, RequestMethod.PATCH,
		RequestMethod.POST }, allowedHeaders = { "*" })
public class ContentController {

	@Autowired
	ContentService contentService;

	@GetMapping
	public List<Article> getArticles() {
		return contentService.getArticles();
	}

	@GetMapping("/articles/{id}")
	public ResponseEntity<Article> getArticleById(@PathVariable(value = "id") int articleId) {
		Optional<Article> article = contentService.findById(articleId);
		if (article.isPresent()) {
			return ResponseEntity.ok().body(article.get());
		} else {
			return ResponseEntity.notFound().build();
			// throw new HttpStatusException(404, "Article with id of " + articleId + " was
			// not found.");
		}
	}

	// @PostMapping("/addarticle")
	@PostMapping
	public Article addArticle(@Valid @RequestBody ArticleDto article) throws InvalidArticleException {
		Article newArticle = new Article();
		if (article.getTitle() == null || article.getValue() == null) {
			throw new InvalidArticleException("Cannot leave empty.");
		}
		if (article.getContent().length() > 19999 || article.getRichtext().length() > 19999
				|| article.getImg().length() > 254 || article.getTitle().length() > 254
				|| article.getValue().length() > 254) {
			throw new InvalidArticleException("Exceding the maximum characters allowed.");
		}

		newArticle.setTitle(article.getTitle());
		newArticle.setContent(article.getContent());
		newArticle.setRichtext(article.getRichtext());
		newArticle.setImg(article.getImg());
		newArticle.setArticleDate(article.getArticleDate());
		newArticle.setValue(article.getValue());

		return contentService.addArticle(newArticle);
	}

}

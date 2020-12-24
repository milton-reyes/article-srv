package com.yukaju.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yukaju.models.Article;
import com.yukaju.repositories.ArticleRepoIFace;

@Service
public class ContentService {
	
	@Autowired
	ArticleRepoIFace articleRepo;
	
	public List<Article> getArticles() {
		return articleRepo.findAll();
	}
	
	public Optional<Article> findById(int id) {
		return articleRepo.findById(id);
	}
	
	public Article addArticle(Article article) {
		article.setArticleDate(LocalDateTime.now());
		return articleRepo.save(article);
	}

}

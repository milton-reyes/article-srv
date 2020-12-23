package com.yukaju.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yukaju.models.Article;

public interface ArticleRepoIFace extends JpaRepository<Article, Integer> {

}

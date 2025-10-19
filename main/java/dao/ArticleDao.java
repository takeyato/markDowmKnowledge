package com.example.search.dao;

import com.example.search.entity.Article;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import java.util.List;

@Dao
public interface ArticleDao {

    @Select
    List<Article> selectAll();

    @Select
    List<Article> selectByIds(List<Integer> ids);
}
package com.example.search.service;

import com.example.search.dao.ArticleDao;
import com.example.search.entity.Article;
import com.example.search.lucene.LuceneSearcher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 検索機能を提供するサービスクラス
 */
@Service
public class SearchService {

    private final ArticleDao articleDao;
    private final LuceneSearcher luceneSearcher;

    public SearchService(ArticleDao articleDao, LuceneSearcher luceneSearcher) {
        this.articleDao = articleDao;
        this.luceneSearcher = luceneSearcher;
    }

    /**
     * キーワードで検索し、該当する記事を取得する
     * @param keyword 検索キーワード
     * @return 該当記事のリスト
     */
    public List<Article> search(String keyword) throws Exception {
        // Luceneで検索してIDリストを取得
        List<Integer> ids = luceneSearcher.search(keyword, 10);

        // DomaでOracleから記事詳細を取得
        return articleDao.selectByIds(ids);
    }
}
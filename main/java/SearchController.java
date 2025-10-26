package com.example.search.controller;

import com.example.search.entity.Article;
import com.example.search.service.SearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 検索APIのエンドポイント
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * GET /search?q=キーワード
     * @param q 検索キーワード
     * @return 該当記事のリスト（JSON）
     */
    @GetMapping
    public List<Article> search(@RequestParam String q) throws Exception {
        return searchService.search(q);
    }
}
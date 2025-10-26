package com.example.search.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;

import java.util.ArrayList;
import java.util.List;

/**
 * Luceneを使ってMarkdown本文を全文検索するクラス
 */
public class LuceneSearcher {

    private final Directory index;
    private final Analyzer analyzer;

    public LuceneSearcher(Directory index, Analyzer analyzer) {
        this.index = index;
        this.analyzer = analyzer;
    }

    /**
     * キーワードで検索し、該当する記事IDのリストを返す
     * @param queryStr 検索キーワード
     * @param limit 最大取得件数
     */
    public List<Integer> search(String queryStr, int limit) throws Exception {
        // クエリを解析（contentフィールドに対して）
        Query query = new QueryParser("content", analyzer).parse(queryStr);

        // インデックスを読み込み
        try (IndexReader reader = DirectoryReader.open(index)) {
            IndexSearcher searcher = new IndexSearcher(reader);

            // 検索を実行
            TopDocs results = searcher.search(query, limit);

            // 該当するIDを抽出
            List<Integer> ids = new ArrayList<>();
            for (ScoreDoc scoreDoc : results.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                ids.add(Integer.parseInt(doc.get("id")));
            }
            return ids;
        }
    }
}
package com.example.search.lucene;

import com.example.search.entity.Article;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.List;

/**
 * Luceneインデックスを構築するユーティリティクラス
 */
public class LuceneIndexer {

    /**
     * 記事一覧をLuceneインデックスに登録する
     * @param articles 記事リスト
     * @param index LuceneのDirectory（RAMまたはFS）
     * @param analyzer 使用するAnalyzer（例：StandardAnalyzer）
     */
    public static void buildIndex(List<Article> articles, Directory index, Analyzer analyzer) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        try (IndexWriter writer = new IndexWriter(index, config)) {
            for (Article article : articles) {
                Document doc = new Document();

                // IDはStringField（検索対象ではないが保存する）
                doc.add(new StringField("id", article.id.toString(), Field.Store.YES));

                // タイトルと本文はTextField（全文検索対象）
                doc.add(new TextField("title", article.title, Field.Store.YES));
                doc.add(new TextField("content", article.markdown, Field.Store.YES));

                writer.addDocument(doc);
            }
        }
    }
}
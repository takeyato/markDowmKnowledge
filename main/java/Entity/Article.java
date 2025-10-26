package com.example.search.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Column;

import java.time.LocalDateTime;

@Entity
public class Article {
    @Id
    public Integer id;

    public String title;
    public String category;
    public String author;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    public String markdown;
}
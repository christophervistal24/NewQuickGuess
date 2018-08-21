package com.example.forest.quickguessv2.DBModule;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "question_category")
public class QuestionCategory
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "category")
    @NonNull
    private String category;

    @ColumnInfo(name = "category_description")
    @NonNull
    private String category_description;

    public QuestionCategory(int id, @NonNull String category, @NonNull String category_description) {
       this.setId(id);
       this.setCategory(category);
       this.setCategory_description(category_description);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory_description() {
        return category_description;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

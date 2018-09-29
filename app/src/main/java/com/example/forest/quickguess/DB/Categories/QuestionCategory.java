package com.example.forest.quickguess.DB.Categories;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "question_category" , indices={@Index(value="category", unique=true)})
public class QuestionCategory
{
    @PrimaryKey(autoGenerate = true)
    private int id;


    @NonNull
    @ColumnInfo(name = "category")
    private String category;

    @NonNull
    @ColumnInfo(name = "category_description")
    private String category_description;


    public void setCategory(String category) {
        this.category = category;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
    }

    public String getCategory() {
        return category;
    }

    public String getCategory_description() {
        return category_description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

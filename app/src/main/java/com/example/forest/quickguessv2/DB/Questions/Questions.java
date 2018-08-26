package com.example.forest.quickguessv2.DB.Questions;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.forest.quickguessv2.DB.Categories.QuestionCategory;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName ="questions", foreignKeys = @ForeignKey(entity= QuestionCategory.class,
                                parentColumns = "id",
                                childColumns="category_id",
                                onDelete = CASCADE),   indices = {@Index(value = "question", unique = true)})
public class Questions
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "question")
    @NonNull
    private String question;

    @ColumnInfo(name = "choice_a")
    @NonNull
    private String choice_a;

    @ColumnInfo(name = "choice_b")
    @NonNull
    private String choice_b;

    @ColumnInfo(name = "choice_c")
    @NonNull
    private String choice_c;

    @ColumnInfo(name = "choice_d")
    @NonNull
    private String choice_d;

    @ColumnInfo(name = "correct_answer")
    @NonNull
    private String correct_answer;

    @ColumnInfo(name = "fun_facts")
    @NonNull
    private String fun_facts;

    @ColumnInfo(name = "fun_facts_image")
    @NonNull
    private String fun_facts_image;

    @ColumnInfo(name = "category_id")
    @NonNull
    private int category_id;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice_a() {
        return choice_a;
    }

    public void setChoice_a(String choice_a) {
        this.choice_a = choice_a;
    }

    public String getChoice_b() {
        return choice_b;
    }

    public void setChoice_b(String choice_b) {
        this.choice_b = choice_b;
    }

    public String getChoice_c() {
        return choice_c;
    }

    public void setChoice_c(String choice_c) {
        this.choice_c = choice_c;
    }

    public String getChoice_d() {
        return choice_d;
    }

    public void setChoice_d(String choice_d) {
        this.choice_d = choice_d;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getFun_facts() {
        return fun_facts;
    }

    public void setFun_facts(String fun_facts) {
        this.fun_facts = fun_facts;
    }

    public String getFun_facts_image() {
        return fun_facts_image;
    }

    public void setFun_facts_image(String fun_facts_image) {
        this.fun_facts_image = fun_facts_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(@NonNull int category_id) {
        this.category_id = category_id;
    }
}

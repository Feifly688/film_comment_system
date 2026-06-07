/**
 * Film
 *
 * @author Feiqi
 * @date 2025/1/9  9:37
 */
package com.example.entity;

/**
 * 电影信息
 */
public class Film {
    /**
     * 电影ID
     */
    private Integer id;
    /**
     * 电影名
     */
    private String name;
    /**
     * 电影封面
     */
    private String cover;
    /**
     * 电影描述
     */
    private String description;
    /**
     * 发布年份
     */
    private String year;
    /**
     * 导演
     */
    private String director;
    /**
     * 演员
     */
    private String actors;
    /**
     * 电影类别ID
     */
    private Integer categoryId;
    /**
     * 电影类别
     */
    private String categoryName;
    /**
     * 出自国家
     */
    private String country;
    /**
     * 主要语言
     */
    private String language;
    /**
     * 发布日期
     */
    private String date;
    /**
     * 电影时长
     */
    private String duration;
    /**
     * IMDB
     */
    private String imdb;
    /**
     * 当前电影评论数量
     */
    private Integer commentNum;
    /**
     * 当前电影评分
     */
    private Double score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}

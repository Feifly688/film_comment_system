/**
 * Comment
 *
 * @author Feiqi
 * @date 2025/1/9  13:22
 */
package com.example.entity;

/**
 * 电影评论
 */
public class Comment {
    /**
     * ID
     */
    private Integer id;
    /**
     * 电影ID
     */
    private Integer filmId;
    /**
     * 电影名
     */
    private String filmName;
    /**
     * 电影评论
     */
    private String comment;
    /**
     * 电影评分
     */
    private Double score;
    /**
     * 评论用户ID
     */
    private Integer userId;
    /**
     * 评论用户名
     */
    private String userName;
    /**
     * 评论时间
     */
    private String time;
    /**
     * 评论类型
     */
    private String type;
    /**
     * 用户头像
     */
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

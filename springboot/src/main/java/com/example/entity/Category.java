/**
 * Category
 *
 * @author Feiqi
 * @date 2025/1/8  18:40
 */
package com.example.entity;

/**
 * 电影类别
 */
public class Category {
    /**
     * ID
     */
    private Integer id;
    /**
     * 电影类别
     */
    private String name;

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
}

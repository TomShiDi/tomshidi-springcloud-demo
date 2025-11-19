package com.tomshidi.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * 路径信息实体
 * @author tomshidi
 * @since 2024-4-16 09:44:10
 */
@Entity(name = "tree_path_table")
public class TreePathTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String ancestor;

    private String descendant;

    private int distance;

    /**
     * 用于区分不同业务线
     */
    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAncestor() {
        return ancestor;
    }

    public TreePathTable setAncestor(String ancestor) {
        this.ancestor = ancestor;
        return this;
    }

    public String getDescendant() {
        return descendant;
    }

    public TreePathTable setDescendant(String descendant) {
        this.descendant = descendant;
        return this;
    }

    public int getDistance() {
        return distance;
    }

    public TreePathTable setDistance(int distance) {
        this.distance = distance;
        return this;
    }

    public String getType() {
        return type;
    }

    public TreePathTable setType(String type) {
        this.type = type;
        return this;
    }
}

package com.tomshidi.mybatis.model;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author tomshidi
 * @date 2023/2/8 13:50
 */
@TableName(value = "tree_path_table")
public class TreePathTable {
    private String ancestor;

    private String descendant;

    private int distance;

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
}

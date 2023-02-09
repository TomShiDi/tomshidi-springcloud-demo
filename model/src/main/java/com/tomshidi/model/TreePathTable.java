package com.tomshidi.model;

/**
 * @author tomshidi
 * @date 2023/2/8 13:50
 */
public class TreePathTable {
    private String ancestor;

    private String descendant;

    private int distance;

    public String getAncestor() {
        return ancestor;
    }

    public void setAncestor(String ancestor) {
        this.ancestor = ancestor;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}

package com.pemng.serviceSystem.base.dao;

/**
 * 别名信息类
 * @author shaojie
 *
 */
public class AliasPack {
    private String property;
    private String alias;
    private int joinType;


    public AliasPack(String property, String alias, int joinType) {
        this.property = property;
        this.alias = alias;
        this.joinType = joinType;
    }


    public String getProperty() {
        return property;
    }

    public String getAlias() {
        return alias;
    }

    public int getJoinType() {
        return joinType;
    }
}

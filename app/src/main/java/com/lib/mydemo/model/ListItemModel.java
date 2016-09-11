package com.lib.mydemo.model;

/**
 * Created by libin on 2016/9/11.
 */
public class ListItemModel {
    String name;

    String content;

    public ListItemModel(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

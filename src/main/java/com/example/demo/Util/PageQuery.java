package com.example.demo.Util;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter @Setter
public class PageQuery extends LinkedHashMap<String, Object> {
    private int page;
    private int limit;

    public PageQuery(Map<String, Object> param){
        this.putAll(param);
        this.page=Integer.parseInt(param.get("page").toString());
        this.limit=Integer.parseInt(param.get("limit").toString());
        this.put("start",(page-1)*limit);
        this.put("page",page);
        this.put("limit",limit);
    }

}

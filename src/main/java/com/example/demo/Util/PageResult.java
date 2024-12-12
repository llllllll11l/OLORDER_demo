package com.example.demo.Util;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult implements Serializable {
    private int totalCount;
    private int pageSize;//每页的记录数
    private int totalPage;
    private int currPage;//当前页码
    private List<?> list;//列表数据
}

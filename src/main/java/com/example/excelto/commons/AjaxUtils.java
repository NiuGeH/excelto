package com.example.excelto.commons;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;

/**
 * Ajax工具类
 */
public class AjaxUtils {
    /**
     * 处理ajax结果
     *
     * @param action
     * @param <T>
     * @return
     */
    public static <T> AjaxResult<T> process(Func_T<T> action) {
        AjaxResult<T> ajaxResult = new AjaxResult<T>();
        ajaxResult.setSuccess(false);
        try {
            T result = action.invoke();
            ajaxResult.setSuccess(true);
            ajaxResult.setCode(200);
            ajaxResult.setData(result);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMessage(e.getMessage());
        }

        return ajaxResult;
    }

    /**
     * 处理列表ajax结果
     *
     * @param page
     * @param action
     * @param <T>
     * @return
     */
    public static <T> PageResult<T> process(PageRequestParam page, Sort sort, Func_T1<Pageable, Page<T>> action) {
        PageResult<T> pageResult = new PageResult<T>();
        try {
            Pageable pageable = PageRequest.of(page.getPage() - 1, page.getRows(), sort);
            Page<T> pageList = action.invoke(pageable);
            pageResult.setRecords(pageList.getTotalElements());
            pageResult.setPage(page.getPage());
            if (pageList.getContent() == null) {
                pageResult.setRows(new ArrayList<>());
            } else {
                pageResult.setRows(pageList.getContent());
            }
            if (pageResult.getRecords() % page.getRows() == 0) {
                pageResult.setTotal(pageResult.getRecords() / page.getRows());
            } else {
                pageResult.setTotal(pageResult.getRecords() / page.getRows() + 1);
            }
        } catch (Exception e) {
            pageResult.setRows(new ArrayList<>());
            pageResult.setTotal(0l);
            e.printStackTrace();
        }

        return pageResult;
    }
}

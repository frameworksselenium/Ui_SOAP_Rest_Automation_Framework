
package com.open.abddf.pojo.request2;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Pagination {

    @SerializedName("limit")
    private Long mLimit;
    @SerializedName("page")
    private Long mPage;
    @SerializedName("pages")
    private Long mPages;
    @SerializedName("total")
    private Long mTotal;

    public Long getLimit() {
        return mLimit;
    }

    public void setLimit(Long limit) {
        mLimit = limit;
    }

    public Long getPage() {
        return mPage;
    }

    public void setPage(Long page) {
        mPage = page;
    }

    public Long getPages() {
        return mPages;
    }

    public void setPages(Long pages) {
        mPages = pages;
    }

    public Long getTotal() {
        return mTotal;
    }

    public void setTotal(Long total) {
        mTotal = total;
    }

}

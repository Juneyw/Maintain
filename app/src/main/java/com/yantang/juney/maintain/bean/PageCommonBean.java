package com.yantang.juney.maintain.bean;

/**
 * 分页数据
 */
public class PageCommonBean {

    /**
     * pageCount : 299
     * recordCount : 299
     * result : H4sIAAAAAAAEAOy9B2AcSZYlJi9tynt/SvVK1+B0oQiAYBMk2JBAEOzBiM3mkuwdaUcjKasqgcplVmVdZhZAzO2dvPfee++999577733ujudTif33/8/XGZkAWz2zkrayZ4hgKrIHz9+fB8/Ir73iz86e/rRo4/2Pt3Z2flo9NHzs5PTF69PXz4/fnP64qsvnpy+oi//qz/2T9zZ2b/3kL7/fU6PT89evH75lL7Hazu7D+7ev7uX7u4/2t9/dO+Amnz56vPjF8dvzr588fu/OP4Crf6Lv/iv/s//wr/nP//D/7r//E/8u3apxelPfHX28vfnfnfvP9jbOTDQ5Qt9a2f3053d/Z17e5+6r16ffdF76SdPv3128vz0ze/z8lTfpA+ffXX6/M3xi98r/PTk+NWrs+PPO21fffndF1/Sr/c/+iXf/38CAAD//2Uh5rsQAQAA
     * status : 1
     */

    private int pageCount;
    private int recordCount;
    private String result;
    private int status;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

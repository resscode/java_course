package com.mycompany.parser;

/**
 *
 * @author adovgobrod
 */
public class Replace {

    private String src;
    private String result;

    public Replace(String src, String result) {
        this.src = src;
        this.result = result;
    }

    public String getSrc() {
        return src;
    }

    public String getResult() {
        return result;
    }

}

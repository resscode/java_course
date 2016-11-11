/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parser;

/**
 *
 * @author adovgobrod
 */
public class Replace {
    private String src;
    private String result;
    public Replace (String src, String result) {
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

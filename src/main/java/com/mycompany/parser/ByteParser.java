/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parser;

import java.util.Set;

/**
 *
 * @author adovgobrod
 */
public class ByteParser {

    private Set<String> phones;
    private Set<String> emails;
    private byte[] bytes;

    public ByteParser(byte[] bytes) {
        this.bytes = bytes;
        this.addEmails();
        this.addPhones();
        this.changeBytes();
    }

    private void addEmails() {

    }

    private void addPhones() {

    }

    public byte[] getBytes() {
        return bytes;
    }

    public byte[] changeBytes() {
        String s = new String(this.bytes);
       // System.out.println(s);
        if (s.contains("some@mail.ru")) {
            this.bytes = s.replaceAll("some@mail.ru", "SOME!!!!@mail.ru").getBytes();
        }
        return this.bytes;
    }

    @Override
    public String toString() {
        return new String(this.bytes);
    }
    
}

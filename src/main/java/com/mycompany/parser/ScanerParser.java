/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parser;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

/**
 *
 * @author adovgobrod
 */
public class ScanerParser {

    private static final String REGEXP_PHONE = "(?<phone>^([+\\s]+)([\\d\\s()]+)[\\s])(?<emails>.*)";
    private static final String REGEXP_EMAIL = "([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})";
    private List<Replace> phonesReplaceList;
    private List<String> phonesList;
    private List<String> emailsList;
    private Scanner scanner;
    private byte[] bytes;

    public ScanerParser(ZipInputStream zipInputStream) {
        this.scanner = new Scanner(zipInputStream);
        this.initReplaceList();
        this.changeBytes();
    }

    private void initReplaceList() {
        this.phonesReplaceList.add(new Replace("(101)", "(401)"));
        this.phonesReplaceList.add(new Replace("(202)", "(802)"));
        this.phonesReplaceList.add(new Replace("(301)", "(321)"));
    }

    private String replace(String line) {
        for (Replace item : this.phonesReplaceList) {
            line.replaceAll(item.getSrc(), item.getResult());
        }
        return line;
    }

    private void parseEmails(String line) {
        Matcher m = Pattern.compile(REGEXP_EMAIL).matcher(line);
        while (m.find()) {
            this.emailsList.add(m.group());
        }
    }

    private void parsePhoneEmails(String line) {
        Matcher m = Pattern.compile(REGEXP_PHONE).matcher(line);
        while (m.find()) {
            this.parseEmails(m.group("emails"));
            this.phonesList.add(m.group("phone"));
        }
    }

    public byte[] getBytes() {
        return bytes;
    }

    public byte[] changeBytes() {
        String s = new String();
        while (this.scanner.hasNextLine()) {
            String line = this.scanner.nextLine();
            this.parsePhoneEmails(line);
            s += this.replace(line);
        }
        this.bytes = s.getBytes();
        return this.bytes;
    }
}

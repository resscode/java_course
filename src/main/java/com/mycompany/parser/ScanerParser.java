/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

/**
 *
 * @author adovgobrod
 */
public class ScanerParser {

    private List<Replace> phonesReplaceList;
    private List<String> phones;
    private List<String> emails;
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
        String pattern = "([+\\s]+)([0-9]+)([(\\s]+)([0-9\\s]+)([)])([0-9\\s]+)";
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile(pattern).matcher(line);
        while (m.find()) {
            System.out.print(m.group());
            allMatches.add(m.group());
        }
        this.emails = allMatches;
    }

    private void parsePhone(String line) {
        String pattern = "([+\\s]+)([0-9]+)([(\\s]+)([0-9\\s]+)([)])([0-9\\s]+)";
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile(pattern).matcher(line);
        while (m.find()) {
            System.out.print(m.group());
            allMatches.add(m.group());
        }
        this.phones = allMatches;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public byte[] changeBytes() {
        String s = new String();
        while (this.scanner.hasNextLine()) {
            String line = this.scanner.nextLine();
            this.parseEmails(line);
            this.parsePhone(line);
            s += this.replace(line);
        }
        this.bytes = s.getBytes();
        return this.bytes;
    }
}

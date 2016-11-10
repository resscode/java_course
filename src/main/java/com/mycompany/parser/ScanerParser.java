/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parser;

import java.util.Scanner;
import java.util.Set;
import java.util.zip.ZipInputStream;

/**
 *
 * @author adovgobrod
 */
public class ScanerParser {

    private Set<String> phones;
    private Set<String> emails;
    private Scanner scanner;
    private byte[] bytes;

    public ScanerParser(ZipInputStream zipInputStream) {
        this.scanner = new Scanner(zipInputStream);
        this.changeBytes();
        this.addEmails();
        this.addPhones();
    }

    private void addEmails() {

    }

    private void addPhones() {

    }

    public byte[] getBytes() {
        return bytes;
    }

    public byte[] changeBytes() {
        String s = new String();
        while (this.scanner.hasNextLine()) {
            String line = this.scanner.nextLine();
            s += line.replaceAll("some@mail.ru", "SOME!!!!@mail.ru") + System.lineSeparator();
        }
        this.bytes=s.getBytes();
        return this.bytes;
    }
}

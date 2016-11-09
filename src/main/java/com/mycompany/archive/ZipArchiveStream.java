/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.archive;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author adovgobrod
 */
public class ZipArchiveStream {

    private ZipOutputStream zipOutputStream;

    public ZipArchiveStream() {
        // Empty stram to fill it on the go
        this.zipOutputStream = new ZipOutputStream(new ByteArrayOutputStream());
    }
    public ZipArchiveStream(FileOutputStream fileOutputStream) {
        this.zipOutputStream = new ZipOutputStream(new BufferedOutputStream(fileOutputStream));
    }
    public ZipArchiveStream(String filePath) throws FileNotFoundException {
        this.zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
    }
    public void putNextEntry(ZipEntry zipEntry) throws IOException {
        this.zipOutputStream.putNextEntry(zipEntry);
    }
    public void close() throws IOException{
        zipOutputStream.close();
    }

}

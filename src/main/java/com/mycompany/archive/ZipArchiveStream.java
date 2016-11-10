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
    private ByteArrayOutputStream byteArrayOutputStream;

    public ZipArchiveStream() {
        // Empty stram to fill it on the go
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.zipOutputStream = new ZipOutputStream(this.byteArrayOutputStream);
    }
    // Not Return it here
    public byte[] getByteArrayOutputStreamBytes() {
        return byteArrayOutputStream.toByteArray();
    }
    
    public ZipArchiveStream(FileOutputStream fileOutputStream) {
        this.zipOutputStream = new ZipOutputStream(new BufferedOutputStream(fileOutputStream));
    }

    public ZipArchiveStream(String filePath) throws FileNotFoundException {
        this.zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
    }

    public void putNextEntry(ZipEntry zipEntry) throws IOException {
        this.zipOutputStream.putNextEntry(zipEntry);
        //this.zipOutputStream.closeEntry();
    }

    public void closeEntry() throws IOException {
        this.zipOutputStream.closeEntry();
    }

    public void flush() throws IOException {
        this.zipOutputStream.flush();
    }

    public void close() throws IOException {
        zipOutputStream.close();
    }
    public void write(byte[] b, int off, int len) throws IOException {
        zipOutputStream.write(b, off, len);
    }
    public void write(byte[] b) throws IOException {
        zipOutputStream.write(b);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.archive;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.mycompany.stream.ZippedFileInputStream;

/**
 *
 * @author adovgobrod
 */
public class ZipArchiveStream implements ArchiveStream {

    private ZippedFileInputStream zippedFileInputStream;
    private ZipFile zipFile;

    public ZipArchiveStream(ZipArchiveStream zipArchiveStream) {
        this.zippedFileInputStream = new ZippedFileInputStream(zipArchiveStream.getZippedFileInputStream());
    }

    public ZipArchiveStream(ZipFile zipFile) throws FileNotFoundException {
        this.zipFile = zipFile;
//        this.zipInputStream = new ZipInputStream();
        this.zippedFileInputStream = new ZippedFileInputStream(new FileInputStream(zipFile.getName()));
    }

    public ZippedFileInputStream getZippedFileInputStream() {
        return this.zippedFileInputStream;
    }

    public boolean isZipFile() throws IOException {
        DataInputStream in = new DataInputStream(this.zippedFileInputStream);
        int test = in.readInt();
        in.close();
        return test == 0x504b0304;
    }

    public ZipFile getZipFile() {
        return this.zipFile;
    }

    public ZipEntry getNextEntry() throws IOException {
        return this.zippedFileInputStream.getNextEntry();
    }

    public int read(byte[] bytes) throws IOException {
        return this.zippedFileInputStream.read(bytes);
    }

    public void close() throws IOException {
        this.zippedFileInputStream.superClose();
    }

}

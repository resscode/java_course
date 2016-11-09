/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.archive;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
//import com.mycompany.stream.ZippedFileInputStream;
import java.util.zip.ZipInputStream;

/**
 *
 * @author adovgobrod
 */
public class ZipArchiveStream implements ArchiveStream {

//    private ZippedFileInputStream zippedFileInputStream;
    private ZipFile zipFile;
    private ZipInputStream zipInputStream;

    public ZipArchiveStream(ZipArchiveStream zipArchiveStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = zipArchiveStream.getZipInputStream().read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        this.zipInputStream = new ZipInputStream(new ByteArrayInputStream(baos.toByteArray()));
    }

    public ZipArchiveStream(ZipFile zipFile) throws FileNotFoundException {
        this.zipFile = zipFile;
        this.zipInputStream = new ZipInputStream(new FileInputStream(zipFile.getName()));
    }

    public ZipInputStream getZipInputStream() {
        return this.zipInputStream;
    }

    public boolean isZipFile(ZipEntry entry) throws IOException {
        return entry.getName().toLowerCase().endsWith(".zip");
    }

    public ZipFile getZipFile() {
        return this.zipFile;
    }

    public ZipEntry getNextEntry() throws IOException {
        return this.zipInputStream.getNextEntry();
    }

    public int read(byte[] bytes) throws IOException {
        return this.zipInputStream.read(bytes);
    }

    public void close() throws IOException {
        this.zipInputStream.close();
    }

}

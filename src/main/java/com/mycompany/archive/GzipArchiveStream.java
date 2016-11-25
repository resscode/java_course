package com.mycompany.archive;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author adovgobrod
 */
public class GzipArchiveStream {

    private GZIPOutputStream gzipOutputStream;
    private ByteArrayOutputStream byteArrayOutputStream;

    public GzipArchiveStream() throws IOException {
        // Empty stram to fill it on the go
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.gzipOutputStream = new GZIPOutputStream(this.byteArrayOutputStream);
    }

    public GzipArchiveStream(FileOutputStream fileOutputStream) throws IOException {
        this.gzipOutputStream = new GZIPOutputStream(new BufferedOutputStream(fileOutputStream));
    }

    public GzipArchiveStream(String filePath) throws FileNotFoundException, IOException {
        this.gzipOutputStream = new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
    }

    public byte[] getByteArrayOutputStreamBytes() {
        return byteArrayOutputStream.toByteArray();
    }

    public void flush() throws IOException {
        this.gzipOutputStream.flush();
    }

    public void close() throws IOException {
        this.gzipOutputStream.close();
    }

    public void write(byte[] b, int off, int len) throws IOException {
        this.gzipOutputStream.write(b, off, len);
    }

    public void write(byte[] b) throws IOException {
        this.gzipOutputStream.write(b);
    }

}

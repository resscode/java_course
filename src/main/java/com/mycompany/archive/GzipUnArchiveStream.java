package com.mycompany.archive;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author adovgobrod
 */
public class GzipUnArchiveStream {

    private GZIPInputStream gzipInputStream;
    private String filename;

    public GzipUnArchiveStream(InputStream gzipInputStream) throws IOException {
        this.gzipInputStream = new GZIPInputStream(gzipInputStream);
    }

    public GzipUnArchiveStream(GzipUnArchiveStream gzipUnArchiveStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = gzipUnArchiveStream.getGzipInputStream().read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        this.gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(baos.toByteArray()));
    }

    public GZIPInputStream getGzipInputStream() {
        return this.gzipInputStream;
    }

    public int read(byte[] bytes) throws IOException {
        return this.gzipInputStream.read(bytes);
    }

    public void close() throws IOException {
        this.gzipInputStream.close();
    }
    
    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}

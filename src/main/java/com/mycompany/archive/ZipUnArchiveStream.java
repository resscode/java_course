package com.mycompany.archive;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 *
 * @author adovgobrod
 */
public class ZipUnArchiveStream implements ArchiveStream {

    private ZipFile zipFile;
    private ZipInputStream zipInputStream;

    public ZipUnArchiveStream(ZipUnArchiveStream zipUnArchiveStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = zipUnArchiveStream.getZipInputStream().read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        this.zipInputStream = new ZipInputStream(new ByteArrayInputStream(baos.toByteArray()));
    }

    public ZipUnArchiveStream(ZipFile zipFile) throws FileNotFoundException {
        this.zipFile = zipFile;
        this.zipInputStream = new ZipInputStream(new FileInputStream(zipFile.getName()));
    }

    public ZipInputStream getZipInputStream() {
        return this.zipInputStream;
    }

    // END TODO
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

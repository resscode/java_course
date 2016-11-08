/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Archive;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import stream.ZippedFileInputStream;

/**
 *
 * @author adovgobrod
 */
public class ZipArchiveStream implements ArchiveStream {

    private ZipInputStream zipInputStream;
    private ZippedFileInputStream zippedFileInputStream;
    private ZipFile zipFile;

    public ZipArchiveStream(ZipInputStream zipInputStream) {
        this.zipInputStream = zipInputStream;
    }

    public ZipArchiveStream(ZippedFileInputStream zippedFileInputStream) {
        this.zipInputStream = zippedFileInputStream.getZipInputStream();
    }

    public ZipArchiveStream(ZipFile zipFile) throws FileNotFoundException {
        this.zipFile = zipFile;
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile.getName()));
        this.zipInputStream = zipInputStream;
    }

    public ZipInputStream getZipInputStream() {
        return zipInputStream;
    }

    public ZipFile getZipFile() {
        return zipFile;
    }
    
}

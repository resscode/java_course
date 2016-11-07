/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Archive;

import java.io.FileInputStream;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 *
 * @author adovgobrod
 */
public class ZipArchiveStream implements ArchiveStream {

    private ZipInputStreamStream zipInputStream;
    private ZipFile zipFile;

    public ZipArchiveStream(ZipInputStreamStream zipInputStream) {
        this.zipInputStream = zipInputStream;
    }

    public ZipArchiveStream(ZipFile zipFile) {
        this.zipFile = zipFile;
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile.getName()));
        this.zipInputStream = zipInputStream;
    }

    public ZipInputStreamStream getZipInputStream() {
        return zipInputStream;
    }

    public ZipFile getZipFile() {
        return zipFile;
    }
    
}

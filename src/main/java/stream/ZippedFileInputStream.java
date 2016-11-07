/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stream;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 *
 * @author adovgobrod
 */
public class ZippedFileInputStream extends InputStream  {
    private ZipInputStream is;

    public ZippedFileInputStream(ZipInputStream is){
        this.is = is;
    }

    @Override
    public int read() throws IOException {
        return is.read();
    }

    @Override
    public void close() throws IOException {
        is.closeEntry();
    }
}

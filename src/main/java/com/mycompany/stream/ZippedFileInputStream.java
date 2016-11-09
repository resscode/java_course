/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.stream;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author adovgobrod
 */
public class ZippedFileInputStream extends ZipInputStream {

    public ZippedFileInputStream(ZipInputStream in) {
        super(in);
    }
    public ZippedFileInputStream(InputStream in) {
        super(in);
    }

    @Override
    public void close() throws IOException {
    }

    public void superClose() throws IOException {
        super.close();
    }
}

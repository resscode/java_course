/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.archive;

import java.io.IOException;
import java.util.zip.ZipEntry;


/**
 *
 * @author adovgobrod
 */
public interface ArchiveStream {
     public int read(byte[] bytes) throws IOException;
     public void close() throws IOException;
}

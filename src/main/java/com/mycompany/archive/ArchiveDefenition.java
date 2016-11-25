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
public class ArchiveDefenition {
    // TODO move to Dfenition class
    public static boolean isZipFile(ZipEntry entry) throws IOException {
        return entry.getName().toLowerCase().endsWith(".zip");
    }

    public static boolean isGZipFile(ZipEntry entry) throws IOException {
        return entry.getName().toLowerCase().endsWith(".gz");
    }
}

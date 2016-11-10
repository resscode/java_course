/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.archive;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

/**
 *
 * @author adovgobrod
 */
public class ZipUnzipper implements Unzipper {

    private final ZipUnArchiveStream zipUnArchiveStream;
    private final ZipArchiveStream zipArchiveStream;

    public ZipUnzipper(ZipUnArchiveStream zipUnArchiveStream, ZipArchiveStream zipArchiveStream) {
        this.zipUnArchiveStream = zipUnArchiveStream;
        this.zipArchiveStream = zipArchiveStream;
    }
    public void closeZip() throws IOException{
        this.zipArchiveStream.close();
    }
    public boolean unzip(String destinationFolder ) {
        File directory = new File(destinationFolder);

        // if the output directory doesn't exist, create it
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // buffer for read and write data to file
        byte[] buffer = new byte[2048];

        try {
            ZipUnArchiveStream zipInputStream = this.zipUnArchiveStream;
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                try{
                String entryName = entry.getName();
                File file = new File(destinationFolder + File.separator + entryName);
                System.out.println("Unzip file " + entryName + " to " + file.getAbsolutePath());
                // create the directories of the zip directory
                if (entry.isDirectory()) {
                    this.zipArchiveStream.putNextEntry(entry);
                    this.zipArchiveStream.closeEntry();
                    File newDir = new File(file.getAbsolutePath());
                    if (!newDir.exists()) {
                        boolean success = newDir.mkdirs();
                        if (success == false) {
                            System.out.println("Problem creating Folder");
                        }
                    }
                } else if (zipInputStream.isZipFile(entry)) {
                    String fileWoExt = stripExtension(file.getAbsolutePath());
                    System.out.println("Zip file " + entryName);
                    ZipUnArchiveStream zipUnArchiveStream = new ZipUnArchiveStream(zipInputStream);
                    ZipArchiveStream zipArchiveStream = new ZipArchiveStream();
                    ZipUnzipper zipUnzipper = new ZipUnzipper(zipUnArchiveStream, zipArchiveStream);
                    zipUnzipper.unzip(fileWoExt);
                    this.zipArchiveStream.putNextEntry(new ZipEntry(entryName));
                    zipArchiveStream.close();
                    this.zipArchiveStream.write(zipArchiveStream.getByteArrayOutputStreamBytes());
                    this.zipArchiveStream.closeEntry();
                } else {
                    this.zipArchiveStream.putNextEntry(entry);
                    FileOutputStream fOutput = new FileOutputStream(file);
                    int count = 0;
                    //sourceIs = clone is;
                    while ((count = zipInputStream.read(buffer)) > 0) {
                        // write 'count' bytes to the file output stream
                        fOutput.write(buffer, 0, count);
                        this.zipArchiveStream.write(buffer, 0, count);
                    }
                    this.zipArchiveStream.closeEntry();
                    fOutput.close();
                }
                }catch(ZipException ex){
                    // ex.printStackTrace();
                }
            }
            //this.zipArchiveStream.close();
            // zipInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            this.zipArchiveStream.close();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return false;
//        }
        return true;
    }

    public static String stripFileName(String absolutePath) {
        return absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
    }

    public static String stripFilePath(String absolutePath) {
        Path path = Paths.get(absolutePath);
        return path.getFileName().toString();
    }

    static String stripExtension(String str) {
        // Handle null case specially.

        if (str == null) {
            return null;
        }

        // Get position of last '.'.
        int pos = str.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.
        if (pos == -1) {
            return str;
        }

        // Otherwise return the string, up to the dot.
        return str.substring(0, pos);
    }
}

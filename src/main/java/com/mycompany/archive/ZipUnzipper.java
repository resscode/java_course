/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.archive;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import stream.ZippedFileInputStream;

/**
 *
 * @author adovgobrod
 */
public class ZipUnzipper implements Unzipper {
    private ZipArchiveStream zipArchiveStream;
    public ZipUnzipper(ZipArchiveStream zipArchiveStream){
        this.zipArchiveStream = zipArchiveStream;
    }

    public boolean unzip(String destinationFolder) {
        File directory = new File(destinationFolder);

        // if the output directory doesn't exist, create it
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // buffer for read and write data to file
        byte[] buffer = new byte[2048];

        try {
            ZipInputStream zipInputStream = zipArchiveStream.getZipInputStream();
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                String entryName = entry.getName();
                File file = new File(destinationFolder + File.separator + entryName);
                System.out.println("Unzip file " + entryName + " to " + file.getAbsolutePath());

                // create the directories of the zip directory
                if (entry.isDirectory()) {
                    File newDir = new File(file.getAbsolutePath());
                    if (!newDir.exists()) {
                        boolean success = newDir.mkdirs();
                        if (success == false) {
                            System.out.println("Problem creating Folder");
                        }
                    }
                } else if (isZipFile(zipInputStream)) {
                    String fileWoExt = stripExtension(file.getAbsolutePath());
                    System.out.println("Zip file " + entryName);
                    ZipArchiveStream zipArchiveStream = new ZipArchiveStream(new ZippedFileInputStream(zipInputStream));
                    ZipUnzipper zipUnzipper = new ZipUnzipper(zipArchiveStream);
                    zipUnzipper.unzip(fileWoExt);
                } else {
                    FileOutputStream fOutput = new FileOutputStream(file);
                    int count = 0;
                    //sourceIs = clone is;
                    while ((count = zipInputStream.read(buffer)) > 0) {
                        // write 'count' bytes to the file output stream
                        fOutput.write(buffer, 0, count);
                    }
                    fOutput.close();
                }
            }
            zipInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     *
     * @param file
     * @return boolean
     * @throws IOException
     */
    public static boolean isZipFile(File file) throws IOException {
        if (file.isDirectory()) {
            return false;
        }
        if (!file.canRead()) {
            throw new IOException("Cannot read file " + file.getAbsolutePath());
        }
        if (file.length() < 4) {
            return false;
        }
        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        int test = in.readInt();
        in.close();
        return test == 0x504b0304;
    }

    /**
     * Determine whether a file is a ZIP File.
     */
    public static boolean isZipFile(FileInputStream fileStream) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(fileStream));
        int test = in.readInt();
        in.close();
        return test == 0x504b0304;
    }

    public static boolean isZipFile(InputStream inputStream) throws IOException {
        DataInputStream in = new DataInputStream(inputStream);
        int test = in.readInt();
        in.close();
        return test == 0x504b0304;
    }

    public static boolean isZipFile(ZipInputStream zipInputStream) throws IOException {
        DataInputStream in = new DataInputStream(zipInputStream);
        int test = in.readInt();
        in.close();
        return test == 0x504b0304;
    }

    /**
     * Determine whether a file is a ZIP File.
     */
    public static boolean isZipFile(BufferedInputStream inputStream) throws IOException {
        DataInputStream in = new DataInputStream(inputStream);
        int test = in.readInt();
        in.close();
        return test == 0x504b0304;
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

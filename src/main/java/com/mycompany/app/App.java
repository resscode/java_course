package com.mycompany.app;

import Archive.ZipArchiveStream;
import Archive.ZipUnzipper;
import org.apache.commons.io.input.CloseShieldInputStream;
import java.io.File;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipFile;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.nio.file.Path;
import java.nio.file.Paths;
import stream.ZippedFileInputStream;

/**
 * Hello world!
 *
 */
public class App {

    private static final String INPUT_ZIP_FILE = "D:\\inputs.zip";
    private static final String OUTPUT_FOLDER = "D:\\outputzip";

    public static void main(String[] args) {
        String zipFile = INPUT_ZIP_FILE;
        String destinationFolder = OUTPUT_FOLDER;
        try {
            // take the arguments from the command line
            if (args.length == 2) {
                
                zipFile = args[0];
                destinationFolder = args[1];

            } else if (zipFile != null && destinationFolder != null) {
                ZipArchiveStream zipArchiveStream = new ZipArchiveStream(new ZipFile(zipFile));
                ZipUnzipper zipUnzipper = new ZipUnzipper(zipArchiveStream);
                zipUnzipper.unzip(destinationFolder);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

package com.mycompany.app;

import com.mycompany.archive.ZipArchiveStream;
import com.mycompany.archive.ZipUnArchiveStream;
import com.mycompany.archive.ZipUnzipper;
import java.util.zip.ZipFile;

/**
 * Hello world!
 *
 */
public class App {

    private static final String INPUT_ZIP_FILE = "D:\\inputs.zip";
    private static final String OUTPUT_ZIP_FILE = "D:\\output.zip";
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
                ZipUnArchiveStream zipUnArchiveStream = new ZipUnArchiveStream(new ZipFile(zipFile));
                ZipArchiveStream zipArchiveStream = new ZipArchiveStream(OUTPUT_ZIP_FILE);
                ZipUnzipper zipUnzipper = new ZipUnzipper(zipUnArchiveStream, zipArchiveStream);
                zipUnzipper.unzip(destinationFolder);
                zipUnzipper.closeZip();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

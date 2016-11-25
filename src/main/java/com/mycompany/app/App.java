package com.mycompany.app;

import com.mycompany.archive.ZipArchiveStream;
import com.mycompany.archive.ZipUnArchiveStream;
import com.mycompany.archive.ZipUnzipper;
import com.mycompany.parser.ScanerParser;
import java.util.zip.ZipFile;

/**
 * Hello world!
 *
 */
public class App {

    private static final String INPUT_ZIP_FILE = "D:\\inputs.zip";
    private static final String OUTPUT_ZIP_FILE = "D:\\output.zip";
    private static final String OUTPUT_FOLDER = "D:\\outputzip";
    private static final String PHONES_FILE = "phones.txt";
    private static final String EMAILS_FILE = "emails.txt";

    public static void main(String[] args) {
        String zipFile = INPUT_ZIP_FILE;
        String destinationFolder = OUTPUT_FOLDER;
        try {
            // take the arguments from the command line
            if (args.length == 2) {
                zipFile = args[0];
                destinationFolder = args[1];
            }
            ZipUnArchiveStream zipUnArchiveStream = new ZipUnArchiveStream(new ZipFile(zipFile));
            ZipArchiveStream zipArchiveStream = new ZipArchiveStream(OUTPUT_ZIP_FILE);
            ScanerParser scanerParser = new ScanerParser();
            ZipUnzipper zipUnzipper = new ZipUnzipper(zipUnArchiveStream, zipArchiveStream, scanerParser);
            zipUnzipper.unzip(destinationFolder);
            zipUnzipper.addListFile(scanerParser.getEmailsList(), EMAILS_FILE, OUTPUT_FOLDER);
            zipUnzipper.addListFile(scanerParser.getPhonesList(), PHONES_FILE, OUTPUT_FOLDER);
            zipUnzipper.closeZip();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

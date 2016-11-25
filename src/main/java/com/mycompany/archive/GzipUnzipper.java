package com.mycompany.archive;

import com.mycompany.parser.ScanerParser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

/**
 *
 * @author adovgobrod
 */
public class GzipUnzipper implements Unzipper {

    private GzipUnArchiveStream gzipUnArchiveStream;
    private GzipArchiveStream gzipArchiveStream;
    private ZipArchiveStream zipArchiveStream;
    private ScanerParser scanerParser;

    public GzipUnzipper(GzipUnArchiveStream gzipUnArchiveStream, GzipArchiveStream gzipArchiveStream, ScanerParser scanerParser) {
        this.gzipUnArchiveStream = gzipUnArchiveStream;
        this.gzipArchiveStream = gzipArchiveStream;
        this.scanerParser = scanerParser;
    }
    public GzipUnzipper(GzipUnArchiveStream gzipUnArchiveStream, GzipArchiveStream gzipArchiveStream, ZipArchiveStream zipArchiveStream, ScanerParser scanerParser) {
        this.zipArchiveStream = zipArchiveStream;
        this.gzipUnArchiveStream = gzipUnArchiveStream;
        this.gzipArchiveStream = gzipArchiveStream;
        this.scanerParser = scanerParser;
    }

    public void closeZip() throws IOException {
        this.gzipArchiveStream.close();
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
            GzipUnArchiveStream gzipInputStream = this.gzipUnArchiveStream;
         
                try {
                    String entryName = this.gzipUnArchiveStream.getFilename();
                    File file = new File(destinationFolder + File.separator + entryName);
                    System.out.println("UnGzip file " + entryName + " to " + file.getAbsolutePath());
                    // create the directories of the zip directory
                        this.zipArchiveStream.putNextEntry(new ZipEntry(entryName));
                        FileOutputStream fOutput = new FileOutputStream(file);
                        int count = 0;
                        //sourceIs = clone is;
                        this.scanerParser.setScanner(gzipInputStream.getGzipInputStream());
                        byte[] bytesForWrite = scanerParser.changeReturnBytes();
                        fOutput.write(bytesForWrite);
                        this.zipArchiveStream.write(bytesForWrite);
                        this.zipArchiveStream.closeEntry();
                        fOutput.close();

                } catch (ZipException ex) {
                    ex.printStackTrace();
                }
            //this.zipArchiveStream.close();
            // zipInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}

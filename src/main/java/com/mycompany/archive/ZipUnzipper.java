package com.mycompany.archive;

import com.mycompany.parser.ScanerParser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

/**
 *
 * @author adovgobrod
 */
public class ZipUnzipper implements Unzipper {

    private final ZipUnArchiveStream zipUnArchiveStream;
    private final ZipArchiveStream zipArchiveStream;
    private final ScanerParser scanerParser;

    public ZipUnzipper(ZipUnArchiveStream zipUnArchiveStream, ZipArchiveStream zipArchiveStream, ScanerParser scanerParser) {
        this.zipUnArchiveStream = zipUnArchiveStream;
        this.zipArchiveStream = zipArchiveStream;
        this.scanerParser = scanerParser;
    }

    public void closeZip() throws IOException {
        this.zipArchiveStream.close();
    }

    public void addListFile(Set set, String fileName, String folder) throws IOException {
        Function<Set<String>, String> compileString = (Set<String> list) -> {
            String fileContent = "";
            fileContent = list.stream().map((row) -> row + System.getProperty("line.separator")).reduce(fileContent, String::concat);
            return fileContent;
        };
        byte[] output = compileString.apply(set).getBytes();
        File file = new File(folder + File.separator + fileName);
        this.zipArchiveStream.putNextEntry(new ZipEntry(fileName));
        FileOutputStream fOutput = new FileOutputStream(file);
        fOutput.write(output);
        this.zipArchiveStream.write(output);
        this.zipArchiveStream.closeEntry();
        fOutput.close();
    }

    @Override
    public boolean unzip(String destinationFolder) {
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
                try {
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
                    } else if (ArchiveDefenition.isZipFile(entry)) {
                        String fileWoExt = stripExtension(file.getAbsolutePath());
                        System.out.println("Zip file " + entryName);
                        ZipUnArchiveStream zipUnArchiveStream = new ZipUnArchiveStream(zipInputStream);
                        ZipArchiveStream zipArchiveStream = new ZipArchiveStream();
                        ZipUnzipper zipUnzipper = new ZipUnzipper(zipUnArchiveStream, zipArchiveStream, this.scanerParser);
                        zipUnzipper.unzip(fileWoExt);
                        this.zipArchiveStream.putNextEntry(new ZipEntry(entryName));
                        zipArchiveStream.close();
                        this.zipArchiveStream.write(zipArchiveStream.getByteArrayOutputStreamBytes());
                        this.zipArchiveStream.closeEntry();
                    } else if (ArchiveDefenition.isGZipFile(entry)) {
                        String fileWoExt = stripExtension(file.getAbsolutePath());
                        System.out.println("GZip file " + entryName);
                        GzipArchiveStream gzipArchiveStream = new GzipArchiveStream();
                        GzipUnArchiveStream gzipUnArchiveStream = new GzipUnArchiveStream(zipInputStream.getZipInputStream());
                        GzipUnzipper gzipUnzipper = new GzipUnzipper(gzipUnArchiveStream, gzipArchiveStream, this.zipArchiveStream, this.scanerParser);
                        gzipUnzipper.unzip(fileWoExt);
                        zipArchiveStream.close();
                        // uNGZIP - GZIP BACK AND PUT AS FILE ENTRY PLUS ZIP FILE ENTRY
                    } else {
                        this.zipArchiveStream.putNextEntry(new ZipEntry(entryName));
                        FileOutputStream fOutput = new FileOutputStream(file);
                        int count = 0;
                        //sourceIs = clone is;
                        this.scanerParser.setScanner(zipInputStream.getZipInputStream());
                        byte[] bytesForWrite = scanerParser.changeReturnBytes();
                        fOutput.write(bytesForWrite);
                        this.zipArchiveStream.write(bytesForWrite);
//                    while ((count = zipInputStream.read(buffer)) > 0) {
//                        // Change Bytes here
//                        ByteParser byteParser = new ByteParser(buffer);
//                        buffer = byteParser.getBytes();
//                        // write 'count' bytes to the file output stream
//                        fOutput.write(buffer, 0, count);
//                        this.zipArchiveStream.write(buffer, 0, (count < buffer.length) ? count : buffer.length);
//                    }
                        this.zipArchiveStream.closeEntry();
                        fOutput.close();
                    }
                } catch (ZipException ex) {
                    ex.printStackTrace();
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

package com.mycompany.app;

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
                unzipFunction(destinationFolder, new ZipFile(zipFile));

            } else if (zipFile != null && destinationFolder != null) {
                unzipFunction(destinationFolder, new ZipFile(zipFile));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void unzipFunction(String destinationFolder, ZipFile zipFile) {
        File directory = new File(destinationFolder);

        // if the output directory doesn't exist, create it
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // buffer for read and write data to file
        byte[] buffer = new byte[2048];

        try {
            Enumeration e = zipFile.entries();
            ZipEntry entry;
            BufferedOutputStream dest = null;
            BufferedInputStream is = null;
            InputStream sourceIs = null;
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile.getName()));
            while ((entry = zipInputStream.getNextEntry()) != null) {
                entry = (ZipEntry) e.nextElement();
                String entryName = entry.getName();
                File file = new File(destinationFolder + File.separator + entryName);
                is = new BufferedInputStream(zipFile.getInputStream(entry));
                sourceIs = zipFile.getInputStream(entry);
//                ZipInputStream zipIs = new ZipInputStream(sourceIs);
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
                } else if (isZipFile(sourceIs)) {
                //    String fileWoExt = stripExtension(file.getAbsolutePath());
                    System.out.println("Zip file " + entryName);
                    unzipFunction(destinationFolder, new ZippedFileInputStream(zipInputStream));
                } else {
                    FileOutputStream fOutput = new FileOutputStream(file);
                    int count = 0;
                    //sourceIs = clone is;
                    while ((count = is.read(buffer)) > 0) {
                        // write 'count' bytes to the file output stream
                        fOutput.write(buffer, 0, count);
                    }
//                    if (isZipFile(sourceIs)) {
//                        String fileWoExt = stripExtension(file.getAbsolutePath());
//                        System.out.println("Zip file " + entryName);
//                        unzipFunction(fileWoExt, new ZipFile(file));
//                    }                    
                }
                // close ZipEntry and take the next one
                //is.close();
            }
            zipInputStream.close();
            zipFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void unzipFunction(String destinationFolder, ZippedFileInputStream zipInputStream) {
        File directory = new File(destinationFolder);

        // if the output directory doesn't exist, create it
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // buffer for read and write data to file
        byte[] buffer = new byte[2048];

        try {
            ZipEntry entry;
            BufferedOutputStream dest = null;
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
                    unzipFunction(destinationFolder, new ZippedFileInputStream(zipInputStream.getZipInputStream()));
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
        }
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

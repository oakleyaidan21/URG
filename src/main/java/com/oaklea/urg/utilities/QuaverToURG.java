package com.oaklea.urg.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A class for converting Quaver song folders into ones usable by URG
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class QuaverToURG {
    public static void main(String[] args) {

        // read in quaver file from args
        File directory = new File(args[0]);
        int timeBetweenNotes = Integer.parseInt(args[1]);
        File[] listing = directory.listFiles();
        boolean foundBackground = false;
        try {
            Files.createDirectories(Paths.get("./result"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (listing != null) {
            for (File file : listing) {
                if (file.getName().equals("audio.mp3")) {
                    // copy audio file
                    try {
                        copyFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    int i = file.getName().lastIndexOf(".");
                    if (i > 0) {
                        String extension = file.getName().substring(i + 1);
                        switch (extension) {
                            case "qua":
                                // handle quaver file
                                handleQuaverFile(file, timeBetweenNotes);
                                break;
                            case "jpg":
                                if (!foundBackground) {
                                    // copy background to new folder
                                    try {
                                        copyFile(file, "bg.png");
                                        foundBackground = true;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                                break;
                            default:
                                break;
                        }
                    }
                }

            }
        }

    }

    /**
     * Copies a file to ./result/ + filename
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param file the file to copy
     * @throws IOException
     */
    @SuppressWarnings("resource")
    private static void copyFile(File file) throws IOException {
        File destFile = new File("./result/" + file.getName());
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(file).getChannel();
        destination = new FileOutputStream(destFile).getChannel();

        destination.transferFrom(source, 0, source.size());

        source.close();
        destination.close();
    }

    /**
     * Copies a file to a location with a new name
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param file    the file to copy
     * @param newName the new name
     * @throws IOException
     */
    @SuppressWarnings("resource")
    private static void copyFile(File file, String newName) throws IOException {
        File destFile = new File("./result/" + newName);
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(file).getChannel();
        destination = new FileOutputStream(destFile).getChannel();

        destination.transferFrom(source, 0, source.size());

        source.close();
        destination.close();
    }

    /**
     * Handles the conversion of a quaver file
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param file the quaver file to convert
     */
    private static void handleQuaverFile(File file, int timeBetweenNotes) {
        String ret = "";
        BufferedReader reader;
        String version = "";
        String previewTime = "0";
        try {
            reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
            String line = reader.readLine();
            while (line != null) {
                if (line.contains("Title:")) {
                    ret += line.split(":")[1].replaceFirst(" ", "") + "\n";
                }
                if (line.contains("Artist:")) {
                    ret += line.split(":")[1].replaceFirst(" ", "") + "\n";
                }
                if (line.contains("SongPreviewTime:")) {
                    previewTime += line.split(":")[1].replaceFirst(" ", "");
                }
                if (line.contains("DifficultyName:")) {
                    version = line.split(":")[1].replaceFirst(" ", "") + "\n";
                    ret += version;
                    ret += previewTime + "\n";
                }
                if (line.contains("HitObjects:")) {
                    while (line != null) {
                        line = reader.readLine();
                        if (line != null) {
                            if (!line.contains("StartTime")) {
                                continue;
                            }
                            String thisLine = "";
                            // first line is timing
                            int timing = Integer.parseInt(line.split(":")[1].replace(" ", ""));
                            if (timing > timeBetweenNotes) {
                                // second line is lane
                                line = reader.readLine();

                                if (line != null) {
                                    String[] laneSplit = line.split(":");
                                    thisLine += ((Integer.parseInt(laneSplit[1].replace(" ", "")) - 1) % 4) + " 5 "
                                            + (timing - timeBetweenNotes) + " " + timing + "\n";
                                    ret += thisLine;
                                }
                            }
                        }
                    }

                }
                line = reader.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // write to file
        try {
            String path = "./result/" + version.replaceAll("[^0-9a-zA-Z]+", "") + ".txt";
            File newFile = new File(path);
            newFile.createNewFile();
            FileWriter myWriter = new FileWriter(newFile);
            myWriter.write(ret);
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

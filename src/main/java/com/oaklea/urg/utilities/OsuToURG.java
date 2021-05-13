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
import java.util.Random;

/**
 * A class for converting an Osu song folder into one for URG
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class OsuToURG {

    public static void main(String[] args) {

        // read in osu file from args
        File directory = new File(args[0]);
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
                            case "osu":
                                // handle osu file
                                handleOSUFile(file);
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
     * Handles the conversion of an osu file
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param file the osu file to convert
     */
    private static void handleOSUFile(File file) {
        String ret = "";
        BufferedReader reader;
        String version = "";
        String previewTime = "0";
        try {
            reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
            String line = reader.readLine();
            while (line != null) {
                if (line.contains("Title:")) {
                    ret += line.split(":")[1] + "\n";
                }
                if (line.contains("Artist:")) {
                    ret += line.split(":")[1] + "\n";
                }
                if (line.contains("PreviewTime:")) {
                    previewTime += line.split(":")[1].replaceFirst(" ", "");
                }
                if (line.contains("Version:")) {
                    version = line.split(":")[1] + "\n";
                    ret += version;
                    ret += previewTime + "\n";
                }
                if (line.contains("HitObjects")) {
                    int prev = 0;
                    while (line != null) {
                        line = reader.readLine();
                        if (line != null) {
                            String[] objectFields = line.split(",");
                            if (objectFields.length > 2) {
                                int timing = Integer.parseInt(objectFields[2]);
                                if (timing > 1000) {
                                    prev = getRandomWithExclusion(new Random(), 0, 3, prev);
                                    String thisLine = (int) prev + " 5 " + (timing - 1000) + " " + timing + "\n";

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

    /**
     * Gets a random number in a range excluding a set of ints
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param rnd     the random object to use
     * @param start   the lower bound of the range
     * @param end     the upperbound of the range
     * @param exclude the numbers to exclude
     * @return a random number
     */
    private static int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
        int random = start + rnd.nextInt(end - start + 1 - exclude.length);
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }
}

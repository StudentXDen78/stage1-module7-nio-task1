package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class FileReader {

    public static Profile getDataFromFile(File file) {
        StringBuilder dataFromFile = new StringBuilder();

        try (RandomAccessFile myFile = new RandomAccessFile(file, "r")) {
            FileChannel inChannel = myFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (inChannel.read(buffer) > 0) {
                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++) {
                    dataFromFile.append((char)(buffer.get()));
                }
            }

            buffer.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HashMap<String, String> dataInLibraryMap= new HashMap<>();
        String[] base = dataFromFile.toString().split(System.lineSeparator());
        Profile anna = new Profile();
        for (String pair : base) {
            String[] pairs = pair.split(": ");
            dataInLibraryMap.put(pairs[0], pairs[1]);
        }

        for (Map.Entry<String, String> entry : dataInLibraryMap.entrySet()){
            if (Objects.equals(entry.getKey(), "Name")) anna.setName(entry.getValue());
            if (Objects.equals(entry.getKey(), "Age")) anna.setAge(Integer.parseInt(entry.getValue()));
            if (Objects.equals(entry.getKey(), "Email")) anna.setEmail(entry.getValue());
            if (Objects.equals(entry.getKey(), "Phone")) anna.setPhone(Long.parseLong(entry.getValue()));
        }

        return anna;
    }
    public static void main(String[] args) {
        File file = new File("C:\\Users\\Admin\\IdeaProjects\\stage1-module7-nio-task1\\src\\main\\resources\\Profile.txt");
        getDataFromFile(file);
    }
}

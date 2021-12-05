package com.example.serverpost.component;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Random;

@Component
public class FileService {
    public static String save(MultipartFile file, Path dir){
        String fileName = FileService.createRandomCode(
        20, file.getOriginalFilename()) + file.getOriginalFilename();

        Path filePath = Paths.get(dir.toString(), fileName);

        try (OutputStream os = Files.newOutputStream(filePath)) {
            os.write(file.getBytes());
        } catch(IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public static File getFile(String name, String url){
        return new File(url + name);
    }

    public static String createRandomCode(int codeLength, String id){
        char[] chars = id.toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < codeLength; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}

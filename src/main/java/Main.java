import Haffman.Haffman;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static List<Byte> readFile(String path){
        try {
            List<Byte> res = new ArrayList<>();
            for(byte b : Files.readAllBytes(Paths.get(path)))
                res.add(b);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Byte> writeFile(String path, List<Byte> outData){
        try {
            byte[] bytes = new byte[outData.size()];
            for(int i = 0; i< outData.size(); ++i)
                bytes[i] = outData.get(i);

            Files.write(Paths.get(path),bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Haffman<Byte> haffman = new Haffman<>();
        List<Byte> fileData = readFile("./1.txt");
        haffman.encode(fileData);
        writeFile("./encode",haffman.getOutputEncodeData());
        fileData = readFile("./encode");
        haffman.decode(fileData);
        writeFile("./decode",haffman.getOutputDecodeData());
    }
}

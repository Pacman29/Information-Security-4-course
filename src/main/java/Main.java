import Enigma.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) throws Exception {

        Enigma enigma = new Enigma();

        Scanner in = new Scanner(System.in);

        System.out.println("Введите код:");
        String code = in.nextLine();

        System.out.println("Укажите файл:");
        String path = in.nextLine();

        String[] keys = code.split("");

        for(String key : keys)
            enigma.setRotor(EnigmaFabric.getRotor((long) key.hashCode()));

        enigma.setReflector(EnigmaFabric.getReflector());

        ArrayList<Byte> bytes = new ArrayList<>();
        byte[] data = Files.readAllBytes(Paths.get(path));
        for(byte b: data)
             bytes.add(enigma.encrypt(b));

        FileOutputStream fos = new FileOutputStream("./enigma");
        for(Byte b : bytes)
            fos.write(b.byteValue());
        fos.close();
    }
}

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);

        System.out.println("E-шифрование, D-расшифровка");
        String type = in.nextLine();

        System.out.println("Введите код:");
        String code = in.nextLine();

        System.out.println("Укажите файл:");
        String path = in.nextLine();

        FileOutputStream fos = null;
        byte[] bytes = null;
        switch (type){
            case "E": {
                byte[] data = Files.readAllBytes(Paths.get(path));
                bytes = DES.encryptCBC(data,code.getBytes());
                fos = new FileOutputStream("./encrypt");
                break;
            }
            case "D": {
                byte[] data = Files.readAllBytes(Paths.get(path));
                bytes = DES.decryptCBC(data,code.getBytes());
                fos = new FileOutputStream("./decrypt");
                break;
            }
        }
        for(Byte b : bytes)
            fos.write(b.byteValue());
        fos.close();
    }
}

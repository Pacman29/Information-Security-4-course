package Enigma;

import java.util.ArrayList;

public class Reflector{
    ArrayList<Byte> face;
    ArrayList<Byte> back;

    public  Reflector(ArrayList<Byte> face, ArrayList<Byte> back){
        this.face = face;
        this.back = back;
    }

    public Byte getKey(Byte letter){
        return this.back.get(this.face.indexOf(letter));
    }


}

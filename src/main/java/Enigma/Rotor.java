package Enigma;

import java.util.ArrayList;

public class Rotor {
    Disk face;
    Disk back;

    public Rotor(ArrayList<Byte> _face, ArrayList<Byte> _back) {
        face = new Disk(_face);
        back = new Disk(_back);
    }

    public Byte getKeyStraight(Byte letter){
        return back.getLetter(face.getPosition(letter));
    }

    public Byte getKeyReturn(Byte letter){
        return face.getLetter(back.getPosition(letter));
    }

    public void turn(){
        back.turn();
    }

    public boolean isRotorTurned(){
        return back.isDiskTurned();
    }

    public void setCodeKey(char key){
        this.back.setKey(key);
    }

    public Byte getCodeKey(){
        return this.back.getKey();
    }
}

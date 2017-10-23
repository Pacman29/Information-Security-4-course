package Enigma;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class EnigmaFabric {

    public static Rotor getRotor(long key){
        return new Rotor(abc(),shuffle(abc(),key));
    }

    public static Reflector getReflector(){
        ArrayList<Byte> revert = new ArrayList<>(abc());
        Collections.reverse(revert);
        return new Reflector(abc(),revert);
    }

    private static ArrayList<Byte> abc(){
        ArrayList<Byte> bytes = new ArrayList<>();
        for(int i = -128; i<128; ++i){
            bytes.add((byte) i);
        }
        return bytes;
    }

    private static ArrayList<Byte> shuffle(ArrayList<Byte> in, long key){
        ArrayList<Byte> out = new ArrayList<>(in);
        Collections.shuffle(out,new Random(key));
        return out;
    }
}

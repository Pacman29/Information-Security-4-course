package Haffman;

import java.util.*;

public class Haffman<T> {
    public void setZerros(int zerros) {
        this.zerros = zerros;
    }

    public Map<T, String> getDictionary() {
        return dictionary;
    }

    public void setDictionary(Map<T, String> dictionary) {
        this.dictionary = dictionary;
    }

    private int zerros;

    public int getZerros() {
        return zerros;
    }

    public List<Byte> getOutputEncodeData() {
        return outputEncodeData;
    }

    private List<Byte> outputEncodeData;

    public List<T> getOutputDecodeData() {
        return outputDecodeData;
    }

    private List<T> outputDecodeData;

    public Haffman() {
    }


    class Node implements Comparable<Node> {
        private final int sum;
        private String code;

        void buildCode(String code){
            this.code = code;
        }

        Node(int sum) {
            this.sum = sum;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(sum, o.sum);
        }

        public String getCode() {
            return code;
        }
    }

    class InternalNode extends Node {
        private Node left;
        private Node right;

        public InternalNode(Node left, Node right) {
            super(left.sum + right.sum);
            this.left = left;
            this.right = right;
        }

        @Override
        void buildCode(String code){
            super.buildCode(code);
            left.buildCode(code + "0");
            right.buildCode(code + "1");
        }
    }

    class LeafNode extends Node {
        T data;

        LeafNode(T data, int count) {
            super(count);
            this.data = data;
        }

        @Override
        void buildCode(String code) {
            super.buildCode(code);
        }
    }

    private Map<T,String> dictionary;

    public void encode(List<T> inputData) {
        createDictionary(inputData);
        encodeDataByDictionary(inputData);
        // LOG
        StringBuilder stringBuilder = new StringBuilder();
        for(T symbol: inputData){
            stringBuilder.append(dictionary.get(symbol)).append(" ");
        }
        System.out.println(stringBuilder.toString());
    }

    private T getKeyFromValue(String value) {
        for (T o: dictionary.keySet())
            if (dictionary.get(o).equals(value))
                return o;
        return null;
    }

    public void decode(List<Byte> inputData){
        outputDecodeData = new ArrayList<T>();
        StringBuilder codeBuilder = new StringBuilder();
        for(int i = 0; i<inputData.size(); ++i){
            byte tmp = inputData.get(i);
            for(int j = 7; j>=0; --j) {
                if(i == inputData.size()-1 && j+1 == zerros)
                    break;
                int c = ((tmp >> j) & 1);
                codeBuilder.append(c);
                if(dictionary.containsValue(codeBuilder.toString())){
                    T addElem = getKeyFromValue(codeBuilder.toString());
                    outputDecodeData.add(addElem);
                    codeBuilder.delete(0,codeBuilder.length());
                }
            }

        }

    }

    private void createDictionary(List<T> inputData){
        Map<T,Integer> symbolsCountMap = new HashMap<T,Integer>();
        for(T symbol : inputData)
            symbolsCountMap.merge(symbol,1, (k,v) -> v+1);

        Map<T,Node> dictionaryNode = new HashMap<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        symbolsCountMap.forEach((k,v) ->{
                    Node tmp = new LeafNode(k,v);
                    dictionaryNode.put(k,tmp);
                    priorityQueue.add(tmp);
                }
        );

        while (priorityQueue.size() > 1){
            Node first = priorityQueue.poll();
            Node second = priorityQueue.poll();
            priorityQueue.add(new InternalNode(first,second));
        }
        Node root = priorityQueue.poll();
        root.buildCode((symbolsCountMap.size() == 1) ? "0" : "");
        dictionary = new HashMap<>();
        dictionaryNode.forEach( (k,v) -> dictionary.put(k,v.getCode()));
    }

    private Byte strBytePresentToByte(String byteAsString){
        byte tmp = 0;
        for(int i = 0; i<8; ++i)
            if(byteAsString.charAt(i) == '1'){
                tmp |= (1 << 7-i);
            }
        return tmp;
    }

    private void encodeDataByDictionary(List<T> inputData){
        outputEncodeData = new ArrayList<>();
        int bitCounter = 0;
        StringBuilder byteBilder = new StringBuilder();
        for(T symbol: inputData){
            String code = dictionary.get(symbol);
            for(char c : code.toCharArray()){
                byteBilder.append(c);
                bitCounter++;
                if(bitCounter == 8){
                    outputEncodeData.add(strBytePresentToByte(byteBilder.toString()));
                    byteBilder.delete(0,byteBilder.length());
                    bitCounter = 0;
                }
            }
        }
        if(bitCounter != 0){
            zerros = 8 - bitCounter;
            for(int i  = 0; i<zerros; ++i)
                byteBilder.append("0");
            outputEncodeData.add(strBytePresentToByte(byteBilder.toString()));
            byteBilder.delete(0,byteBilder.length());
        }
    }
}

package com.leo.test.serializable;

import java.io.*;

/**
 * User: Leo Date: 12-10-9 Time: 上午12:14
 */
public class DataObject implements Serializable {
    public int getI() {
        return i;
    }

    public String getWord() {
        return word;
    }

    private static int i = 0;
    private String word = "";

    public void setI(int i) {
        DataObject.i = i;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DataObject dataObject = new DataObject();
//        dataObject.setI(2);
        dataObject.setWord("ddd");
        ObjectOutputStream dd = new ObjectOutputStream(new FileOutputStream("F:\\aa.test"));
        dd.writeObject(dataObject);
        dd.close();
        dataObject.setI(2);
        FileInputStream fis = new FileInputStream("F:\\aa.test");
        ObjectInputStream ois = new ObjectInputStream(fis);
        DataObject dataObject1 = (DataObject) ois.readObject();
        System.out.println(dataObject1.getWord());
        System.out.println(dataObject1.getI());
    }
}

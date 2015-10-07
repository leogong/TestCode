package com.leo.test.serializable;

import java.io.*;

public class DeserializeTest {

    public static void main(String[] args) {
        String directory = "";
        String cartId = null;
        System.out.println("Enter the ID of the cart file to deserialize or q exit.");
        // Wrap the System.in InputStream with a BufferedReader to read
        // each line from the keyboard.
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            cartId = in.readLine();
            if (cartId.equals("q")) {
                System.exit(0);
            }
        } catch (IOException e) { // Catch any IO exceptions.
            System.out.println("Exception: " + e);
            System.exit(-1);
        }

        // Attempt to open the file and deserialize it into an object
        String cartFile = directory + "cart" + cartId + ".ser";
        ShoppingCart cart = null;
        // Your code goes here....
        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream(cartFile))) {
            cart = (ShoppingCart) oi.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        System.out.println(cart);
    }
}
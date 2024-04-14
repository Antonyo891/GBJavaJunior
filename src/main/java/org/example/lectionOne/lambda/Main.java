package org.example;

public class Main {
    static PlainInterface plainInterface;
    static PlainInterfaceTwo plainInterfaceTwo;
    static int valueX;
    static int valueY;
    static void print(int x,int y) {
        System.out.println(plainInterface.action(x, y));
    }
    static void printTwo(int x,int y) {
        System.out.println(plainInterfaceTwo.action(x, y));
    }
    public static void main(String[] args)
    {
        if (args.length >1) {
            try {
                valueX = Integer.parseInt(args[0]);
                valueY = Integer.parseInt(args[1]);
            } catch (NumberFormatException e){
                System.out.println(e.getMessage());
                valueX = 10;
                valueY = 15;
            }
        } else {
            valueX = 10;
            valueY = 15;
        }
        plainInterface = new PlainInterface() {
            @Override
            public String action(int x, int y) {
                return String.valueOf(x+y);
            }
        };
        print(valueX, valueY);
        plainInterface = (x,y)-> String.valueOf(x+y);
        print(valueX, valueY);
        plainInterface = (x,y)->{
            String result = String.valueOf(x+y);
            return result;
        };
        print(valueX, valueY);
        plainInterfaceTwo = Integer::sum;
        printTwo(valueX, valueY);
        plainInterfaceTwo = Integer::compare;
        printTwo(valueX, valueY);

    }
}
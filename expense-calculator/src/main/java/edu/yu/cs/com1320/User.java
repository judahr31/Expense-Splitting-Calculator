package edu.yu.cs.com1320;

public class User {
    private int id;
    private String name;
    private double netBalance;

    public User(int id, String name){
        this.id = id;
        this.name = name;
        this.netBalance = 0;
    }

    protected int getId(){
        return this.id;
    }

    protected String getName(){
        return this.name;
    }

    protected double setNetBalance(double netBalance){
        this.netBalance = netBalance;
    }

    protected double getNetBalance(){
        return this.netBalance;
    }
}

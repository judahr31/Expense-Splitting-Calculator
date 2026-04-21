package edu.yu.cs.com1320;

public class User implements Comparable<User> {
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

    protected void setName(String name) {
        this.name = name;
    }

    protected void updateNetBalance(double netBalance){
        this.netBalance += netBalance;
    }

    protected double getNetBalance(){
        return this.netBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

    @Override
    public int compareTo(User other) {
        return Double.compare(this.netBalance, other.netBalance);
    }
}

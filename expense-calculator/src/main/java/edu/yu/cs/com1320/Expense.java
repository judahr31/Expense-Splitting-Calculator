package edu.yu.cs.com1320;

import java.util.ArrayList;
import java.util.List;


public class Expense {
    private final int expenseId;
    private String description;
    private double totalAmount;
    private User payer;
    private List<User> participants;
    
    public Expense(String des, double amt, User payer, int expId) {
        this.description = des;
        this.totalAmount = amt;
        this.payer = payer;
        this.expenseId = expId;
        this.participants = new ArrayList<>();
    }

    protected int getExpenseID() {
        return this.expenseId;
    }

    protected void setDescription(String des) {
        this.description = des;
    }

    protected String getDescription() {
        return this.description;
    }

    protected void setTotalAmount(double amt) {
        this.totalAmount = amt;
    }

    protected double getTotalAmount() {
        return this.totalAmount;
    }

    protected List<User> getParticipants() {
        return new ArrayList<>(this.participants);
    }

    protected void addUser(User user) {
        this.participants.add(user);
    }
}

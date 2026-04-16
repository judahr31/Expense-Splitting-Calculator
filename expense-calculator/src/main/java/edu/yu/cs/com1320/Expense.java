package edu.yu.cs.com1320;

import java.util.ArrayList;
import java.util.List;


public class Expense {
    private int expenseId;
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

    // Getters and Setters...
    public List<User> getParticipants() {
        return new ArrayList<>(this.participants);
    }
}

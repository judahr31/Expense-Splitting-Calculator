package edu.yu.cs.com1320;

public class Expense {
    private String expenseId;
    private String description;
    private double totalAmount;
    private User payer;
    private List<User> participants;
    
    public Expense(String des, double amt, User payer) {
        
    }
    // Getters and Setters...
    public List<User> getParticipants() {
        return new ArrayList<>(this.participants);
    }
}

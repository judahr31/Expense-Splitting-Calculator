package edu.yu.cs.com1320;

import java.util.*;

public class Group {
    private int id;
    private String name;
    private Map<Integer, User> members;
    private List<Expense> expenseHistory;

    public Group(int groupId, String groupName){
        this.id = groupId;
        this.name = groupName;
        this.members = new HashMap<>();
        this.expenseHistory = new ArrayList<>();

    }

    // Represents a Directed Graph of debts:
    // Map of (Debtor ID -> Map of (Creditor ID -> Amount Owed))
    private Map<Integer, Map<Integer, Double>> debtGraph;

    

    protected void setName(String name){
        this.name = name;
    }

    protected void addMember(Integer id, User user){
        this.members.put(id, user);
    }

    protected void addExpense(Expense exp){
        this.expenseHistory.add(exp);
    }

    protected int getId(){
        return this.id;
    }

    protected String getName(){
        return this.name;
    }

    protected Map<Integer, User> getMembers(){
        return this.members;
    }

    protected List<Expense> getExpenseHistory(){
        return this.expenseHistory;
    }

}

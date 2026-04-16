package edu.yu.cs.com1320;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group {
    private int id;
    private String name;
    private Map<Integer, User> members;
    private List<Expense> expenseHistory;
    private Map<User, Map<User, Double>> debtGraph;

    public Group(int groupId, String groupName){
        this.id = groupId;
        this.name = groupName;
        this.members = new HashMap<>();
        this.expenseHistory = new ArrayList<>();
        // Represents a Directed Graph of debts:
        // Map of (Debtor ID -> Map of (Creditor ID -> Amount Owed))
        // Outer Map represents the vertex (User) and Inner map the edge (Debt)
        this.debtGraph = new HashMap<>();
    }
 
    protected void setDebts(Integer creditor, int amount){
        /*
        this should call a split calculator method that 
        determines how much each person owes and to who
        the split calculator method would return a map 
        with only one key and value which is the creditor 
        id (key) and owed amount (value)

        then we just populate the setDebts with the debtors ID
        and the value of the id is the map from the split calculator method
         */
    }

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

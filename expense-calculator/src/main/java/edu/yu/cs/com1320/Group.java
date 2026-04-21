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

    /**
     * Logs an expense and immediately updates the net balances of everyone involved.
     */
    public void addExpense(Expense expense) {
        // TODO: 1. Add the expense to the expenseHistory list
        // TODO: 2. Calculate the split amount (total / number of participants)
        // TODO: 3. Credit the payer's net balance (add the total amount)
        // TODO: 4. Debit everyone in the split (subtract the split amount from their balance)
    }

    /**
     * The core algorithm: Matches debtors to creditors using Priority Queues 
     * to minimize the total number of transactions.
     * * @return A list of instructions (Debts) to settle all accounts.
     */
    public List<Debt> calculateSettlement() {
        List<Debt> settlements = new ArrayList<>();

        // TODO: 1. Create a Max-Heap for creditors (users with positive balance)
        // TODO: 2. Create a Max-Heap for debtors (users with negative balance)
        // TODO: 3. Populate both heaps by iterating through this.members.values()
        // TODO: 4. Create a while loop that runs until one or both heaps are empty
        // TODO: 5. Inside the loop, pop the top of both heaps, find the min amount, 
        //          create a Debt object, update balances, and push them back if they still have a balance.

        return settlements; // Returns the generated list of instructions
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

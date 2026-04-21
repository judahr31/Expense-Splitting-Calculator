package edu.yu.cs.com1320;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

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

        PriorityQueue<User> creditorHeap = new PriorityQueue<>(Collections.reverseOrder()); // max heap
        PriorityQueue<User> debtorHeap = new PriorityQueue<>(); // min heap
        for (Map.Entry<Integer, User> entry : this.getMembers().entrySet()) {
            creditorHeap.add(entry.getValue());
            debtorHeap.add(entry.getValue());
        }

        while (creditorHeap.isEmpty() || debtorHeap.isEmpty()) {
            
        }


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

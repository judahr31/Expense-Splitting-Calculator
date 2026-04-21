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

    public void addExpense(Expense expense) {
        this.expenseHistory.add(expense);
        double splitAmount = expense.getTotalAmount()/((double)expense.getParticipants().size());
        expense.getPayer().setNetBalance( ((splitAmount) * (expense.getParticipants().size() - 1 )) );

        for (User u : expense.getParticipants()){
            u.setNetBalance(-1*(splitAmount));
        }
    }

    public List<Debt> calculateSettlement() {
        List<Debt> settlements = new ArrayList<>();

        PriorityQueue<User> creditorHeap = new PriorityQueue<>(Collections.reverseOrder()); // max heap
        PriorityQueue<User> debtorHeap = new PriorityQueue<>(); // min heap
        for (Map.Entry<Integer, User> entry : this.getMembers().entrySet()) {
            creditorHeap.add(entry.getValue());
            debtorHeap.add(entry.getValue());
        }

        while (creditorHeap.isEmpty() && debtorHeap.isEmpty()) {
            User creditor = creditorHeap.poll();
            User debtor = debtorHeap.poll();
           
            double creditAmt = creditor.getNetBalance();
            double debtAmt = Math.abs(debtor.getNetBalance());
            
            double settleAmount = Math.min(creditAmt, debtAmt);
            double roundedAmount = Math.round(settleAmount * 100.0) / 100.0;
            settlements.add(new Debt(debtor, creditor, roundedAmount));

            creditor.setNetBalance(creditAmt - settleAmount);
            debtor.setNetBalance(debtor.getNetBalance() + settleAmount);

            if (creditor.getNetBalance() > 0.01) {
                creditorHeap.add(creditor);
            }
            if (debtor.getNetBalance() < -0.01) {
                debtorHeap.add(debtor);
            }
        }
        return settlements;
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

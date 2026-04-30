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
        expense.getPayer().updateNetBalance(expense.getTotalAmount() - splitAmount);

        for (User u : expense.getParticipants()){
            if (!u.equals(expense.getPayer())) {
                u.updateNetBalance(-1*(splitAmount));
            }
        }
    }

    public void removeExpense(Expense expense) {
        if (this.expenseHistory.remove(expense)) {
            List<User> participants = expense.getParticipants();
            if (participants.isEmpty()) return;

            User payer = expense.getPayer();
            double total = expense.getTotalAmount();

            int totalCents = (int) Math.round(total * 100);
            int numPeople = participants.size();

            int baseCentsPerPerson = totalCents / numPeople;
            int leftoverPennies = totalCents % numPeople;

            payer.updateNetBalance(-total);

            for (int i = 0; i < numPeople; i++) {
                User u = participants.get(i);
                int centsToCharge = baseCentsPerPerson + (i < leftoverPennies ? 1 : 0);
                u.updateNetBalance(centsToCharge / 100.0);
            }
        }
    }

    public List<Debt> calculateSettlement() {
        List<Debt> settlements = new ArrayList<>();

        PriorityQueue<User> creditorHeap = new PriorityQueue<>(Collections.reverseOrder()); 
        PriorityQueue<User> debtorHeap = new PriorityQueue<>(); 

        for (User user : this.members.values()) {
            if (user.getNetBalance() > 0.01) {
                creditorHeap.add(user);
            } else if (user.getNetBalance() < -0.01) {
                debtorHeap.add(user);
            }
        }

        while (!creditorHeap.isEmpty() && !debtorHeap.isEmpty()) {
            User creditor = creditorHeap.poll();
            User debtor = debtorHeap.poll();
        
            double creditAmt = creditor.getNetBalance();
            double debtAmt = Math.abs(debtor.getNetBalance());
            
            double settleAmount = Math.min(creditAmt, debtAmt);
            double roundedAmount = Math.round(settleAmount * 100.0) / 100.0;
            
            if (roundedAmount > 0) {
                settlements.add(new Debt(debtor, creditor, roundedAmount));
            }

            creditor.updateNetBalance(-settleAmount); 
            debtor.updateNetBalance(settleAmount);

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

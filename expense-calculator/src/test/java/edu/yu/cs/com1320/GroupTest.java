package edu.yu.cs.com1320;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GroupTest {
    Group group;
    User alice;
    User bob;
    User charlie;

    @BeforeEach
    public void setUp() {
        // Create group and members
        this.group = new Group(1, "The Apartment");
        this.alice = new User(1, "Alice");
        this.bob = new User(2, "Bob");
        this.charlie = new User(3, "Charlie");
        
        group.addMember(alice.getId(), alice);
        group.addMember(bob.getId(), bob);
        group.addMember(charlie.getId(), charlie);
    }

    @Test
    public void testAddExpenseMath() {
        // Alice pays $120 for everyone (Split: $40 each)
        Expense e1 = new Expense("Dinner", 120.0, alice, 101);
        e1.addUser(bob);
        e1.addUser(charlie);
        
        // Use the logic you just wrote
        group.addExpense(e1);

        // Alice should be +80, Bob -40, Charlie -40
        assertEquals(80.0, alice.getNetBalance(), 0.001);
        assertEquals(-40.0, bob.getNetBalance(), 0.001);
        assertEquals(-40.0, charlie.getNetBalance(), 0.001);
    }

    @Test
    public void testMultipleExpensesAccumulate() {
        // 1. Alice pays $90 for all 3 (Alice +60, Bob -30, Charlie -30)
        Expense e1 = new Expense("Groceries", 90.0, alice, 201);
        e1.addUser(bob); 
        e1.addUser(charlie);
        group.addExpense(e1);

        // 2. Bob pays $30 for all 3 (Bob +20, Alice -10, Charlie -10)
        Expense e2 = new Expense("Supplies", 30.0, bob, 202);
        e2.addUser(alice);
        e2.addUser(charlie);
        group.addExpense(e2);

        // Total Expected:
        // Alice: 60 - 10 = 50
        // Bob: -30 + 20 = -10
        // Charlie: -30 - 10 = -40
        assertEquals(50.0, alice.getNetBalance(), 0.001);
        assertEquals(-10.0, bob.getNetBalance(), 0.001);
        assertEquals(-40.0, charlie.getNetBalance(), 0.001);
    }

    @Test
    public void testCalculateSettlement() {
        // Set up a debt manually
        alice.updateNetBalance(50.0);
        bob.updateNetBalance(-10.0);
        charlie.updateNetBalance(-40.0);

        List<Debt> result = group.calculateSettlement();

        // Should take 2 payments to settle Alice up
        assertEquals(2, result.size());
        
        // Ensure total amount settled is $50
        double totalSettled = result.stream().mapToDouble(Debt::amount).sum();
        assertEquals(50.0, totalSettled, 0.001);
    }

    @Test
    public void testRemoveBasicExpense() {
        // Alice pays $30 for all three people ($10 each)
        Expense expense = new Expense("Dinner", 30.00, alice, 101);
        expense.addUser(alice);
        expense.addUser(bob);
        expense.addUser(charlie);

        group.addExpense(expense);

        // Verify balances after adding
        assertEquals(20.00, alice.getNetBalance(), 0.001);
        assertEquals(-10.00, bob.getNetBalance(), 0.001);
        assertEquals(-10.00, charlie.getNetBalance(), 0.001);

        // Remove the expense
        group.removeExpense(expense);

        // Verify balances are perfectly 0.00
        assertEquals(0.00, alice.getNetBalance(), 0.001);
        assertEquals(0.00, bob.getNetBalance(), 0.001);
        assertEquals(0.00, charlie.getNetBalance(), 0.001);
        
        // Verify it was removed from history
        assertFalse(group.getExpenseHistory().contains(expense));
    }

    @Test
    public void testRemoveExpenseWithUnevenPennyDistribution() {
        // Alice pays $10 for all three people ($3.34, $3.33, $3.33)
        Expense expense = new Expense("Snacks", 10.00, alice, 102);
        expense.addUser(alice);
        expense.addUser(bob);
        expense.addUser(charlie);

        group.addExpense(expense);

        // Verify penny distribution logic happened
        // Alice gets credited $10, but charged $3.34. Net = +6.66
        assertEquals(6.66, alice.getNetBalance(), 0.001);
        assertEquals(-3.33, bob.getNetBalance(), 0.001);
        assertEquals(-3.33, charlie.getNetBalance(), 0.001);

        // Remove the expense
        group.removeExpense(expense);

        // Ensure the extra penny was reversed flawlessly
        assertEquals(0.00, alice.getNetBalance(), 0.001);
        assertEquals(0.00, bob.getNetBalance(), 0.001);
        assertEquals(0.00, charlie.getNetBalance(), 0.001);
    }

    @Test
    public void testRemoveOneOfMultipleExpenses() {
        // Expense 1: Alice pays $30 for all 3
        Expense ex1 = new Expense("Lunch", 30.00, alice, 201);
        ex1.addUser(alice);
        ex1.addUser(bob);
        ex1.addUser(charlie);
        group.addExpense(ex1);

        // Expense 2: Bob pays $15 for just Bob and Charlie
        Expense ex2 = new Expense("Drinks", 15.00, bob, 202);
        ex2.addUser(bob);
        ex2.addUser(charlie);
        group.addExpense(ex2);

        // Remove ONLY Expense 1
        group.removeExpense(ex1);

        // Balances should reflect ONLY Expense 2 ($15 split by Bob and Charlie)
        assertEquals(0.00, alice.getNetBalance(), 0.001); // Alice wasn't in ex2
        assertEquals(7.50, bob.getNetBalance(), 0.001);   // Bob paid 15, split 7.50
        assertEquals(-7.50, charlie.getNetBalance(), 0.001); // Charlie owes 7.50
        
        // Ensure settlement still works on remaining expenses
        List<Debt> debts = group.calculateSettlement();
        assertEquals(1, debts.size());
        assertEquals("Charlie", debts.get(0).debtor().getName());
        assertEquals("Bob", debts.get(0).creditor().getName());
        assertEquals(7.50, debts.get(0).amount(), 0.001);
    }

    @Test
    public void testRemoveNonExistentExpense() {
        // Expense created but NEVER added to the group
        Expense fakeExpense = new Expense("Ghost", 100.00, alice, 999);
        fakeExpense.addUser(alice);
        fakeExpense.addUser(bob);

        // Alice and Bob start at 0
        assertEquals(0.00, alice.getNetBalance(), 0.001);

        // Trying to remove an expense that isn't in history shouldn't break anything
        group.removeExpense(fakeExpense);

        // Balances should remain untouched
        assertEquals(0.00, alice.getNetBalance(), 0.001);
        assertEquals(0.00, bob.getNetBalance(), 0.001);
    }
}
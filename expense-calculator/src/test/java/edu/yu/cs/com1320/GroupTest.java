package edu.yu.cs.com1320;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
package edu.yu.cs.com1320;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExpenseTest {
    Expense expense;
    User payer;
    User participant1;
    User participant2;

    @BeforeEach
    public void setUp() {
        // Set up the payer and participants
        this.payer = new User(1, "Alice");
        this.participant1 = new User(2, "Bob");
        this.participant2 = new User(3, "Charlie");
        
        // Initialize the Expense object using your specific constructor
        // String des, double amt, User payer, int expId
        this.expense = new Expense("Dinner at Joe's", 120.00, this.payer, 1001);
        
        // Add participants using your addUser method
        this.expense.addUser(this.participant1);
        this.expense.addUser(this.participant2);
    }
    
    @Test
    public void testGetExpenseID() {
        assertEquals(1001, expense.getExpenseID());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Dinner at Joe's", expense.getDescription());
    }

    @Test
    public void testSetDescription() {
        this.expense.setDescription("Lunch at Joe's");
        assertEquals("Lunch at Joe's", expense.getDescription());
    }

    @Test
    public void testGetTotalAmount() {
        assertEquals(120.00, expense.getTotalAmount());
    }
    
    @Test
    public void testSetTotalAmount() {
        this.expense.setTotalAmount(150.50);
        assertEquals(150.50, expense.getTotalAmount());
    }

    @Test
    public void testGetParticipantsReturnsCopy() {
        // Retrieve the list of participants
        List<User> returnedParticipants = expense.getParticipants();
        
        // Verify the data is correct
        assertEquals(3, returnedParticipants.size());
        assertTrue(returnedParticipants.contains(this.participant1));
        
        // Verify encapsulation (it should NOT be the exact same List object in memory)
        // If someone calls returnedParticipants.clear(), the actual expense object shouldn't lose its data.
        returnedParticipants.clear();
        assertEquals(3, expense.getParticipants().size()); 
    }
}
package edu.yu.cs.com1320;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    User user;
    
    @BeforeEach
    public void setUp() {
        this.user = new User(1, "Oriel");
    }
    
    @Test
    public void testGetID() {
        assertEquals(user.getId(), 1);
    }

    @Test
    public void testGetName() {
        assertEquals(user.getName(), "Oriel");
    }

    @Test
    public void testSetName() {
        this.user.setName("Judah");
        assertEquals(user.getName(), "Judah");
    }

    @Test
    public void testGetNetBalance() {
        assertEquals(user.getNetBalance(), 0);
    }

    @Test
    public void testUpdateNetBalance() {
        this.user.updateNetBalance(100);
        assertEquals(user.getNetBalance(), 100);
        this.user.updateNetBalance(100);
        assertEquals(user.getNetBalance(), 200);
    }
}

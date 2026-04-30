package edu.yu.cs.com1320;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Map<Integer, Group> groups = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int groupCount = 0;
    private static int userCount = 0;

    public static void main(String[] args) {
        boolean running = true;
        clearScreen(); 
        
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            clearScreen(); 
            
            switch (choice) {
                case "1": createGroup(); break;
                case "2": listGroups(); break;
                case "3": manageGroup(); break; 
                case "4": running = false; break;
                default: 
                    System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.\n");
            }
        }
        clearScreen();
        System.out.println("Goodbye!");
    }

    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }

    private static void printMainMenu() {
        System.out.println("=== SYSTEM OVERVIEW ===");
        System.out.println("1. Create New Group");
        System.out.println("2. View All Groups");
        System.out.println("3. Select & Manage a Group");
        System.out.println("4. Exit");
        System.out.print("Selection: ");
    }

    private static void createGroup() {
        String name;
        while (true) {
            System.out.print("Enter Group Name (or 'b' to cancel): ");
            name = scanner.nextLine().trim();
            
            if (name.equalsIgnoreCase("b")) {
                clearScreen();
                return;
            }
            if (name.isEmpty()) {
                System.out.println("Group name cannot be empty. Please try again.");
                continue;
            }

            boolean exists = false;
            for (Group g : groups.values()) {
                if (g.getName().equalsIgnoreCase(name)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                System.out.println("Error: A group with the name '" + name + "' already exists. Please choose a unique name.");
            } else {
                break; 
            }
        }
        groups.put(groupCount, new Group(groupCount, name));
        clearScreen();
        System.out.println("SUCCESS: Group '" + name + "' created with ID: " + groupCount + "\n");
        groupCount++;
    }

    private static void listGroups() {
        if (groups.isEmpty()) {
            System.out.println("No groups available.\n");
            return;
        }
        System.out.println("--- Available Groups ---");
        groups.forEach((id, g) -> System.out.println("ID: " + id + " | Name: " + g.getName()));
        System.out.println("------------------------\n");
    }

    private static void manageGroup() {
        if (groups.isEmpty()) {
            System.out.println("No groups available to manage. Please create one first.\n");
            return;
        }
        
        listGroups(); 
        Group selectedGroup = null;
        
        while (true) {
            System.out.print("Enter the Group ID to manage (or 'b' to go back): ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("b")) {
                clearScreen();
                return; 
            }
            
            try {
                int groupId = Integer.parseInt(input);
                selectedGroup = groups.get(groupId);
                
                if (selectedGroup != null) {
                    break;
                }
                System.out.println("Group ID not found. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid numeric Group ID.");
            }
        }

        boolean back = false;
        clearScreen(); 
        
        while (!back) {
            System.out.println("--- MANAGING GROUP: " + selectedGroup.getName() + " ---");
            System.out.println("1. Add/View Users");
            System.out.println("2. Remove a User");
            System.out.println("3. Log Expense");
            System.out.println("4. View/Delete Expenses"); // NEW MENU OPTION
            System.out.println("5. Calculate Settlement");
            System.out.println("6. Back to Main Menu");
            System.out.print("Selection: ");
            String choice = scanner.nextLine().trim();

            clearScreen(); 

            switch (choice) {
                case "1": manageUsers(selectedGroup); break;
                case "2": removeUser(selectedGroup); break; 
                case "3": logExpenseInGroup(selectedGroup); break;
                case "4": manageExpenses(selectedGroup); break; // NEW METHOD CALL
                case "5": runSettlement(selectedGroup); break;
                case "6": back = true; break;
                default: 
                    System.out.println("Invalid choice. Please enter a number from 1 to 6.\n");
            }
        }
    }

    private static void manageUsers(Group g) {
        System.out.println("--- Current Members ---");
        if (g.getMembers().isEmpty()) {
            System.out.println(" (None)");
        } else {
            g.getMembers().values().forEach(u -> System.out.println(" - " + u.getName() + " (ID: " + u.getId() + ")"));
        }
        System.out.println("-----------------------\n");
        
        while (true) {
            System.out.print("Add new user? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            
            if (choice.equals("n")) {
                clearScreen(); 
                break;
            } else if (choice.equals("y")) {
                String name;
                while (true) {
                    System.out.print("Name: ");
                    name = scanner.nextLine().trim();
                    if (name.isEmpty()) {
                        System.out.println("Name cannot be empty. Please try again.");
                        continue;
                    }

                    boolean exists = false;
                    for (User u : g.getMembers().values()) {
                        if (u.getName().equalsIgnoreCase(name)) {
                            exists = true;
                            break;
                        }
                    }

                    if (exists) {
                        System.out.println("Error: A user named '" + name + "' is already in this group. Please use a unique name or initial.");
                    } else {
                        break; 
                    }
                }
                g.addMember(userCount, new User(userCount, name));
                clearScreen(); 
                System.out.println("SUCCESS: User '" + name + "' added successfully with ID: " + userCount + "\n");
                userCount++;
                break; 
            } else {
                System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
            }
        }
    }

    private static void removeUser(Group g) {
        System.out.println("--- Current Members ---");
        if (g.getMembers().isEmpty()) {
            System.out.println(" (None to remove)\n");
            return;
        }
        g.getMembers().values().forEach(u -> 
            System.out.printf(" - %s (ID: %d) | Balance: $%.2f%n", u.getName(), u.getId(), u.getNetBalance())
        );
        System.out.println("-----------------------\n");

        System.out.print("Enter the ID of the user to remove (or 'b' to cancel): ");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("b")) {
            clearScreen();
            return;
        }

        try {
            int userId = Integer.parseInt(input);
            User u = g.getMembers().get(userId);

            if (u == null) {
                clearScreen();
                System.out.println("Error: User ID not found in this group.\n");
                return;
            }

            if (Math.abs(u.getNetBalance()) > 0.01) {
                clearScreen();
                System.out.println("ERROR: Cannot remove " + u.getName() + ".");
                System.out.println("Their balance is currently $" + String.format("%.2f", u.getNetBalance()) + ".");
                System.out.println("You must settle all debts involving this user before removing them from the group.\n");
                return;
            }

            g.getMembers().remove(userId);
            clearScreen();
            System.out.println("SUCCESS: User '" + u.getName() + "' has been removed from the group.\n");

        } catch (NumberFormatException e) {
            clearScreen();
            System.out.println("Error: Invalid input. Please enter a numeric ID.\n");
        }
    }

    private static void logExpenseInGroup(Group g) {
        if (g.getMembers().size() < 2) {
            System.out.println("Error: Need at least 2 members to split an expense.\n");
            return;
        }

        System.out.println("--- Log New Expense (Type 'b' at any time to cancel) ---");

        String desc;
        while (true) {
            System.out.print("Description: ");
            desc = scanner.nextLine().trim();
            if (desc.equalsIgnoreCase("b")) {
                clearScreen();
                return; 
            }
            if (!desc.isEmpty()) {
                break;
            }
            System.out.println("Description cannot be empty. Please try again.");
        }

        double amt;
        while (true) {
            System.out.print("Amount: ");
            String amtInput = scanner.nextLine().trim();
            if (amtInput.equalsIgnoreCase("b")) {
                clearScreen();
                return; 
            }
            try {
                amt = Double.parseDouble(amtInput);
                if (amt > 0) {
                    break;
                }
                System.out.println("Amount must be greater than zero. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number (e.g., 150.75).");
            }
        }

        System.out.println("\nSelect Payer by ID:");
        g.getMembers().values().forEach(u -> System.out.println(u.getId() + ": " + u.getName()));
        
        User payer = null;
        while (true) {
            System.out.print("Payer ID: ");
            String payerInput = scanner.nextLine().trim();
            if (payerInput.equalsIgnoreCase("b")) {
                clearScreen();
                return; 
            }
            try {
                int payerId = Integer.parseInt(payerInput);
                payer = g.getMembers().get(payerId);
                if (payer != null) {
                    break;
                }
                System.out.println("User ID not found in this group. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid numeric User ID.");
            }
        }

        Expense ex = new Expense(desc, amt, payer, (int)(Math.random() * 1000));
        
        while (true) {
            System.out.println("\nWho is splitting this? (Enter IDs separated by comma, or 'all'):");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("b")) {
                clearScreen();
                return; 
            }

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                continue;
            }

            if (input.equalsIgnoreCase("all")) {
                g.getMembers().values().forEach(ex::addUser);
                break;
            } else {
                String[] ids = input.split(",");
                boolean allValid = true;
                
                for (String idStr : ids) {
                    try {
                        int id = Integer.parseInt(idStr.trim());
                        if (!g.getMembers().containsKey(id)) {
                            System.out.println("Error: User ID " + id + " not found in this group.");
                            allValid = false;
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid format '" + idStr.trim() + "'. Use numbers separated by commas.");
                        allValid = false;
                        break;
                    }
                }
                
                if (allValid && ids.length > 0) {
                    for (String idStr : ids) {
                        int id = Integer.parseInt(idStr.trim());
                        ex.addUser(g.getMembers().get(id));
                    }
                    break;
                } else if (!allValid) {
                    System.out.println("Please re-enter the IDs.");
                }
            }
        }
        g.addExpense(ex);
        clearScreen(); 
        System.out.println("SUCCESS: Expense logged successfully!\n");
    }

    private static void manageExpenses(Group g) {
        System.out.println("--- Expense History ---");
        List<Expense> history = g.getExpenseHistory();
        
        if (history.isEmpty()) {
            System.out.println(" (No expenses logged)\n");
            return;
        }

        for (int i = 0; i < history.size(); i++) {
            Expense ex = history.get(i);
            System.out.printf("%d. %s | $%.2f paid by %s%n", 
                (i + 1), ex.getDescription(), ex.getTotalAmount(), ex.getPayer().getName());
        }
        System.out.println("-----------------------\n");

        System.out.print("Enter the number of the expense to delete (or 'b' to go back): ");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("b")) {
            clearScreen();
            return;
        }

        try {
            int index = Integer.parseInt(input) - 1;
            if (index >= 0 && index < history.size()) {
                Expense toRemove = history.get(index);
                g.removeExpense(toRemove);
                clearScreen();
                System.out.println("SUCCESS: Expense '" + toRemove.getDescription() + "' deleted and balances safely adjusted.\n");
            } else {
                clearScreen();
                System.out.println("Error: Invalid expense number. Please enter a number from the list.\n");
            }
        } catch (NumberFormatException e) {
            clearScreen();
            System.out.println("Error: Invalid input. Please enter a numeric value.\n");
        }
    }

    private static void runSettlement(Group g) {
        List<Debt> result = g.calculateSettlement();
        if (result.isEmpty()) {
            System.out.println("No outstanding debts! Everyone is settled up.\n");
        } else {
            System.out.println("--- Optimal Settlement Plan ---");
            result.forEach(d -> System.out.printf("%s pays %s: $%.2f%n", 
                d.debtor().getName(), d.creditor().getName(), d.amount()));
            System.out.println("-------------------------------\n");
        }
    }
}
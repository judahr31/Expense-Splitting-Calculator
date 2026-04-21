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
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": createGroup(); break;
                case "2": listGroups(); break;
                case "3": manageGroup(); break;
                case "4": running = false; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n=== SYSTEM OVERVIEW ===");
        System.out.println("1. Create New Group");
        System.out.println("2. View All Groups");
        System.out.println("3. Select & Manage a Group");
        System.out.println("4. Exit");
        System.out.print("Selection: ");
    }

    private static void createGroup() {
        System.out.print("Enter Group Name: ");
        String name = scanner.nextLine();
        groups.put(groupCount, new Group(groupCount++, name));
        System.out.println("Group '" + name + "' created.");
    }

    private static void listGroups() {
        if (groups.isEmpty()) {
            System.out.println("No groups available.");
            return;
        }
        System.out.println("\nAvailable Groups:");
        groups.forEach((id, g) -> System.out.println("ID: " + id + " | Name: " + g.getName()));
    }

    private static void manageGroup() {
        listGroups();
        if (groups.isEmpty()) return;

        System.out.print("Enter the Group ID to manage: ");
        int groupId = Integer.parseInt(scanner.nextLine());
        Group selectedGroup = groups.get(groupId);

        if (selectedGroup == null) {
            System.out.println("Group not found.");
            return;
        }

        boolean back = false;
        while (!back) {
            System.out.println("\n--- MANAGING GROUP: " + selectedGroup.getName() + " ---");
            System.out.println("1. Add/View Users");
            System.out.println("2. Log Expense");
            System.out.println("3. Calculate Settlement");
            System.out.println("4. Back to Main Menu");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": manageUsers(selectedGroup); break;
                case "2": logExpenseInGroup(selectedGroup); break;
                case "3": runSettlement(selectedGroup); break;
                case "4": back = true; break;
            }
        }
    }

    private static void manageUsers(Group g) {
        System.out.println("Current Members:");
        g.getMembers().values().forEach(u -> System.out.println(" - " + u.getName() + " (ID: " + u.getId() + ")"));
        
        System.out.print("Add new user? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.print("Name: ");
            String name = scanner.nextLine();
            g.addMember(userCount, new User(userCount++, name));
        }
    }

    private static void logExpenseInGroup(Group g) {
        if (g.getMembers().size() < 2) {
            System.out.println("Need at least 2 members to split an expense.");
            return;
        }

        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.print("Amount: ");
        double amt = Double.parseDouble(scanner.nextLine());

        System.out.println("Select Payer by ID:");
        g.getMembers().values().forEach(u -> System.out.println(u.getId() + ": " + u.getName()));
        int payerId = Integer.parseInt(scanner.nextLine());
        User payer = g.getMembers().get(payerId);

        Expense ex = new Expense(desc, amt, payer, (int)(Math.random() * 1000));
        
        System.out.println("Who is splitting this? (Enter IDs separated by comma, or 'all'):");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("all")) {
            g.getMembers().values().forEach(ex::addUser);
        } else {
            for (String id : input.split(",")) {
                User u = g.getMembers().get(Integer.parseInt(id.trim()));
                if (u != null) ex.addUser(u);
            }
        }
        g.addExpense(ex);
        System.out.println("Expense logged successfully!");
    }

    private static void runSettlement(Group g) {
        List<Debt> result = g.calculateSettlement();
        if (result.isEmpty()) {
            System.out.println("No outstanding debts!");
        } else {
            result.forEach(d -> System.out.printf("%s pays %s: $%.2f%n", 
                d.debtor().getName(), d.creditor().getName(), d.amount()));
        }
    }
}
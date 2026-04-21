package edu.yu.cs.com1320;

import java.util.Scanner;
import main.java.edu.yu.cs.com1320.*;

public class Main {
    
    public static void main (String[] args){
        int userCount = 0;
        Scanner reader = new Scanner(System.in);
        System.out.println("Welcome to the Expense Calculator (aka Ya-Cheshbon).\n
         We're here 6 days a week and open after R\"T on Motzash (Sorry in Advance).\n 
         The way this works is very simple, we'll ask a few questions and you answer,
         \n then we return to you how much each person owes, etc (you'll see). Ready?? (yes or no)");

        String yesOrNo = read.nextLine();
        if (!yesOrNo.equals("yes") || 
        !yesOrNo.equals("Yes") || 
        !yesOrNo.equals("YES") || 
        !yesOrNo.equals("Y") ||
        !yesOrNo.equals("y") ||
        !yesOrNo.equals("כן")){
            System.out.println("Sorry to hear you're not ready. Seek your local 
            Orthodox Rav or Rebbetzin for guidance");
        }
        System.out.println("Group Name?: ");
        String grpName = read.nextLine();
        Group group = new Group(userCount++, grpName );
        System.out.println("Please list the people in the group by name, 
        separating each name by a \",\" thank you (Ex: Avaraham, Yitzchok, Yaakov)");

        String strPpl = read.nextLine();
        String[] arrPpl = strPpl.split(",");
        for (String s : arrPpl){
            User u = new User(userCount++, s);
            group.addMember(u.getId(), u);
        }
        
        System.out.println("How much was the total bill?: ");
        String bill = read.nextLine();
        System.out.println("Describe the event in a few words or sentences: ");
        String description = read.nextLine();
        System.out.println("Who Paid");
        String payer = read.nextLine();
        Expense expense = new Expense(description, bill, payer, userCount++);

    }
}

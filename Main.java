//Name: Saimon Asghedom
//course: CS202
//file: this file will be used to read in a file that it passed by the user
//parse the file into tokens and check for correct syntax and store them accordingly
//Some of the operation I made available for this program are addition, subtraction, assigning
//you can declare a variable, assign it to a value at the same time or later, don't have support
//for multiple declaration. you can print a literal string or a variable, not both at the same time
//I also don't have support for to read in from the user.
//Some of the rules I set for this program is
//1. you have to declare a variable before you use it
//2. you can only add or subtract two numbers at a time and are not stand alone statement, a + b doesn't work c = a + b work
//3. you can only print one thing at a time, print a(variable) or print "write something"
//4. Support math op: = + -, no support for math op: * % /
//5. you can use the start and end words but they are not necessary

import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        String name;
        String filename;
        String response;
        int answer;
        int option;
        red_black obj = new red_black();
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("1. add files");
            System.out.println("2. check file for errors");
            System.out.println("3. display all the files");
            System.out.print("choose from the options: ");
            option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1 -> { //option 1 adding files to the table
                    System.out.print("How many files do you have to enter: ");
                    answer = input.nextInt();
                    input.nextLine();
                    for (int i = 0; i < answer; ++i) {
                        System.out.print("Enter your name: ");
                        name = input.nextLine();
                        System.out.print("Enter your the link to your file: "); //provide the absolute path to your file
                        filename = input.nextLine();

                        obj.insert(name, filename);
                    }
                }
                case 2 -> { //option 2 retrieve file and check for errors
                    System.out.print("Whose file do you want to check for errors: ");
                    name = input.nextLine();
                    String[] file = new String[1];
                    if (obj.retrieve(name, file) == 0) {
                        System.out.println("There was an error retrieving the file.");
                    } else {
                        System.out.println("Filename: " + file[0]);
                        obj.check_file(file[0]);
                    }
                }
                case 3 -> obj.display_all();    //option 3 diplay all the files in the table
            }

            System.out.print("Do you want to see the options again: ");
            response = input.nextLine();
        }while(response.equalsIgnoreCase("YES"));
    }
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Map<String, Double> courses = new HashMap<>();
        courses.put("MATHEMATICS", 80.0);
        courses.put("COMPUTER SCIENCE", 75.0);
        courses.put("ECONOMICS", 85.0);

        
        List<String> skills = new ArrayList<>();
        skills.add("Programming");
        skills.add("Communication");
        skills.add("Problem Solving");

        
        Student student1 = new Student(
                1,
                "John",
                21,
                courses,
                skills
        );

    
        List<Student> registeredStudents = new ArrayList<>();

    
        registeredStudents.add(student1);
         System.out.println("Registered Students:");

        for (Student student : registeredStudents) {
            System.out.println(student);
        }
    }
     int choice;
        do {
            displayMenu();
            try {
                choice = Integer.parseInt(scanner.nextLine());
                handleChoice(choice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number.");
                choice = -1;
            }
        } while (choice != 0);
        
    }



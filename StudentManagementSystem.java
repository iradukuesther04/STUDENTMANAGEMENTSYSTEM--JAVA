import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StudentManagementSystem {
    private static List<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);


    private static void displayMenu() {
        System.out.println("\n==========================================");
        System.out.println("STUDENT MANAGEMENT SYSTEM");
        System.out.println("==========================================");
        System.out.println("1. Register New Student");
        System.out.println("2. Display All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Filter Passing Students (Avg >=65)");
        System.out.println("5. Filter Failing Students (Avg <65)");
        System.out.println("6. Search Students by Name");
        System.out.println("7. Sort Students by Name");
        System.out.println("8. Sort Students by Average Marks");
        System.out.println("9. Display All Courses");
        System.out.println("10. Display Unique Courses");
        System.out.println("11. Search Students by Course");
        System.out.println("12. Display Unique Skills");
        System.out.println("13. Group Students by Course");
        System.out.println("14. View Student Performance Statistics");
        System.out.println("15. View Best Performing Student");
        System.out.println("16. View Top 3 Students");
        System.out.println("17. Apply Bonus Marks");
        System.out.println("18. View Final Student Ranking");
        System.out.println("0. Exit");
        System.out.println("==========================================");
        System.out.print("Enter your choice: ");
    }
    
    private static void handleChoice(int choice) {
        switch (choice) {
            case 1 -> registerStudent();
            case 2 -> displayAllStudents();
            case 3 -> searchById();
            case 4 -> filterPassing();
            case 5 -> filterFailing();
            case 6 -> searchByName();
            case 7 -> sortByName();
            case 8 -> sortByAverage();
            case 9 -> displayAllCourses();
            case 10 -> displayUniqueCourses();
            case 11 -> searchByCourse();
            case 12 -> displayUniqueSkills();
            case 13 -> groupByCourse();
            case 14 -> performanceReport();
            case 15 -> bestStudent();
            case 16 -> top3Students();
            case 17 -> applyBonus();
            case 18 -> finalRanking();
            case 0 -> System.out.println("Exiting... Thank you!");
            default -> System.out.println("Invalid option! Try again.");
        }
        private static void registerStudent() {
        try {
            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            if (students.stream().anyMatch(s -> s.getId() == id)) {
                System.out.println("Error: Duplicate ID! Student already exists.");
                return;
            }
            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Age: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Number of Courses: ");
            int numCourses = Integer.parseInt(scanner.nextLine());
            Map<String, Double> courses = new HashMap<>();
            for (int i = 0; i < numCourses; i++) {
                System.out.print("Course " + (i + 1) + " Name: ");
                String cName = scanner.nextLine();
                System.out.print("Mark for " + cName + " (0-100): ");
                double mark = Double.parseDouble(scanner.nextLine());
                if (mark < 0 || mark > 100) {
                    System.out.println("Invalid mark! Must be 0-100. Set to 0.");
                    mark = 0;
                }
                courses.put(cName, mark);
            }

            System.out.print("Enter number of skills: ");
            int numSkills = Integer.parseInt(scanner.nextLine());
            List<String> skills = new ArrayList<>();
            for (int i = 0; i < numSkills; i++) {
                System.out.print("Skill " + (i + 1) + ": ");
                skills.add(scanner.nextLine());
            }

            students.add(new Student(id, name, age, courses, skills));
            System.out.println("Student registered successfully!");

        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private static void displayAllStudents() {
        if (students.isEmpty()) { System.out.println("No students registered."); return; }
        students.stream().forEach(System.out::println);
    }

    private static void searchById() {
        System.out.print("Enter ID to search: ");
        int id = Integer.parseInt(scanner.nextLine());
        students.stream().filter(s -> s.getId() == id).findFirst()
                .ifPresentOrElse(System.out::println, () -> System.out.println("Student not found."));
    }


    private static void filterPassing() {
        System.out.println("--- Passing Students (Avg >= 65) ---");
        students.stream().filter(s -> s.getAverage() >= 65).forEach(System.out::println);
    }

    private static void filterFailing() {
        System.out.println("--- Failing Students (Avg < 65) ---");
        students.stream().filter(s -> s.getAverage() < 65).forEach(System.out::println);
    }

    private static void searchByName() {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine().toLowerCase();
        students.stream().filter(s -> s.getStudentName().toLowerCase().contains(name))
                .forEach(System.out::println);
    }

    private static void sortByName() {
        students.stream().sorted(Comparator.comparing(Student::getStudentName))
                .forEach(System.out::println);
    }

    private static void sortByAverage() {
        students.stream().sorted(Comparator.comparing(Student::getAverage).reversed())
                .forEach(System.out::println);
    }


    private static void displayAllCourses() {
        students.stream().flatMap(s -> s.getCourses().keySet().stream()).forEach(System.out::println);
    }

    private static void displayUniqueCourses() {
        students.stream().flatMap(s -> s.getCourses().keySet().stream()).distinct().sorted()
                .forEach(System.out::println);
    }

    private static void searchByCourse() {
        System.out.print("Enter course name: ");
        String course = scanner.nextLine();
        students.stream().filter(s -> s.getCourses().containsKey(course)).forEach(System.out::println);
    }

    private static void displayUniqueSkills() {
        students.stream().flatMap(s -> s.getSkills().stream()).distinct().sorted()
                .forEach(System.out::println);
    }

    private static void groupByCourse() {
        Map<String, List<Student>> grouped = students.stream()
                .flatMap(s -> s.getCourses().keySet().stream().map(c -> new AbstractMap.SimpleEntry<>(c, s)))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
        grouped.forEach((course, list) -> {
            System.out.println("\nCourse: " + course);
            list.forEach(st -> System.out.println("  - " + st.getStudentName()));
        });
    }


    private static void performanceReport() {
        if (students.isEmpty()) { System.out.println("No students."); return; }
        students.forEach(s -> {
            DoubleSummaryStatistics stats = s.getCourses().values().stream().mapToDouble(d -> d).summaryStatistics();
            System.out.println("\n" + s.getStudentName() + " (ID:" + s.getId() + ")");
            System.out.println("  Avg: " + String.format("%.2f", stats.getAverage()) + ", Highest: " + stats.getMax() + ", Lowest: " + stats.getMin() + ", Sum: " + stats.getSum() + ", Count: " + stats.getCount());
        });
        Student best = students.stream().max(Comparator.comparing(Student::getAverage)).orElse(null);
        long passed = students.stream().filter(s -> s.getAverage() >= 50).count();
        System.out.println("\nBest Student: " + best);
        System.out.println("Number of Passed Students: " + passed);
    }

    private static void bestStudent() {
        students.stream().max(Comparator.comparing(Student::getAverage))
                .ifPresentOrElse(s -> System.out.println("Best: " + s), () -> System.out.println("No students."));
    }

    private static void top3Students() {
        System.out.println("--- Top 3 Students ---");
        students.stream().sorted(Comparator.comparing(Student::getAverage).reversed()).limit(3)
                .forEach(System.out::println);
    }

    // TASK 5
    private static void applyBonus() {
        System.out.println("--- Applying Bonus (10 marks, max 100, avg>50, once only) ---");
        students.stream().filter(s -> !s.isBonusApplied()).filter(s -> s.getAverage() > 50)
                .forEach(s -> {
                    double oldAvg = s.getAverage();
                    Map<String, Double> newCourses = s.getCourses().entrySet().stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, e -> Math.min(100, e.getValue() + 10)));
                    s.setCourses(newCourses);
                    s.setBonusApplied(true);
                    System.out.println(s.getStudentName() + ": Old Avg=" + String.format("%.2f", oldAvg) + " -> New Avg=" + String.format("%.2f", s.getAverage()));
                });
    }

    private static void finalRanking() {
        System.out.println("--- FINAL RANKING (Highest to Lowest) ---");
        students.stream().sorted(Comparator.comparing(Student::getAverage).reversed())
                .map(s -> s.getStudentName() + " - Avg: " + String.format("%.2f", s.getAverage()))
                .forEach(System.out::println);
    }

}

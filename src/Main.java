import java.util.Scanner;

public class Main {
    private static TaskManager taskManager;
    private static Scanner scanner;

    public static void main(String[] args) {
        System.out.println("Task Management System");
        System.out.println("=====================");

        // Initialize the task manager and scanner
        taskManager = new TaskManager();
        scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    removeTask();
                    break;
                case 3:
                    markTaskAsCompleted();
                    break;
                case 4:
                    changeTaskPriority();
                    break;
                case 5:
                    viewAllTasks();
                    break;
                case 6:
                    viewTasksByPriority();
                    break;
                case 7:
                    viewPendingTasks();
                    break;
                case 8:
                    viewCompletedTasks();
                    break;
                case 9:
                    searchTaskByTitle();
                    break;
                case 10:
                    searchTaskById();
                    break;
                case 11:
                    processTasks();
                    break;
                case 12:
                    undoLastTask();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using Task Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            // Wait for user to press Enter to continue
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Add a new task");
        System.out.println("2. Remove a task");
        System.out.println("3. Mark a task as completed");
        System.out.println("4. Change task priority");
        System.out.println("5. View all tasks");
        System.out.println("6. View tasks by priority");
        System.out.println("7. View pending tasks");
        System.out.println("8. View completed tasks");
        System.out.println("9. Search task by title");
        System.out.println("10. Search task by ID");
        System.out.println("11. Process tasks in priority order");
        System.out.println("12. Undo last added task");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            return choice;
        } catch (NumberFormatException e) {
            return -1; // Invalid input
        }
    }

    private static void addTask() {
        System.out.println("\n--- Add a New Task ---");

        System.out.print("Enter task title: ");
        String title = scanner.nextLine();

        System.out.println("Select priority:");
        System.out.println("1. LOW");
        System.out.println("2. MEDIUM");
        System.out.println("3. HIGH");
        System.out.print("Enter choice (1-3): ");

        Task.Priority priority;
        try {
            int priorityChoice = Integer.parseInt(scanner.nextLine());
            switch (priorityChoice) {
                case 1:
                    priority = Task.Priority.LOW;
                    break;
                case 2:
                    priority = Task.Priority.MEDIUM;
                    break;
                case 3:
                    priority = Task.Priority.HIGH;
                    break;
                default:
                    System.out.println("Invalid choice. Setting priority to MEDIUM.");
                    priority = Task.Priority.MEDIUM;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Setting priority to MEDIUM.");
            priority = Task.Priority.MEDIUM;
        }

        Task newTask = taskManager.addTask(title, priority);
        System.out.println("Task added successfully: " + newTask);
    }

    private static void removeTask() {
        System.out.println("\n--- Remove a Task ---");

        System.out.print("Enter task ID to remove: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            boolean removed = taskManager.removeTask(id);

            if (removed) {
                System.out.println("Task with ID " + id + " removed successfully.");
            } else {
                System.out.println("Task with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private static void markTaskAsCompleted() {
        System.out.println("\n--- Mark Task as Completed ---");

        System.out.print("Enter task ID to mark as completed: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            boolean marked = taskManager.markTaskAsCompleted(id);

            if (marked) {
                System.out.println("Task with ID " + id + " marked as completed.");
            } else {
                System.out.println("Task with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private static void changeTaskPriority() {
        System.out.println("\n--- Change Task Priority ---");

        System.out.print("Enter task ID to change priority: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());

            Task task = taskManager.findTaskById(id);
            if (task == null) {
                System.out.println("Task with ID " + id + " not found.");
                return;
            }

            System.out.println("Current task: " + task);
            System.out.println("Select new priority:");
            System.out.println("1. LOW");
            System.out.println("2. MEDIUM");
            System.out.println("3. HIGH");
            System.out.print("Enter choice (1-3): ");

            Task.Priority priority;
            try {
                int priorityChoice = Integer.parseInt(scanner.nextLine());
                switch (priorityChoice) {
                    case 1:
                        priority = Task.Priority.LOW;
                        break;
                    case 2:
                        priority = Task.Priority.MEDIUM;
                        break;
                    case 3:
                        priority = Task.Priority.HIGH;
                        break;
                    default:
                        System.out.println("Invalid choice. Keeping current priority.");
                        return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Keeping current priority.");
                return;
            }

            boolean changed = taskManager.changeTaskPriority(id, priority);
            if (changed) {
                System.out.println("Task priority updated successfully.");
            } else {
                System.out.println("Failed to update task priority.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private static void viewAllTasks() {
        System.out.println("\n--- All Tasks ---");
        printTasks(taskManager.getAllTasks());
    }

    private static void viewTasksByPriority() {
        System.out.println("\n--- View Tasks by Priority ---");
        System.out.println("Select priority:");
        System.out.println("1. LOW");
        System.out.println("2. MEDIUM");
        System.out.println("3. HIGH");
        System.out.print("Enter choice (1-3): ");

        try {
            int priorityChoice = Integer.parseInt(scanner.nextLine());
            Task.Priority priority;

            switch (priorityChoice) {
                case 1:
                    priority = Task.Priority.LOW;
                    break;
                case 2:
                    priority = Task.Priority.MEDIUM;
                    break;
                case 3:
                    priority = Task.Priority.HIGH;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            System.out.println("\n" + priority + " Priority Tasks:");
            printTasks(taskManager.getTasksByPriority(priority));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private static void viewPendingTasks() {
        System.out.println("\n--- Pending Tasks ---");
        printTasks(taskManager.getPendingTasks());
    }

    private static void viewCompletedTasks() {
        System.out.println("\n--- Completed Tasks ---");
        printTasks(taskManager.getCompletedTasks());
    }

    private static void searchTaskByTitle() {
        System.out.println("\n--- Search Task by Title ---");
        System.out.print("Enter title to search: ");
        String title = scanner.nextLine();

        System.out.println("\nMatching tasks:");
        printTasks(taskManager.findTasksByTitle(title));
    }

    private static void searchTaskById() {
        System.out.println("\n--- Search Task by ID ---");
        System.out.print("Enter task ID: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());
            Task task = taskManager.findTaskById(id);

            if (task != null) {
                System.out.println("Found task: " + task);
            } else {
                System.out.println("Task with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private static void processTasks() {
        System.out.println("\n--- Process Tasks ---");
        System.out.println("Processing tasks in priority order (HIGH > MEDIUM > LOW):");

        taskManager.queueTasksForProcessing();
        Task processed = taskManager.processNextTask();

        if (processed != null) {
            System.out.println("Processed: " + processed);
        } else {
            System.out.println("No tasks to process.");
        }
    }

    private static void undoLastTask() {
        System.out.println("\n--- Undo Last Added Task ---");
        Task undoneTask = taskManager.undoLastTask();

        if (undoneTask != null) {
            System.out.println("Undone task: " + undoneTask);
        } else {
            System.out.println("No task to undo.");
        }
    }

    // Helper method to print a list of tasks
    private static void printTasks(LinkedList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        System.out.println("-----------------------------------------");
        System.out.printf("%-5s | %-25s | %-8s | %-10s\n", "ID", "Title", "Priority", "Status");
        System.out.println("-----------------------------------------");

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.printf("%-5d | %-25s | %-8s | %-10s\n",
                    task.getId(),
                    task.getTitle().length() > 25 ? task.getTitle().substring(0, 22) + "..." : task.getTitle(),
                    task.getPriority(),
                    task.isCompleted() ? "Completed" : "Pending");
        }
        System.out.println("-----------------------------------------");
    }
}
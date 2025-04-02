public class Task {
    private int id;
    private String title;
    private Priority priority;
    private boolean completed;

    // Enum for priority levels
    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    // Constructor
    public Task(int id, String title, Priority priority) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.completed = false;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Task #" + id + ": " + title + " [Priority: " + priority +
                ", Status: " + (completed ? "Completed" : "Pending") + "]";
    }
}
public class TaskManager {
    private LinkedList<Task> tasks;
    private Stack<Task> undoStack;
    private Queue<Task> processingQueue;
    private int nextTaskId;

    public TaskManager() {
        tasks = new LinkedList<>();
        undoStack = new Stack<>();
        processingQueue = new Queue<>();
        nextTaskId = 1;
    }

    // Add a new task
    public Task addTask(String title, Task.Priority priority) {
        Task newTask = new Task(nextTaskId++, title, priority);
        tasks.add(newTask);
        undoStack.push(newTask); // Save for undo operation
        return newTask;
    }

    // Remove a task by ID
    public boolean removeTask(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getId() == id) {
                tasks.remove(i);
                return true;
            }
        }
        return false;
    }

    // Search for a task by ID
    public Task findTaskById(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    // Search for tasks by title (partial match)
    public LinkedList<Task> findTasksByTitle(String title) {
        LinkedList<Task> result = new LinkedList<>();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(task);
            }
        }

        return result;
    }

    // Mark a task as completed
    public boolean markTaskAsCompleted(int id) {
        Task task = findTaskById(id);
        if (task != null) {
            task.setCompleted(true);
            return true;
        }
        return false;
    }

    // Change task priority
    public boolean changeTaskPriority(int id, Task.Priority newPriority) {
        Task task = findTaskById(id);
        if (task != null) {
            task.setPriority(newPriority);
            return true;
        }
        return false;
    }

    // Undo last added task
    public Task undoLastTask() {
        if (!undoStack.isEmpty()) {
            Task lastTask = undoStack.pop();
            removeTask(lastTask.getId());
            return lastTask;
        }
        return null;
    }

    // Queue tasks for processing based on priority
    public void queueTasksForProcessing() {
        // Clear the current processing queue
        while (!processingQueue.isEmpty()) {
            processingQueue.dequeue();
        }

        // First, add all HIGH priority tasks
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getPriority() == Task.Priority.HIGH && !task.isCompleted()) {
                processingQueue.enqueue(task);
            }
        }

        // Then, add all MEDIUM priority tasks
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getPriority() == Task.Priority.MEDIUM && !task.isCompleted()) {
                processingQueue.enqueue(task);
            }
        }

        // Finally, add all LOW priority tasks
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getPriority() == Task.Priority.LOW && !task.isCompleted()) {
                processingQueue.enqueue(task);
            }
        }
    }

    // Process the next task
    public Task processNextTask() {
        if (processingQueue.isEmpty()) {
            queueTasksForProcessing();
            if (processingQueue.isEmpty()) {
                return null; // No tasks to process
            }
        }

        Task nextTask = processingQueue.dequeue();
        nextTask.setCompleted(true);
        return nextTask;
    }

    // Get all tasks
    public LinkedList<Task> getAllTasks() {
        return tasks;
    }

    // Get tasks by priority
    public LinkedList<Task> getTasksByPriority(Task.Priority priority) {
        LinkedList<Task> result = new LinkedList<>();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getPriority() == priority) {
                result.add(task);
            }
        }

        return result;
    }

    // Get pending tasks
    public LinkedList<Task> getPendingTasks() {
        LinkedList<Task> result = new LinkedList<>();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (!task.isCompleted()) {
                result.add(task);
            }
        }

        return result;
    }

    // Get completed tasks
    public LinkedList<Task> getCompletedTasks() {
        LinkedList<Task> result = new LinkedList<>();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.isCompleted()) {
                result.add(task);
            }
        }

        return result;
    }
}
# BRUCE User Guide

---
![Product screenshot](images/SS-opening.png)

This is BRUCE, your very own assistant ChatBot!
BRUCE can assist with documenting various tasks, in this case both future To Do:s, Deadlines and Events all via the text Command-Line Interface (CLI for short).

---
## Features

---
### 1. Add a Todo
Create a simple task and add it to your list.

**Usage:**
```
todo <description>
```
**Example:**
```
todo finish assignment
```

---

### 2. Adding Deadlines
Create a task with a due date and add it to your list.

**Usage:**
```
deadline <description> /by <due date>
```
**Examples:**
```
deadline submit report /by 10-03-2025
```
```
deadline submit report /by Tomorrow 23:59
```

---

### 3. Add Events
Create a simple task and add it to your list.

**Usage:**
```
event <description> /from <start> /to <end>
```
**Example:**
```
event project meeting /from Wednesday 14:00 /to 16:00
```

---

### 4. List all Tasks
See all the tasks created.

**Usage:**
```
list
```

---
### 5. Find Tasks by Keyword
Search and find tasks containing specific words.

**Usage:**
```
find <keyword>
```
**Example:**
```
find books
```

---
### 6. Mark Task as Done
Mark a specific task as completed.

This is completed via entering the correct index of the task.
Recommendation to first view all your taks by utilizing the command:

**list**

and then pick the corresponding correct index for the task you would like to mark as done.

**Usage:**
```
mark <task index>
```
**Example:**
```
mark 7
```
---
### 7. Mark Task as not done
Mark a specific task as not completed.

This is completed via entering the correct index of the task.
Recommendation to first view all your taks by utilizing the command:

**list**

and then pick the corresponding correct index for the task you would like to mark as done.

**Usage:**
```
unmark <task number>
```
**Example:**
```
unmark 7
```
---
### 8. Delete a Task
Remove a task from your list.

This is completed via entering the correct index of the task.
Recommendation to first view all your taks by utilizing the command:

**list**

and then pick the corresponding correct index for the task you would like to mark as done.

**Usage:**
```
delete <task number>
```
**Example:**
```
delete 7
```
---
### 8. Exit the Program: BRUCE
Say goodbye to Bruce and exit the program. This also and saves your list of Tasks before exit.

**Usage:**
```
bye.
```
**Example:**
```
delete 7
```


---
## Notes

Dates and times can be entered in any format you prefer, but recommendation is to be consistent for your own reference.

Task numbers refer to the number shown in the list command.

Your tasks are automatically saved between sessions (default path: ~/bruceData/bruce.txt).

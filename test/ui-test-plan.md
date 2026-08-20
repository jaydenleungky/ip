# UI Test Plan

Manual/automated console test cases for the `Coco` chatbot. Each test case is run
independently against a fresh instance of the program (no state carries over
between test cases). The `test-ui` skill parses this file, runs the program with
each test case's input, and compares the captured console output against the
expected output verbatim.

## Test 1: Greet and exit

Aim: Verify the banner, greeting, and prompt are printed, and the program exits
cleanly (printing the goodbye message) when the user immediately types `bye`.

### Input

```
bye
```

### Expected Output

```
____________________________________________________________
  ____ ___   ____ ___  
 / ___/ _ \ / ___/ _ \ 
| |  | | | | |  | | | |
| |__| |_| | |__| |_| |
 \____\___/ \____\___/ 

Hello! I'm Coco.
What can I do for you?
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test 2: Add a todo and list it

Aim: Verify `todo <description>` adds a task with the `[T]` type marker, confirms
with the "Got it" message and running count, and `list` shows it correctly.

### Input

```
todo borrow book
list
bye
```

### Expected Output

```
____________________________________________________________
  ____ ___   ____ ___  
 / ___/ _ \ / ___/ _ \ 
| |  | | | | |  | | | |
| |__| |_| | |__| |_| |
 \____\___/ \____\___/ 

Hello! I'm Coco.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test 3: Add a deadline and an event, then list

Aim: Verify `deadline <description> /by <date>` and `event <description> /from
<start> /to <end>` add tasks with the `[D]`/`[E]` markers and their date fields,
and that `list` renders both correctly.

### Input

```
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
bye
```

### Expected Output

```
____________________________________________________________
  ____ ___   ____ ___  
 / ___/ _ \ / ___/ _ \ 
| |  | | | | |  | | | |
| |__| |_| | |__| |_| |
 \____\___/ \____\___/ 

Hello! I'm Coco.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[D][ ] return book (by: Sunday)
2.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test 4: Mark and unmark a task

Aim: Verify `mark <n>` sets a task's done status to `[X]` and `unmark <n>` reverts
it to `[ ]`, with `list` reflecting the change after each.

### Input

```
todo read book
mark 1
list
unmark 1
list
bye
```

### Expected Output

```
____________________________________________________________
  ____ ___   ____ ___  
 / ___/ _ \ / ___/ _ \ 
| |  | | | | |  | | | |
| |__| |_| | |__| |_| |
 \____\___/ \____\___/ 

Hello! I'm Coco.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] read book
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][X] read book
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] read book
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test 5: Combined regression scenario

Aim: Regression-test all three task types together in one session, mixing
additions, marks, and a `list`, plus adding a second deadline/event that share a
description with an earlier task (they should be tracked as distinct entries).

### Input

```
todo read book
mark 1
deadline return book /by June 6th
event project meeting /from Aug 6th 2pm /to 4pm
todo join sports club
mark 4
todo borrow book
list
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
bye
```

### Expected Output

```
____________________________________________________________
  ____ ___   ____ ___  
 / ___/ _ \ / ___/ _ \ 
| |  | | | | |  | | | |
| |__| |_| | |__| |_| |
 \____\___/ \____\___/ 

Hello! I'm Coco.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] read book
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: June 6th)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] join sports club
Now you have 4 tasks in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] join sports club
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] borrow book
Now you have 5 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][X] read book
2.[D][ ] return book (by: June 6th)
3.[E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
4.[T][X] join sports club
5.[T][ ] borrow book
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 6 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 7 tasks in the list.
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test 6: Todo/deadline/event validation errors

Aim: Verify empty descriptions and malformed/missing `/by`, `/from`, `/to`
markers are rejected with a specific error message instead of crashing or
silently adding a bad task.

### Input

```
todo
deadline
deadline return book
deadline return book /by
event
event project meeting
event project meeting /from Mon 2pm
bye
```

### Expected Output

```
____________________________________________________________
  ____ ___   ____ ___  
 / ___/ _ \ / ___/ _ \ 
| |  | | | | |  | | | |
| |__| |_| | |__| |_| |
 \____\___/ \____\___/ 

Hello! I'm Coco.
What can I do for you?
____________________________________________________________
____________________________________________________________
Sorry, todo description cannot be empty!
____________________________________________________________
____________________________________________________________
Sorry, a deadline needs a '/by' date! Try: deadline <description> /by <date>
____________________________________________________________
____________________________________________________________
Sorry, a deadline needs a '/by' date! Try: deadline <description> /by <date>
____________________________________________________________
____________________________________________________________
Sorry, the date for a deadline cannot be empty!
____________________________________________________________
____________________________________________________________
Sorry, an event needs '/from' and '/to'! Try: event <description> /from <start> /to <end>
____________________________________________________________
____________________________________________________________
Sorry, an event needs '/from' and '/to'! Try: event <description> /from <start> /to <end>
____________________________________________________________
____________________________________________________________
Sorry, an event needs '/from' and '/to'! Try: event <description> /from <start> /to <end>
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test 7: Unknown command

Aim: Verify input that doesn't match any known command produces an error
message instead of being silently added as a task.

### Input

```
blah
bye
```

### Expected Output

```
____________________________________________________________
  ____ ___   ____ ___  
 / ___/ _ \ / ___/ _ \ 
| |  | | | | |  | | | |
| |__| |_| | |__| |_| |
 \____\___/ \____\___/ 

Hello! I'm Coco.
What can I do for you?
____________________________________________________________
____________________________________________________________
Boy, what that mean?
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test 8: Invalid task numbers for mark/unmark

Aim: Verify `mark`/`unmark` reject an out-of-range index, a non-numeric
argument, and a missing argument, each with a specific error message, without
crashing the program.

### Input

```
todo read book
mark 5
mark abc
mark
unmark 0
bye
```

### Expected Output

```
____________________________________________________________
  ____ ___   ____ ___  
 / ___/ _ \ / ___/ _ \ 
| |  | | | | |  | | | |
| |__| |_| | |__| |_| |
 \____\___/ \____\___/ 

Hello! I'm Coco.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Sorry, there is no task number 5!
____________________________________________________________
____________________________________________________________
Sorry, 'abc' is not a valid task number!
____________________________________________________________
____________________________________________________________
Sorry, tell me which task number to mark!
____________________________________________________________
____________________________________________________________
Sorry, there is no task number 0!
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test 9: Interleaved valid/invalid commands preserve correct state

Aim: Verify that failed operations (empty descriptions, malformed
deadline/event, an unknown command, and mark/unmark at exactly
`taskCount + 1`) never corrupt internal state — no phantom tasks are added,
the task count and done-status of existing tasks stay correct, and a valid
command immediately after an invalid one still targets the right task.
This specifically checks the boundary case of marking/unmarking the task
number one past the end of the list, which a generic "large out-of-range
number" test case would not catch.

### Input

```
todo read book
todo
list
mark 2
list
mark 1
unmark 2
list
deadline return book /by June 6th
deadline return book /by
event meeting /from Mon 2pm /to 4pm
event meeting /from Mon 2pm
blah
list
mark 4
unmark 1
list
bye
```

### Expected Output

```
____________________________________________________________
  ____ ___   ____ ___  
 / ___/ _ \ / ___/ _ \ 
| |  | | | | |  | | | |
| |__| |_| | |__| |_| |
 \____\___/ \____\___/ 

Hello! I'm Coco.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Sorry, todo description cannot be empty!
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
____________________________________________________________
____________________________________________________________
Sorry, there is no task number 2!
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] read book
____________________________________________________________
____________________________________________________________
Sorry, there is no task number 2!
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][X] read book
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: June 6th)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Sorry, the date for a deadline cannot be empty!
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Sorry, an event needs '/from' and '/to'! Try: event <description> /from <start> /to <end>
____________________________________________________________
____________________________________________________________
Boy, what that mean?
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][X] read book
2.[D][ ] return book (by: June 6th)
3.[E][ ] meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Sorry, there is no task number 4!
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] read book
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: June 6th)
3.[E][ ] meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

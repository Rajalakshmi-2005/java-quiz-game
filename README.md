# Java Quiz Game

A console-based multiple-choice quiz game built in Java.

## Features
- Loads questions from a text file, so questions can be added without changing the code
- Shuffles the questions every round
- Shows the correct answer when you get one wrong
- Validates input and handles invalid entries
- Saves the high score to a file
- Play-again option

## Concepts used
- Object-oriented programming: the Question class with encapsulation
- ArrayList and Collections.shuffle
- File handling with java.nio.file (reading questions, saving the high score)
- Exception handling (try/catch)
- Methods and loops (for, do-while)

## Question file format
Each line in `src/questions.txt` is one question, separated by `|`:

`Question text|Option 1|Option 2|Option 3|Option 4|Correct option number`

## How to run
1. Install JDK 21 or later
2. Clone this repository
3. From the project folder, compile: `javac -d out src/*.java`
4. Run: `java -cp out Main`

## Planned improvements
- Question categories and difficulty levels
- A timer for each question

## Author
Rajalakshmi S, Electronics and Communication Engineering
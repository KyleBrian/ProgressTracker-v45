package ui;

import model.ListOfSubjects;
import model.Subject;
import persistence.Reader;
import ui.tools.ListOfSubjectTool;
import ui.tools.SubjectTool;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;


// User interface for the ProgressTracker.

//** I had the idea of the ProgressTracker last term when I took this course, but only wrote < 10 lines of code.
//      I withdrew from the course and am now using this idea again using brand new code.
//      HOWEVER, some classes will inevitably have the same name; I got the OK from the instructor about this.

public class ProgressTracker extends JFrame {

    private ListOfSubjects listOfSubjects;
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 700;

    private JPanel mainPanel;
    private JPanel subjectPanel;
    private JPanel cardPanel;
    private CardLayout cl;
    private Subject currentSubject;

    private ListOfSubjectTool losTool;
    private SubjectTool subjectTool;

    private static final String DATA_FILE = "data/ProgressTracker.json";

    public ProgressTracker() {
        super("ProgressTracker");
        setLayout(new BorderLayout());
        setSize(FRAME_WIDTH, FRAME_HEIGHT);

        mainPanel = new JPanel();
        subjectPanel = new JPanel();
        cardPanel = new JPanel();
        currentSubject = new Subject("placeHolder");
        cl = new CardLayout();

        loadDialogue();

        setMainPanel();
        setSubjectPanel();
        setCardLayout();


        JLabel title = new JLabel("ProgressTracker");
        add(title);
        add(cardPanel);
        pack();
        setVisible(true);
    }

    private void setCardLayout() {
        cardPanel.setLayout(cl);
        cardPanel.add(mainPanel, "1");
        cardPanel.add(subjectPanel, "2");
        cl.show(cardPanel, "1");
    }


    public void setMainPanel() {
        mainPanel.setLayout(new BorderLayout());
        JLabel subjects = new JLabel("Subjects");
        ListOfSubjectTool listOfSubjectTool = new ListOfSubjectTool(this, listOfSubjects);
        mainPanel.add(subjects, BorderLayout.PAGE_START);
        mainPanel.add(listOfSubjectTool, BorderLayout.CENTER);
        mainPanel.add(addAndDeletePanel(), BorderLayout.PAGE_END);
    }


    public void setSubjectPanel() {
        subjectPanel.setLayout(new BorderLayout());
        SubjectTool subjectTool = new SubjectTool(this, currentSubject);
        subjectPanel.add(subjectTool);
    }


    private JPanel addAndDeletePanel() {
        JPanel addAndDeletePanel = new JPanel();
        addAndDeletePanel.setSize(600, 100);
        addAndDeletePanel.add(addSubjectPanel());
        addAndDeletePanel.add(deleteSubjectPanel());
        return addAndDeletePanel;
    }

    public JPanel addSubjectPanel() {
        JPanel addPanel = new JPanel();
        JLabel add = new JLabel("Add a subject:");
        JTextField addText = new JTextField();
        addText.setSize(100, 80);
        addText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newSubject = addText.getText();
                listOfSubjects.addSubject(new Subject(newSubject));
            }
        });
        addPanel.add(add);
        addPanel.add(addText);
        return addPanel;
    }

    public JPanel deleteSubjectPanel() {
        JPanel deletePanel = new JPanel();
        JLabel delete = new JLabel("Delete a subject:");
        JTextField deleteText = new JTextField();
        deleteText.setSize(100, 20);
        deleteText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newSubject = deleteText.getText();
                for (Subject subject : listOfSubjects.getListOfSubjects()) {
                    if (newSubject.equals(subject.getName())) {
                        listOfSubjects.getListOfSubjects().remove(subject);
                    }
                }
            }
        });
        deletePanel.add(delete);
        deletePanel.add(deleteText);
        return deletePanel;
    }


    public void loadDialogue() {
        int input = JOptionPane.showConfirmDialog(null, "Load from file?", "Load", JOptionPane.YES_NO_OPTION);
        if (input == 0) {           // Yes option
            try {
                listOfSubjects = Reader.reader(DATA_FILE);
            } catch (IOException e) {
                listOfSubjects = new ListOfSubjects();
            }
        } else if (input == 1) {
            listOfSubjects = new ListOfSubjects();
        }
    }

    public CardLayout getCardLayout() {
        return cl;
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getSubjectPanel() {
        return subjectPanel;
    }

    public Subject getCurrentSubject() {
        return currentSubject;
    }

    public void setCurrentSubject(Subject subject) {
        this.currentSubject = subject;
    }
}
//    private Scanner input;
//    private ListOfSubjects listOfSubjects;
//    private static final String DATA_FILE = "data/ProgressTracker.json";

//    public ProgressTracker() {
//        runApp();


//    private void runApp() {         // based on TellerApp ui class
//        boolean keepGoing = true;
//        String command = null;
//        input = new Scanner(System.in);
//
//        System.out.println("Track your progress with the ProgressTracker!");
//
//        loadOption();
//
//        while (keepGoing) {
//
//            displayMenu(); // from TellerApp
//            command = input.nextLine();
//            if (command.equals("q")) {
//                saveOption();
//                keepGoing = false;
//            } else {
//                processCommand(command);
//            }
//        }
//        System.out.println("\nSee you next time!");
//    }
//
//    public void saveOption() {
//        String command = "";             // from TellerApp
//        while (!((command.equals("y") || (command.equals("n"))))) {
//            System.out.println("Save to file?");
//            System.out.println("y -> yes");
//            System.out.println("n -> no");
//            command = input.nextLine();
//            if (command.equals("y")) {
//                try {
//                    Saver.saveListOfSubject(listOfSubjects, DATA_FILE);
//                    return;
//                } catch (IOException e) {
//                    System.out.println("File doesn't exist");
//                    return;
//                }
//            }
//            if (command.equals("n")) {
//                return;
//
//
//            }
//        }
//    }
//
//    public void loadOption() {
//        String command = "";             // from TellerApp
//        while (!((command.equals("y") || (command.equals("n"))))) {
//            System.out.println("Load from file?");
//            System.out.println("y -> yes");
//            System.out.println("n -> no");
//            command = input.nextLine();
//            if (command.equals("y")) {
//                try {
//                    listOfSubjects = Reader.reader(DATA_FILE);
//                    return;
//                } catch (IOException e) {
//                    System.out.println("Nothing in file");
//                    init();
//                    return;
//                }
//            }
//            if (command.equals("n")) {
//                init();
//                return;
//            }
//        }
//    }
//
//
//    public void init() {
//        System.out.println("Initializing..");
//        listOfSubjects = new ListOfSubjects();
//    }
//
//    private void processCommand(String command) {
//        if (command.equals("a")) {
//            addNewSubject();
//            return;
//        } else if (command.equals("r")) {
//            removeSubject();
//            return;
//        } else {
//            for (Subject next : listOfSubjects.getListOfSubjects()) {
//                if (command.equals(next.getName())) {
//                    viewSubject(next);
//                    return;
//                }
//            }
//        }
//        System.out.println("Enter valid command!");
//    }
//
//    private void removeSubject() {
//        if (listOfSubjects.isEmpty()) {
//            System.out.println("No subjects yet!");
//        } else {
//            String command = "";
//            while (!((command.equals("q")))) {
//                System.out.println("Type name of subject to be removed:");
//                System.out.println("q - > back");
//                command = input.nextLine();
//
//                for (Subject next : listOfSubjects.getListOfSubjects()) {
//                    if (command.equals(next.getName())) {
//                        listOfSubjects.getListOfSubjects().remove(next);
//                        System.out.println("Removed " + next.getName() + "!");
//                        return;
//                    }
//                }
//                System.out.println(command + " not found!");
//            }
//        }
//    }
//
//    private void viewSubject(Subject subject) {
//        Boolean keepInView = true;
//        while (keepInView) {
//            displaySubject(subject);
//            String command = "";             // from TellerApp
//            while (!((command.equals("s") || (command.equals("u")) || (command.equals("q"))))) {
//                System.out.println("s -> Set a goal");
//                System.out.println("u -> Post an update");
//                System.out.println("q -> Back");
//                command = input.nextLine();
//            }
//
//            if (command.equals("s")) {
//                goalSetter(subject);
//            } else if (command.equals("u")) {
//                postUpdate(subject);
//            } else {
//                keepInView = false;
//            }
//        }
//    }
//
//    private void displaySubject(Subject subject) {
//        System.out.println(subject.getName());
//        System.out.println("Current Goal: " + subject.getBigGoal() + "\n");
//        for (Update update : subject.getUpdateLog()) {
//            System.out.println(update.getReport() + "\t||\t" + update.getNextGoal());
//        }
//    }
//
//    private void postUpdate(Subject subject) {
//        System.out.println("Description of update:");
//        String description = input.nextLine();
//
//        System.out.println("Goal for next update:");
//        String nextGoal = input.nextLine();
//
//        Update newUpdate = new Update(description, nextGoal);
//        subject.addUpdate(newUpdate);
//        System.out.println("Update added!");
//    }
//
//
//    private void goalSetter(Subject subject) {
//        System.out.println("Set a goal:");
//        String goal = input.nextLine();
//        subject.setBigGoal(goal);
//        System.out.println("Goal set!");
//    }
//
//    private void addNewSubject() {
//        System.out.println("Choose name of subject:");
//        String name = input.nextLine();
//        Subject newSubject = new Subject(name);
//        listOfSubjects.addSubject(newSubject);
//        System.out.println(name + " added!");
//        viewSubject(newSubject);
//    }
//
//    private void displayMenu() {
//        if (listOfSubjects == null) {
//            init();
//        } else if (listOfSubjects.isEmpty()) {
//            System.out.println("Nothing added yet");
//        } else {
//            for (Subject next : listOfSubjects.getListOfSubjects()) {
//                System.out.println(next.getName());
//            }
//        }
//
//        System.out.println("\nType subject name -> View subject");
//        System.out.println("a -> Add subject");
//        System.out.println("r -> Remove subject");
//        System.out.println("q -> Exit");
//    }
// package tuneup;

// import java.util.Scanner;

// /**
//  * Handles terminal-based user interface for the TuneUp application
//  *  
//  * @author edwinjwood
//  * @author allanpaiz - added main method / javadoc
//  */
// public class TerminalUI {
//     private TuneUp facade;
//     private Scanner scanner;
    
//     /**
//      * Constructor
//      * 
//      * @param facade The TuneUp facade that provides access to the application's functionality
//      */
//     public TerminalUI(TuneUp facade) {
//         this.facade = facade;
//         this.scanner = new Scanner(System.in);
//     }
    
//     /**
//      * Starts the terminal-based UI
//      */
//     public void run() {
//         User currentUser = null;
//         boolean running = true;

//         System.out.println("\nWelcome to TuneUp Terminal Mode");

//         while (running) {
//             if (currentUser == null) {
//                 // Handle authentication menu
//                 int authChoice = displayAuthMenu();
//                 switch (authChoice) {
//                     case 1: // Login
//                         currentUser = facade.login(scanner);
//                         break;
//                     case 2: // Register
//                         currentUser = facade.register(scanner);
//                         break;
//                     case 3: // Exit
//                         running = false;
//                         break;
//                     default:
//                         System.out.println("Invalid choice. Please try again.");
//                 }
//             } else {
//                 // Handle main application menu
//                 int mainChoice = displayMainMenu(currentUser);
//                 switch (mainChoice) {
//                     case 1: // Play Mode
//                         activatePlayMode(currentUser);
//                         break;
//                     case 2: // Song Library
//                         activateSongLibrary(currentUser);
//                         break;
//                     case 3: // Lesson Mode
//                         activateLessonMode(currentUser);
//                         break;
//                     case 4: // Create Mode
//                         activateCreateMode(currentUser);
//                         break;
//                     case 5: // View Profile
//                         viewProfile(currentUser);
//                         break;
//                     case 6: // List Students
//                         listStudents(currentUser);
//                         break;
//                     case 7: // Logout
//                         currentUser = null;
//                         System.out.println("Logged out successfully.");
//                         break;
//                     case 8: // Exit
//                         running = false;
//                         break;
//                     default:
//                         System.out.println("Invalid choice. Please try again.");
//                 }
//             }
//         }
        
//         System.out.println("Thank you for using TuneUp Terminal Mode. Goodbye!");
//         scanner.close();
//     }
    
//     /**
//      * Display authentication menu and get user choice
//      * 
//      * @return int choice
//      */
//     private int displayAuthMenu() {
//         System.out.println("\n1. Login");
//         System.out.println("2. Register");
//         System.out.println("3. Exit");
//         System.out.print("Choose an option: ");
        
//         try {
//             int choice = scanner.nextInt();
//             scanner.nextLine();
//             return choice;
//         } catch (Exception e) {
//             System.out.println("Please enter a valid number.");
//             scanner.nextLine();
//             return 0; // Invalid choice
//         }
//     }
    
//     /**
//      * Display main application menu and get user choice
//      * 
//      * @param currentUser User - the logged in user
//      * @return int choice
//      */
//     private int displayMainMenu(User currentUser) {
//         System.out.println("\nLogged in as: " + currentUser.getUsername() + " (" + currentUser.getRole() + ")");
//         System.out.println("=== Main Menu ===");
//         System.out.println("1. Play Mode");
//         System.out.println("2. Song Library");
//         System.out.println("3. Lesson Mode");
//         System.out.println("4. Create Mode");
//         System.out.println("5. View Profile");
//         System.out.println("6. List Students");
//         System.out.println("7. Logout");
//         System.out.println("8. Exit");
//         System.out.print("Choose an option: ");
        
//         try {
//             int choice = scanner.nextInt();
//             scanner.nextLine();
//             return choice;
//         } catch (Exception e) {
//             System.out.println("Please enter a valid number.");
//             scanner.nextLine();
//             return 0; // Invalid choice
//         }
//     }
    
//     /**
//      * Activate Play Mode
//      * 
//      * @param currentUser User
//      */
//     private void activatePlayMode(User currentUser) {
//         Mode playMode = new PlayMode(currentUser, facade);
//         playMode.handleTerminal(scanner);
//     }
    
//     /**
//      * Activate Song Library
//      * 
//      * @param currentUser User
//      */
//     private void activateSongLibrary(User currentUser) {
//         Mode songLibraryMode = new SongLibraryMode(currentUser, facade);
//         songLibraryMode.handleTerminal(scanner);
//     }
    
//     /**
//      * Activate Lesson Mode
//      * 
//      * @param currentUser User
//      */
//     private void activateLessonMode(User currentUser) {
//         Mode lessonMode = new LessonMode(currentUser, facade);
//         lessonMode.handleTerminal(scanner);
//     }
    
//     /**
//      * Activate Create Mode
//      * 
//      * @param currentUser User
//      */
//     private void activateCreateMode(User currentUser) {
//         Mode createMode = new CreateMode(currentUser, facade);
//         createMode.handleTerminal(scanner);
//     }
    
//     /**
//      * View user profile
//      * 
//      * @param currentUser User
//      */
//     private void viewProfile(User currentUser) {
//         String profileInfo = facade.getProfileDisplay(currentUser);
//         System.out.println(profileInfo);
        
//         System.out.println("\nPress Enter to return to the main menu...");
//         scanner.nextLine();
//     }
    
//     /**
//      * List students (teacher only)
//      * 
//      * @param currentUser User
//      */
//     private void listStudents(User currentUser) {
//         facade.displayStudentList(currentUser, scanner);
//     }

//     /**
//      * Main method - entry point of the application
//      */
//     public static void main(String[] args) {
//         // Create the facade
//         TuneUp tuneUp = new TuneUp();
        
//         // Create the UI and run
//         TerminalUI terminalUI = new TerminalUI(tuneUp);
//         terminalUI.run();
//     }
// }
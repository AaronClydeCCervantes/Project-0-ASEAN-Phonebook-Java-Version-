import java.util.Scanner;

public class Main
{

    private static final String[][] MENUS = { {
            // Main Menu
            "Store to ASEAN phonebook", "Edit entry in ASEAN phonebook", "Delete entry from ASEAN phonebook", "View/search ASEAN phonebook", "Exit" },
            {
                    // Store to ASEAN phonebook Menu
                    "Student number", "Surname", "Gender", "Occupation", "Country Code",
                    "Area Code", "Phone Number", "None - Go back to Main Menu" },
            {
                    // Menu for View Phonebook
                    "View All", "View Contact through ID", "View Contacts through Country Code",
                    "Go back to Main Menu" },
            {
                    // Country Codes
                    "Burma", "Cambodia", "Thailand", "Vietnam", "Malaysia", "Philippines",
                    "Indonesia", "Timor Leste", "Laos", "Brunei" ,"All", "No More"}, };

    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args)
    {
        Phonebook pb = new Phonebook();
        boolean exit = false;
        while (true)
        {
            showMenu(1, 1);
            // System.out.print("Select an option: ");
            // int opt = input.nextInt();
            int opt = Integer.parseInt(prompt("Select an option: "));
            switch (opt)
            {
                case 1:
                    while (true) {
                        pb.insert(createNewPerson());

                        String another = prompt("Do you want to enter another entry [Y/N]? ").trim().toUpperCase();
                        if (!another.equals("Y")) {
                            System.out.println("Returning to main menu...");
                            break;
                        }
                    }
                    break;
                case 2:
                    // EDIT METHOD
                    String id = prompt("Enter student number: ");
                    Person p = pb.getContact(id);

                    if (p == null) {
                        System.out.println("Error: Contact ID does not exist.");
                    } else {
                        while (true) {
                            System.out.println("\nHere is the existing information about " + id + ":");
                            System.out.println(p);
                            System.out.println("Which of the following information do you wish to change?");
                            showMenu(2, 4);

                            int choice = Integer.parseInt(prompt("Enter choice: "));

                            switch (choice) {
                                case 1:
                                    String newContactId = prompt("Enter new student number: ");
                                    p.setId(newContactId);
                                    break;
                                case 2:
                                    p.setLName(prompt("Enter new surname: "));
                                    break;
                                case 3:
                                    p.setSex(prompt("Enter new gender(M for male, F for female): "));
                                    break;
                                case 4:
                                    p.setOccupation(prompt("Enter new occupation: "));
                                    break;
                                case 5:
                                    p.setCountryCode(Integer.parseInt(prompt("Enter new country code: ")));
                                    break;
                                case 6:
                                    p.setAreaCode(Integer.parseInt(prompt("Enter new area code: ")));
                                    break;
                                case 7:
                                    p.setContactNum(prompt("Enter new phone number: "));
                                    break;
                                case 8:
                                    System.out.println("Returning to main menu...");
                                    break; // Correctly exits the loop
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }

                            if (choice == 8) {
                                break; // Ensure the loop exits when returning to the main menu
                            }
                        }
                    }
                    break;
                case 3:
                    id = prompt("Enter student number: ");
                    p = pb.getContact(id);
                    if (p == null) {
                        System.out.println("Error: Contact ID does not exist!");
                    } else {
                        String confirmation = prompt("Are you sure you want to delete it [Y/N]? ").trim().toUpperCase();

                        if (confirmation.equals("Y")) {
                            Person deletedContact = pb.deleteContact(id);
                            if (deletedContact != null) {
                                System.out.println("Deletion successful.");
                            }
                        } else {
                            System.out.println("Deletion did not proceed.");
                        }
                    }
                    break;
                case 4:
                    while (true)
                    {
                        showMenu(3, 1);
                        int showOpt = Integer.parseInt(prompt("Enter option:"));
                        if (showOpt == 1)
                        {
                            System.out.println(pb);
                        }
                        else if (showOpt == 2)
                        {
                            String targetId = prompt("Enter id to search: ");
                            Person target = pb.getContact(targetId);
                            if (target != null)
                            {
                                System.out.println(target);
                            }
                            else
                            {
                                System.out.println("No contact exists with that surname!");
                            }
                        }
                        else if (showOpt == 3)
                        {
                            int ccCount = 0;
                            int[] countryCodes = new int[11];
                            showMenu(4, 5);
                            while (true)
                            {
                                int countryCode = Integer.parseInt(prompt("\nEnter Country Code: "));
                                // Print if input is 0
                                if (countryCode == 0)
                                {
                                    pb.printContactsFromCountryCodes(countryCodes);
                                    break;
                                }
                                // Check if area code is already inputted
                                boolean exists = false;
                                for (int a : countryCodes)
                                {
                                    if (a == countryCode)
                                    {
                                        System.out.println(
                                                "This area code has already been inputted!");
                                        exists = true;
                                        break;
                                    }
                                }
                                // Only add if area codes isn't part of the array...
                                if (!exists)
                                {
                                    countryCodes[ccCount] = countryCode;
                                    ccCount++;
                                }

                            }
                        }
                        else if (showOpt == 4)
                        {
                            break;
                        }
                    }
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option!");
            }
            if (exit)
                break;
        }
    }

    /**
     * Show menu based on given index. <br>
     * <br>
     * 1 for Main Menu. <br>
     * <br>
     * 2 for Edit Contact Menu. <br>
     * <br>
     * 3 for View Phonebook Menu. <br>
     * <br>
     * 4 for Country Code Menu.
     *
     * @param menuIdx Index of the menu to be shown.
     * @param inlineTexts Number of menu options to be printed in a single line. Set to 1 if you
     *        want every line to only have one menu option.
     */
    private static void showMenu(int menuIdx, int inlineTexts)
    {
        String[] menu = MENUS[menuIdx - 1];
        int count = 0;
        String space = inlineTexts == 0 ? "" : "%-12s";
        String fmt = "[%d] " + space;
        for (int i = 0; i < menu.length; i++)
        {
            System.out.printf(fmt, i + 1, menu[i]);
            if (inlineTexts != 0)
            {
                count += 1;
            }
            if (count % inlineTexts == 0)
            {
                System.out.print("\n");
            }
        }
    }

    /**
     * Convert choices from the menu into their appropriate country code values.
     *
     * @return Country code value of the menu choice.
     */
    public static int convertChoices(int choice)
    {
        // Complete this method.
        return 0;
    }

    /**
     * Create a new person object using a slightly complicated setup.
     *
     * @return Newly created person object.
     */
    private static Person createNewPerson()
    {
        String id, fname, lname, sex, occupation, contactNum;
        int countryCode, areaCode;
        id = prompt("Enter student number: ");
        fname = prompt("Enter first name: ");
        lname = prompt("Enter last name: ");
        occupation = prompt("Enter occupation: ");
        sex = prompt("Enter gender(M for male, F for female): ");
        countryCode = Integer.parseInt(prompt("Enter country code: "));
        areaCode = Integer.parseInt(prompt("Enter area code: "));
        contactNum = prompt("Enter contact number: ");
        return new Person(id, fname, lname, sex, occupation, contactNum, countryCode, areaCode);
    }

    /**
     * Receive prompt and return the inputted value back to the variable or process that requires
     * it. Data type is String. Do not forget to type cast if possible.
     *
     * @param phrase Phrase to be given to user when requiring input.
     * @return Returns the data needed.
     */
    private static String prompt(String phrase)
    {
        System.out.print(phrase);
        return input.nextLine();
    }
}

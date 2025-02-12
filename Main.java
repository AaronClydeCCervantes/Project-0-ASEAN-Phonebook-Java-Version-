import java.util.Scanner;

public class Main
{

    private static final String[][] MENUS = { {
            // Main Menu
            "Add New Contact", "Edit Contact", "Delete Contact", "View Phonebook", "Exit" },
            {
                    // Edit Contact Menu
                    "Student Number", "First Name", "Last Name", "Occupation", "Country Code",
                    "Area Code", "Phone Number", "None - Go back to Main Menu" },
            {
                    // Menu for View Phonebook
                    "View All", "View Contact through ID", "View Contacts through Country Code",
                    "Go back to Main Menu" }, };
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
                    pb.insert(createNewPerson());
                    break;
                    
                case 2:

                        String eid = prompt("Enter Contact ID to edit: ");
                        Person ep = pb.getContact(eid);
                        if (ep != null) {

                            System.out.printf("Here is the Existing Contact about %s \n %s \n" , eid , ep);
                            showMenu(2, 2);
//                          
                            //int eopt = Integer.parseInt(prompt("Select Which Information to Edit: "));
//                           // if (eopt == 1) {
//                            //    Person.setId(Scanner.nextLine());
//                             //   break;
//                           // } else if (eopt == 2) {
//                                break;
//                          //  }

                            int opt = Integer.parseInt(prompt("Select Which Information to edit"));

                            switch (opt){

                                case 1://edits ID
                                    String newId = prompt("Enter new ID: ");
                                    ep.setId(newId);  // Set new ID using the setter
                                    break;

                                case 2://edits first name
                                    String newFirstName = prompt("Enter new First Name: ");
                                    ep.setFirstName(newFirstName);  // Set new First Name
                                    break;

                                case 3://edits lastname
                                    String newLastName = prompt("Enter new Last Name: ");
                                    ep.setLastName(newLastName);  // Set new Last Name
                                    break;
  
                                case 4://edits occupation
                                    String newOccupation = prompt("Enter new Occupation: ");
                                    ep.setOccupation(newOccupation);  // Set new Occupation
                                    break;

                                case 5://edits country code
                                    int newCountryCode = Integer.parseInt(prompt("Enter new Country Code: "));
                                    ep.setCountryCode(newCountryCode);  // Set new Country Code
                                    break;

                                case 6://edits the area code
                                    int newAreaCode = Integer.parseInt(prompt("Enter new Area Code: "));
                                    ep.setAreaCode(newAreaCode);  // Set new Area Code
                                    break;

                                case 7: //edits the contact number
                                    String newContactNum = prompt("Enter new Phone Number: ");
                                    ep.setContactNum(newContactNum);  // Set new Phone Number
                                    break;

                                case 8: //Return to Main Menu
                                    break;

                                default: //In case User inputs a number not in the given choices
                                    System.out.println("Invalid Option");
                                    break;
                            }

                            

                    }
                    break;
                    
                case 3:
                    String id = prompt("Enter contact ID to delete: ");
                    Person p = pb.getContact(id);
                    if (p != null)
                    {
                        Person deletedContact = pb.deleteContact(id);
                        if (deletedContact != null)
                        {
                            System.out.println("Contact has been successfully deleted!");
                        }
                    }
                    else
                    {
                        System.out.println("This contact does not exist!");
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
                            int[] countryCodes = new int[9];
                            while (true)
                            {
                                int countryCode = Integer.parseInt("Enter Country Code: ");
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
    private int convertChoices(int choice)
    {
        // Complete this method.
        switch (choice) {
            case 1: // Federation of Malaysia
                return 60;
            case 2: // Republic of Indonesia
                return 62;
            case 3: // Republic of the Philippines
                return 63;
            case 4: // Republic of Singapore
                return 65;
            case 5: // Kingdom of Thailand
                return 66;
            case 6: // Socialist Republic of Vietnam
                return 84;
            case 7: // Brunei Darussalam
                return 673;
            case 8: // Kingdom of Cambodia
                return 855;
            case 9: // Lao People's Democratic Republic
                return 856;
            case 10: // Republic of the Union of Myanmar
                return 95;
            case 11: // Democratic Republic of Timor Leste
                return 670;
            default: // Invalid choice, returning -1 as an error code
                return -1;
                }
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
        id = prompt("Enter Contact ID: ");
        fname = prompt("Enter First Name: ");
        lname = prompt("Enter Last Name: ");
        occupation = prompt("Enter Occupation: ");
        sex = prompt("Enter sex/gender: ");
        countryCode = Integer.parseInt(prompt("Enter Country Code: "));
        areaCode = Integer.parseInt(prompt("Enter Area Code: "));
        contactNum = prompt("Enter Contact Number: ");
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

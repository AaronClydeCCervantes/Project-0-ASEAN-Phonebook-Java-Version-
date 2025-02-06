public class Phonebook
{
    // Storage of contacts.
    private Person[] contacts;
    // Number of contacts present in the phonebook.
    private int size;

    /**
     * Create a phonebook of size 50.
     */
    public Phonebook()
    {
        contacts = new Person[50];
    }

    /**
     * @return Number of contacts stored in this phonebook.
     */
    public int getSize()
    {
        // Complete this method
        return size;
    }

    /**
     * Get the contact at index.
     * 
     * @param index Index to get contact.
     * @return Person object from index. Null if index is not valid or out of range.
     */
    public Person getContactAtIndex(int index) {
        // Complete this method
        // Check if the index is out of range
        if (index < 0 || index >= size) {
            return null;
        }
        // Return the contact at the valid index
        return contacts[index];
    }
    /**
     * Get the person object based on a given id.
     * 
     * @param id Target id.
     * @return Person object that has this id. Null if it does not exist.
     */
    public Person getContact(String id)
    {
        // Complete this method
        if (id == null) {
            return null; // Check if the id is null
        }
        // Iterate through the contacts to find a matching ID
        for (int i = 0; i < size; i++) {
            if (contacts[i] != null && contacts[i].getId().equals(id)) {
                return contacts[i]; // Return the matching person
            }
        }
        return null;
    }

    /**
     * Checks if this phonebook has contacts or not.
     * 
     * @return True or False.
     */
    public boolean isEmpty()
    {
        return this.getSize() == 0;
    }

    /**
     * Increase number of contacts present in this phonebook.
     */
    public void incrSize()
    {
        this.size++;
    }

    /**
     * Decrease number of contacts present in this phonebook.
     */
    public void decrSize()
    {
        this.size--;
    }

    /**
     * Increases the size of the phonebook whenever it is full.
     */
    private void increasePhonebookMaxSize()
    {
        // Complete this method
        Person[] newContacts = new Person[contacts.length * 2];  //Create a new array with double the size of the current one
        // Copy all the existing contacts to the new array
        if (size >= 0) System.arraycopy(contacts, 0, newContacts, 0, size);
        contacts = newContacts; // Update the contacts reference to the new array
    }

    /**
     * Inserts a new person object at its appropriate lexicographic location in the phonebook.
     *
     * @param p Person to be added to the Phonebook.
     */
    public void insert(Person p)
    {
        // Complete this method
        if (size == contacts.length) {
            increasePhonebookMaxSize(); // If the phonebook is full, increase its size
        }
        int index = findIndexInsertion(p); // Find the appropriate index for the new person
        for (int i = size; i > index; i--) {
            contacts[i] = contacts[i - 1]; // Shift elements to the right to make space for the new person
        }
        contacts[index] = p; // Insert the new person at the found index
        incrSize();
    }

    /**
     * Searches in what index should this person object with the given be inserted.
     * 
     * @param p Person object to be inserted into the phonebook.
     * @return Appropriate index (position).
     */
    private int findIndexInsertion(Person p)
    {
        // Complete this method
        int left = 0;
        int right = size - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2; // Locate the middle index
            int compare = contacts[mid].compareTo(p);

            if (compare == 0) {
                return mid; // Exact match found
            }
            if (compare < 0) {
                left = mid + 1; // Search the right half
            } else {
                right = mid - 1; // Search the left half
            }
        }
        return left; // The index where the person should be inserted
    }

    /**
     * Delete a person based on their contact id.
     * 
     * @param id Contact ID of that contact.
     * @return Deleted contact.
     */
    public Person deleteContact(String id)
    {
        // Complete this method...
        for (int i = 0; i < size; i++) { // Find the index of the contact to be deleted by its ID
            if (contacts[i] != null && contacts[i].getId().equals(id)) { // If we find the contact with the matching ID
                Person p = contacts[i]; // Store the deleted contact

                // Shift the elements to the left to fill the gap
                for (int j = i + 1; j < size; j++) {
                    contacts[j - 1] = contacts[j];
                }

                contacts[size - 1] = null; // Nullify the last element since it's now an extra
                decrSize();
                return p; // Return the deleted contact
            }
        }
        return null;
    }

    /**
     * Adjusts the existing contacts in a phonebook from a given starting index to where it ends,
     * following a particular direction.
     * 
     * @param start Index to start adjustment from.
     * @param end Index to end adjustment into.
     * @param direction Direction in which the adjustment must be made. direction = "f" if element
     *        at index 0 takes the value of the element next to it (e.g. index 1). direction = "b"
     *        if element at index 1 takes the value of the element behind it (e.g. index 0).
     */
    private void adjustPhonebook(int start, int end, String direction)
    {
        // Complete this method...
        if (direction.equals("forward")) { // Shifting elements forward
            for (int i = end; i >= start; i--) { // Start from end to avoid overwriting elements
                if (i + 1 < contacts.length) { // Ensure we are within array bounds
                    contacts[i + 1] = contacts[i]; // Shift element forward
                }
            }
            contacts[start] = null; // Set the element at start to null after shifting
        }
        else if (direction.equals("backward")) { // Shifting elements backward
            for (int i = start; i <= end; i++) { // Start from start to avoid overwriting elements
                if (i - 1 >= 0) { // Ensure we are within array bounds
                    contacts[i - 1] = contacts[i]; // Shift element backward
                }
            }
            contacts[end] = null; // Set the element at end to null after shifting
        }

    }

    /**
     * Uses ellipsis to ambiguously accept as many country codes as possible. <br>
     * <br>
     * For example: <br>
     * <br>
     * If we have: printContactsFromCountryCodes(1, 2, 3) <br>
     * <br>
     * Then we get: countryCodes = { 1, 2, 3 };
     * 
     * @param countryCodes Area codes to be used as a filter.
     * @return Contacts on this phonebook under a particular area code set by the user.
     */
    public String printContactsFromCountryCodes(int... countryCodes)
    {
        // Complete this method.
        String result = ""; // Initialize the result string.

        // Iterate through all the contacts in the phonebook.
        for (int i = 0; i < size; i++) {
            if (contacts[i] != null) { // Make sure the contact is not null.
                int contactCountryCode = contacts[i].getCountryCode(); // Assuming each contact has a getCountryCode() method.

                // Check if the contact's country code is in the provided countryCodes array.
                for (int countryCode : countryCodes) {
                    if (contactCountryCode == countryCode) {
                        result += contacts[i].toString() + "\n"; // Append the contact to the result string.
                        break; // Stop checking further country codes for this contact.
                    }
                }
            }
        }
        if (result.isEmpty()) {
            return "No contacts found for the given country codes."; // If no contacts were found, return a message indicating so.
        }
        return result; // Return the final result containing matching contacts.
    }

    /**
     * Print the entire phonebook without any filter or so...
     * 
     * @return The entire list of contacts present in this phonebook.
     */
    public String toString() {
        // Complete this method.
        String result = ""; // Initialize the result string.
        if (isEmpty()) {
            return "Phonebook is empty."; // If there are no contacts, return a suitable message.
        }

        // Iterate over all contacts
        for (int i = 0; i < size; i++) {
            if (contacts[i] != null) { // If the contact is not null, append its string representation
                result += contacts[i].toString(); // Concatenate the contact's string to result
            }
        }
        return result; // Return the concatenated string
    }
}
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.example.AddressBook;
import org.example.AddressEntry;

public class AddressBookTest {
    
    private AddressBook addressBook;

    @Before
    public void setUp() {
        addressBook = new AddressBook();
    }


    @Test
    public void testLoad() {

        String testFileContent = "John,Doe,123 Main St,City,State,12345,john@example.com,123-456-7890\n" +
                "Jane,Doe,456 Oak St,Town,State,56789,jane@example.com,987-654-3210";


        InputStream originalIn = System.in;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(testFileContent.getBytes());
            System.setIn(in);

            addressBook.load("test.txt");

            ArrayList<AddressEntry> entries = addressBook.getEntries();
            assertEquals(2, entries.size());
            assertEquals("John", entries.get(0).getFirstName());
            assertEquals("Doe", entries.get(0).getLastName());
            assertEquals("456 Oak St", entries.get(1).getStreet());
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    public void testAddAddressBook() {
        // Mock the input stream with sample user input
        String userInput = "John\nDoe\n123 Main St\nCity\nState\n12345\njohn@example.com\n123-456-7890\n";
        InputStream originalIn = System.in;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
            System.setIn(in);

            // Call the addAddressBook method
            addressBook.addAddressBook();

            // Get the entries and verify
            ArrayList<AddressEntry> entries = addressBook.getEntries();
            assertEquals(1, entries.size());
            assertEquals("John", entries.get(0).getFirstName());
            assertEquals("Doe", entries.get(0).getLastName());
            assertEquals("123 Main St", entries.get(0).getStreet());
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    public void testRemoveAddress() {
        // Test removing an entry
        String userInput = "Doe\n";
        InputStream originalIn = System.in;
        try {
            // Add a sample entry for removal
            addressBook.addAddressBook();

            // Mock the input stream with sample user input
            ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
            System.setIn(in);

            // Call the removeAddress method
            addressBook.removeAddress();

            // Get the entries and verify
            ArrayList<AddressEntry> entries = addressBook.getEntries();
            assertTrue(entries.isEmpty());
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    public void testSearchAddress() {
        // Add some sample entries
        addressBook.addAddressBook();

        // Mock the input stream with sample user input
        String userInput = "Doe\n";
        InputStream originalIn = System.in;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
            System.setIn(in);

            // Call the searchAddress method
            addressBook.searchAddress();
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    public void testGetAddressBook() {
        // Add some sample entries
        addressBook.addAddressBook();

        // Call the getAddressBook method
        addressBook.getAddressBook();
    }

    @Test
    public void testIsValidInput() {
        // Test valid input
        assertTrue(addressBook.isValidInput("John", "Doe", "123 Main St", "City", "12345", "john@example.com", "123-456-7890"));

        // Test invalid input
        assertFalse(addressBook.isValidInput("", "Doe", "123 Main St", "City", "12345", "john@example.com", "123-456-7890"));
    }
}

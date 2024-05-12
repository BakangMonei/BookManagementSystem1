/**
 * @Author: Monei Bakang Mothuti
 * @Date: Sunday 12 May 2024
 * @Time: 4:23 PM
 */

import model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class BookstoreGUI extends JFrame {
    private JButton addBookButton;
    private JButton manageInventoryButton;
    private JButton processSalesButton;
    private JButton customerManagementButton;
    private JButton viewRevenueButton;
    private JButton viewTopSellingBooksButton;

    private JTextField searchField;
    private JButton searchButton;

    public BookstoreGUI() {
        setTitle("Bookstore Management System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1));




        addBookButton = new JButton("Add New Book");
        manageInventoryButton = new JButton("Manage Inventory");
        processSalesButton = new JButton("Process Sales");
        customerManagementButton = new JButton("Customer Management");
        viewRevenueButton = new JButton("View Revenue");
        viewTopSellingBooksButton = new JButton("View Top Selling Books");
        // Search functionality
        searchField = new JTextField();
        searchButton = new JButton("Search");
        JPanel searchPanel = new JPanel(new GridLayout(1,1));
        searchPanel.add(new JLabel("Search by Author/Genre/Title:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        add(searchPanel); // Done
        add(addBookButton); // Done
        add(manageInventoryButton); // Done
        add(processSalesButton); // Done
        add(customerManagementButton); // Done
        add(viewRevenueButton); // Done
        add(viewTopSellingBooksButton);


        // Adding Books
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddBookForm();
            }
        });

        // Managing the books
        manageInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openManageInventoryForm();
            }
        });

        // Search Button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        // Process Button
        processSalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openProcessSalesForm();
            }
        });

        // Customer Management
        customerManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCustomerManagementForm();
            }
        });

        // View Total Revenue
        viewRevenueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewRevenue();
            }
        });

        // View Top selling books
        viewTopSellingBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                viewTopSellingBooks();
            }
        });

        setVisible(true);
    }

    private void openAddBookForm() {
        JFrame addBookFrame = new JFrame("Add New Book");
        addBookFrame.setSize(400, 300);
        addBookFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addBookFrame.setLayout(new GridLayout(7, 2));

        JLabel isbnLabel = new JLabel("ISBN:");
        JTextField isbnField = new JTextField();
        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField();
        JLabel genreLabel = new JLabel("Genre:");
        JTextField genreField = new JTextField();
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField();
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField();

        JButton addButton = new JButton("Add Book");

        addBookFrame.add(isbnLabel);
        addBookFrame.add(isbnField);
        addBookFrame.add(titleLabel);
        addBookFrame.add(titleField);
        addBookFrame.add(authorLabel);
        addBookFrame.add(authorField);
        addBookFrame.add(genreLabel);
        addBookFrame.add(genreField);
        addBookFrame.add(priceLabel);
        addBookFrame.add(priceField);
        addBookFrame.add(quantityLabel);
        addBookFrame.add(quantityField);
        addBookFrame.add(new JLabel()); // Empty space
        addBookFrame.add(addButton);

        // Inside addActionListener method
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add book to inventory using the provided details
                String isbn = isbnField.getText();
                String title = titleField.getText();
                String author = authorField.getText();
                String genre = genreField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                // Create a new Book object
                Book newBook = new Book(isbn, title, author, genre, price, quantity);

                // Write book details to text file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("inventory.txt", true))) {
                    writer.write(isbn + "," + title + "," + author + "," + genre + "," + price + "," + quantity);
                    writer.newLine();
                    writer.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(addBookFrame, "Error occurred while adding book to inventory!");
                    return; // Exit the method
                }

                // Display success message
                JOptionPane.showMessageDialog(addBookFrame, "Book added successfully!");
                addBookFrame.dispose(); // Close the form
            }
        });
        addBookFrame.setVisible(true);
    }

    /****************************************************** Create A New Book ***********************************************************************/
    private void openManageInventoryForm() {
        JFrame manageInventoryFrame = new JFrame("Manage Inventory");
        manageInventoryFrame.setSize(600, 400);
        manageInventoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a table to display inventory
        String[] columns = {"ISBN", "Title", "Author", "Genre", "Price", "Quantity"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        JTable table = new JTable();
        table.setModel(model);

        // Load inventory data from text file
        try (BufferedReader reader = new BufferedReader(new FileReader("inventory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(manageInventoryFrame, "Error loading inventory data!");
        }

        // Make table cells editable
        table.setDefaultEditor(Object.class, null);

        // Add table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        manageInventoryFrame.add(scrollPane);

        manageInventoryFrame.setVisible(true);
    }
    /*****************************************************************************************************************************/


    /****************************************************** Perform Search ***********************************************************************/
    private void performSearch() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term.");
            return;
        }

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ISBN", "Title", "Author", "Genre", "Price", "Quantity"});
        JTable table = new JTable(model);

        try (BufferedReader reader = new BufferedReader(new FileReader("inventory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String title = data[1].toLowerCase();
                String author = data[2].toLowerCase();
                String genre = data[3].toLowerCase();
                if (title.contains(searchTerm) || author.contains(searchTerm) || genre.contains(searchTerm)) {
                    model.addRow(data);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading inventory data!");
        }

        JScrollPane scrollPane = new JScrollPane(table);
        JFrame searchResultFrame = new JFrame("Search Results");
        searchResultFrame.add(scrollPane);
        searchResultFrame.setSize(600, 400);
        searchResultFrame.setVisible(true);
    }
    /*****************************************************************************************************************************/


    /****************************************************** Open Process Sales Form ***********************************************************************/
    private void openProcessSalesForm() {
        JFrame processSalesFrame = new JFrame("Process Sales");
        processSalesFrame.setSize(600, 400);
        processSalesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ISBN", "Title", "Author", "Genre", "Price", "Quantity"});
        JTable table = new JTable();
        table.setModel(model);

        // Load inventory data from text file
        try (BufferedReader reader = new BufferedReader(new FileReader("inventory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(processSalesFrame, "Error loading inventory data!");
            return;
        }

        JScrollPane scrollPane = new JScrollPane(table);
        JPanel salesPanel = new JPanel();
        JLabel quantityLabel = new JLabel("Quantity Sold:");
        JTextField quantityField = new JTextField(5);
        JButton generateReportButton = new JButton("Generate Sales Report");

        salesPanel.add(quantityLabel);
        salesPanel.add(quantityField);
        salesPanel.add(generateReportButton);

        processSalesFrame.setLayout(new BorderLayout());
        processSalesFrame.add(scrollPane, BorderLayout.CENTER);
        processSalesFrame.add(salesPanel, BorderLayout.SOUTH);

        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(processSalesFrame, "Please select a book to process sales.");
                    return;
                }

                // Get the quantity sold from the text field
                String quantityText = quantityField.getText().trim();
                if (quantityText.isEmpty()) {
                    JOptionPane.showMessageDialog(processSalesFrame, "Please enter a valid quantity.");
                    return;
                }
                int quantitySold;
                try {
                    quantitySold = Integer.parseInt(quantityText);
                    if (quantitySold <= 0) {
                        JOptionPane.showMessageDialog(processSalesFrame, "Quantity must be a positive integer.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(processSalesFrame, "Invalid quantity format.");
                    return;
                }

                // Update inventory quantity
                int currentQuantity = Integer.parseInt((String) model.getValueAt(selectedRow, 5));
                int updatedQuantity = currentQuantity - quantitySold;
                model.setValueAt(updatedQuantity, selectedRow, 5);

                // Generate sales report
                generateSalesReport((String) model.getValueAt(selectedRow, 1), quantitySold);

                JOptionPane.showMessageDialog(processSalesFrame, "Sales processed successfully!");
            }
        });

        processSalesFrame.setVisible(true);
    }

    private void generateSalesReport(String bookTitle, int quantitySold) {
        // Implement your code to generate sales report
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("sales_report.txt", true))) {
            writer.write(bookTitle + "," + quantitySold);
            writer.newLine();
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating sales report!");
        }
    }
    /*****************************************************************************************************************************/

    /****************************************************** Customer Management ***********************************************************************/
    private void openCustomerManagementForm() {
        JFrame customerManagementFrame = new JFrame("Customer Management");
        customerManagementFrame.setSize(600, 400);
        customerManagementFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Customer management panel
        JPanel customerPanel = new JPanel(new BorderLayout());

        // Customer table
        DefaultTableModel customerModel = new DefaultTableModel();
        customerModel.setColumnIdentifiers(new String[]{"Customer ID", "Name", "Email"});
        JTable customerTable = new JTable();
        customerTable.setModel(customerModel);

        // Load customer data from text file
        try (BufferedReader reader = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                customerModel.addRow(data);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(customerManagementFrame, "Error loading customer data!");
        }

        JScrollPane customerScrollPane = new JScrollPane(customerTable);
        customerPanel.add(customerScrollPane, BorderLayout.CENTER);

        // Buttons for adding new customer and viewing purchase history
        JPanel buttonPanel = new JPanel();
        JButton addCustomerButton = new JButton("Add New Customer");
        JButton viewPurchaseHistoryButton = new JButton("View Purchase History");

        buttonPanel.add(addCustomerButton);
        buttonPanel.add(viewPurchaseHistoryButton);
        customerPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners for buttons
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddCustomerForm(customerModel);
            }
        });

        // View Purchase History
        viewPurchaseHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openViewPurchaseHistoryForm(customerTable);
            }
        });
        customerManagementFrame.add(customerPanel);
        customerManagementFrame.setVisible(true);


    }


    /****************************************************** Open Add Customer ***********************************************************************/

    private void openAddCustomerForm(DefaultTableModel customerModel) {
        JFrame addCustomerFrame = new JFrame("Add New Customer");
        addCustomerFrame.setSize(400, 200);
        addCustomerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel addCustomerPanel = new JPanel(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JButton addButton = new JButton("Add Customer");

        addCustomerPanel.add(nameLabel);
        addCustomerPanel.add(nameField);
        addCustomerPanel.add(emailLabel);
        addCustomerPanel.add(emailField);
        addCustomerPanel.add(new JLabel()); // Empty label for alignment
        addCustomerPanel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();

                if (name.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(addCustomerFrame, "Please fill in all fields.");
                    return;
                }

                // Generate a unique customer ID (implementation needed)
                int customerId = generateCustomerId();

                // Add customer to the table
                customerModel.addRow(new Object[]{customerId, name, email});

                // Write customer data to the text file (implementation needed)
                writeCustomerToFile(customerId, name, email);

                JOptionPane.showMessageDialog(addCustomerFrame, "Customer added successfully!");
                addCustomerFrame.dispose(); // Close the form
            }
        });

        addCustomerFrame.add(addCustomerPanel);
        addCustomerFrame.setVisible(true);
    }

    private void openViewPurchaseHistoryForm(JTable customerTable) {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer to view purchase history.");
            return;
        }

        String customerId = customerTable.getValueAt(selectedRow, 0).toString();
        String customerName = customerTable.getValueAt(selectedRow, 1).toString();

        // Fetch purchase history for the selected customer (implementation needed)

        // Display purchase history in a new frame (implementation needed)
        JFrame viewPurchaseHistoryFrame = new JFrame("Purchase History for " + customerName);
        viewPurchaseHistoryFrame.setSize(600, 400);
        viewPurchaseHistoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea purchaseHistoryTextArea = new JTextArea();
        // Populate purchase history text area with data (implementation needed)

        JScrollPane scrollPane = new JScrollPane(purchaseHistoryTextArea);
        viewPurchaseHistoryFrame.add(scrollPane);
        viewPurchaseHistoryFrame.setVisible(true);
    }


    private int generateCustomerId() {
        // Implementation to generate a unique customer ID
        // You can use a counter or some other mechanism to ensure uniqueness
        // For simplicity, you can generate a random number within a certain range
        return (int) (Math.random() * 1000) + 1;
    }

    private void writeCustomerToFile(int customerId, String name, String email) {
        // Implementation to write customer data to the text file
        // You can append the data to the end of the file in CSV format
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("customers.txt", true))) {
            writer.write(customerId + "," + name + "," + email);
            writer.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error writing customer data to file!");
        }
    }

    /****************************************************** View Revenue ***********************************************************************/
    private void viewRevenue() {
        double totalRevenue = calculateTotalRevenue();
        JOptionPane.showMessageDialog(this, "Total Revenue: $" + String.format("%.2f", totalRevenue));
    }

    private double calculateTotalRevenue() {
        double totalRevenue = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader("sales_report.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 3) {
                    System.err.println("Invalid data format in sales report: " + line);
                    continue; // Skip this line and move to the next one
                }
                double price;
                try {
                    price = Double.parseDouble(data[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid price format in sales report: " + line);
                    continue; // Skip this line and move to the next one
                }
                int quantity = Integer.parseInt(data[2]);
                totalRevenue += price * quantity;
            }
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error calculating total revenue!");
        }

        return totalRevenue;
    }

    /*****************************************************************************************************************************/

    /****************************************************** View top rated Customer ***********************************************************************/

    /*****************************************************************************************************************************/

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookstoreGUI());
    }
}

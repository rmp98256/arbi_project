import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;

public class JaiBalramGUI {

	static JFrame frame;
	private static int lastRow;
	private static int initialRowCount=5;

	public JaiBalramGUI() {
		frame = new JFrame("Jai Balram");
		frame.setSize(1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
	}

	public static void main(String[] args) {
		// Frame setup
		new JaiBalramGUI();

		// Header label
		JLabel headerLabel = new JLabel("\u091C\u092F \u092C\u0932\u0930\u093E\u092E", SwingConstants.CENTER);
		headerLabel.setFont(new Font("Mangal", Font.BOLD, 24));
		frame.add(headerLabel, BorderLayout.NORTH);

		// Main panel with padding
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		frame.add(mainPanel, BorderLayout.CENTER);

		// Date input (with date picker)
		JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel dateLabel = new JLabel("Date: ");
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDate(new Date()); // Set current date as default
		dateChooser.setDateFormatString("dd-MM-yyyy");

		datePanel.add(dateLabel);
		datePanel.add(dateChooser);
		mainPanel.add(datePanel);
		JDateChooser dateChooser1 = new JDateChooser();
		dateChooser1.setDate(new Date()); // Set current date as default
		dateChooser1.setDateFormatString("yyyy-MM-dd_HH-mm-ss");
		Date selectedDate = dateChooser1.getDate();

		// Dropdown menu
		JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel dropdownLabel = new JLabel("Crop ");
		String[] dropdownOptions = { Constants.ARBI, Constants.ADARAK };
		JComboBox<String> dropdown = new JComboBox<>(dropdownOptions);
		dropdownPanel.add(dropdownLabel);
		dropdownPanel.add(dropdown);
		mainPanel.add(dropdownPanel);

		// Table for former details
		JPanel tablePanel = new JPanel();
		String[] columnNames = { "Former Name", "No. of Bags", "Weight (40/50 kg)", "Rate per Bag", "Washed",
				"Loading Cost", "Rate per Kg", "Net Weight", "Cost of Vegetables" };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, initialRowCount);
		// Loop through all rows and set values
		for (int row = 0; row < initialRowCount; row++) {
			tableModel.setValueAt(40, row, 2); // Set 40 in column index 2
			tableModel.setValueAt(Constants.WASHING, row, 4); // Set Constants.WASHING in column index 4
		} // Assuming washedOptions[0] is false
		JTable table = new JTable(tableModel) {
			@Override
			public Class<?> getColumnClass(int column) {
				if (column == ColumnConstants.WASHED) { // For the "Washed" column
					return String.class; // Change to String to match the dropdown data type
				}
				return super.getColumnClass(column);
			}
		};

		// Create a dropdown (JComboBox) for the "Washed" column
		String[] washedOptions = { Constants.WASHING, Constants.WASHINGANDLOADING, Constants.LOADING,
				Constants.NONOFTHEABOVE }; // Options for the dropdown
		JComboBox<String> washedDropdown = new JComboBox<>(washedOptions);

		// Set the dropdown as the editor for the "Washed" column
		table.getColumnModel().getColumn(ColumnConstants.WASHED).setCellEditor(new DefaultCellEditor(washedDropdown));

		// Add a scroll pane and the table to the panel
		JScrollPane tableScrollPane = new JScrollPane(table);
		tableScrollPane.setPreferredSize(new Dimension(1100, 120));
		tablePanel.add(tableScrollPane);

		// Add the table panel to your main panel
		mainPanel.add(tablePanel);

		// Restrict numeric input in NO_OF_BAGS
		table.getColumnModel().getColumn(ColumnConstants.NO_OF_BAGS)
				.setCellEditor(new DefaultCellEditor(new JTextField()) {
					@Override
					public boolean stopCellEditing() {
						JTextField editor = (JTextField) getComponent();
						String text = editor.getText();
						if (text != null && !text.trim().isEmpty()) {
							try {
								if (editor.getText() != null || !editor.getText().isBlank()) {
									Integer.parseInt(editor.getText());
								}
							} catch (NumberFormatException ex) {
								JOptionPane.showMessageDialog(frame, "Only numeric values allowed for 'No. of Bags'.",
										"Input Error", JOptionPane.ERROR_MESSAGE);
								return false;
							}
						}

						return super.stopCellEditing();
					}
				});

		// Restrict numeric input in WEIGHT_OF_BAG
		table.getColumnModel().getColumn(ColumnConstants.WEIGHT_OF_BAG)
				.setCellEditor(new DefaultCellEditor(new JTextField()) {
					@Override
					public boolean stopCellEditing() {
						JTextField editor = (JTextField) getComponent();
						String text = editor.getText();
						if (text != null && !text.trim().isEmpty()) {
							try {
								if (editor.getText() != null || !editor.getText().isBlank()) {
									Integer.parseInt(editor.getText());
								}
							} catch (NumberFormatException ex) {
								JOptionPane.showMessageDialog(frame,
										"Only numeric values allowed for 'Weight (40/50 kg)'.", "Input Error",
										JOptionPane.ERROR_MESSAGE);
								return false;
							}
						}
						return super.stopCellEditing();
					}
				});

		table.getColumnModel().getColumn(ColumnConstants.RATE_PER_BAG)
				.setCellEditor(new DefaultCellEditor(new JTextField()) {
					@Override
					public boolean stopCellEditing() {
						JTextField editor = (JTextField) getComponent();
						String text = editor.getText();
						if (text != null && !text.trim().isEmpty()) {
							try {
								if (editor.getText() != null || !editor.getText().isBlank()) {
									Integer.parseInt(editor.getText());
								}
							} catch (NumberFormatException ex) {
								JOptionPane.showMessageDialog(frame, "Only numeric values allowed for 'Rate per Bag'.",
										"Input Error", JOptionPane.ERROR_MESSAGE);
								return false;
							}
						}
						return super.stopCellEditing();
					}
				});

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.removeColumn(columnModel.getColumn(ColumnConstants.LOADING_COST));
		columnModel.removeColumn(columnModel.getColumn(ColumnConstants.RATE_PER_KG - 1));
		columnModel.removeColumn(columnModel.getColumn(ColumnConstants.NET_WEIGHT - 2));
		columnModel.removeColumn(columnModel.getColumn(ColumnConstants.COST_OF_VEGETABLE - 3));

		// Button to add rows
		JButton addRowButton = new JButton("Add Row");
		addRowButton.addActionListener(e -> {
			tableModel.addRow(new Object[9]); // Add a new empty row

			// Set values for the new row
			lastRow = tableModel.getRowCount() - 1;
			tableModel.setValueAt(40, lastRow, 2);
			tableModel.setValueAt(Constants.WASHING, lastRow, 4); // Assuming washedOptions[0] is false

			System.out.println("Row added. Total rows: " + tableModel.getRowCount());
		});

		tablePanel.add(addRowButton);
		mainPanel.add(tablePanel);

		// Delete row button
		JButton deleteRowButton = new JButton("Delete Row");
		deleteRowButton.addActionListener(e -> {
			int[] selectedRows = table.getSelectedRows(); // Get all selected rows

			if (selectedRows.length > 0) {
				System.out.println("Selected Rows: " + java.util.Arrays.toString(selectedRows));

				// Sort rows in descending order to prevent shifting issues
				java.util.Arrays.sort(selectedRows);

				for (int i = selectedRows.length - 1; i >= 0; i--) {
					tableModel.removeRow(selectedRows[i]);
				}
			} else {
				JOptionPane.showMessageDialog(frame, "Please select one or more rows to delete.", "No Row Selected",
						JOptionPane.WARNING_MESSAGE);
			}
		});

		// Add button to panel
		tablePanel.add(deleteRowButton);
		mainPanel.add(tablePanel);
		
		///////////////////////////////////////
		// Panel for Amount and Description input
		JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		// Amount Label & Input
		JLabel amountLabel = new JLabel("Amount: ");
		JTextField advanceField = new JTextField(10);

		// Restrict Amount to Numeric Input
		advanceField.setInputVerifier(new InputVerifier() {
			@Override
			public boolean verify(JComponent input) {
				String text = ((JTextField) input).getText();

				// Allow null or empty values
				if (text == null || text.trim().isEmpty()) {
					return true;
				}

				// Check for numeric values
				try {
					Double.parseDouble(text.trim()); // Attempt to parse as a double
					return true; // Numeric input is valid
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Only numeric values are allowed for 'Amount'.", "Input Error",
							JOptionPane.ERROR_MESSAGE);
					return false; // Reject non-numeric input
				}
			}
		});
		amountPanel.add(amountLabel);
		amountPanel.add(advanceField);
		// Description Label & Input
		JLabel descriptionLabel = new JLabel("Description: ");
		JTextField descriptionField = new JTextField(20); // Text field for string input
		// Restrict description field to have value if advanceAmount is there
		descriptionField.setInputVerifier(new InputVerifier() {
			@Override
			public boolean verify(JComponent input) {
				String text = ((JTextField) input).getText();

				System.out.println("text=" + text);

				// Check for numeric values
				try {
					System.out.println(
							"Double.parseDouble(advanceField.getText())=" + Double.parseDouble(advanceField.getText()));
					if (Double.parseDouble(advanceField.getText()) > 0 && text.isEmpty()) {
						System.out.println("Inside if ");
						JOptionPane.showMessageDialog(null, "Description is required when Advance Amount is entered.",
								"Input Error", JOptionPane.ERROR_MESSAGE);
						return false; // Stop further execution
					}

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Advance field is Non Numaric .", "Input Error",
							JOptionPane.ERROR_MESSAGE);

					return false; // Reject non-numeric input
				}
				return true;
			}
		});

		// Add components to the panel

		amountPanel.add(descriptionLabel);
		amountPanel.add(descriptionField);

		// Add panel to main panel
		mainPanel.add(amountPanel);

		// Submit button
		JButton submitButton = new JButton("Generate Report and Send Mail");
		submitButton.addActionListener(e -> {
			try {
				if (tableModel.getRowCount() < 1) {
					JOptionPane.showMessageDialog(frame, "No Row Added to Table", "Validation Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				// Validate rows
				for (int i = 0; i < tableModel.getRowCount(); i++) {
					for (int j = 0; j < 4; j++) { // Check first 3 columns
						if (tableModel.getValueAt(i, j) == null
								|| tableModel.getValueAt(i, j).toString().trim().isEmpty()) {
							JOptionPane.showMessageDialog(frame,
									"Row " + (i + 1) + " has an empty field in column " + columnNames[j] + ".",
									"Validation Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				}
				if (Double.parseDouble(advanceField.getText())>0&&descriptionField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "No Description is added", "Validation Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				// Calculate additional columns
				for (int i = 0; i < tableModel.getRowCount(); i++) {
					int weight = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
					double ratePerBag = Double.parseDouble(tableModel.getValueAt(i, 3).toString());
					String isWashed = (String) tableModel.getValueAt(i, 4); // Washed column updated to index 4
					double ratePerKg = ratePerBag / weight;
					int noOfBags = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
					double loadingCost = 0; // You can change this calculation as per your requirement
					double netWeight = noOfBags * weight;

					// Constants.WASHING,Constants.WASHINGANDLOADING,Constants.LOADING,Constants.NONOFTHEABOVE
					// Set Loading Cost value
					if (Constants.WASHING.equals(isWashed)) {
						tableModel.setValueAt(Boolean.TRUE, i, 4);
						loadingCost = netWeight * 1;
						tableModel.setValueAt(loadingCost, i, 5);
					} else {
						tableModel.setValueAt(Boolean.FALSE, i, 4);
						loadingCost = netWeight * 0.15;
						tableModel.setValueAt(loadingCost, i, 5);
					}

					double costOfVegetables = netWeight * ratePerKg + loadingCost;

					tableModel.setValueAt(String.format("%.2f", ratePerKg), i, 7);
					tableModel.setValueAt(netWeight, i, 6);
					tableModel.setValueAt(String.format("%.2f", costOfVegetables), i, 8);
				}

				// Add summary row
				int rowCount = tableModel.getRowCount();
				double totalBags = 0, totalNetWeight = 0, totalCost = 0, totalLoadingCost = 0, totalVegCost = 0;

				for (int i = 0; i < rowCount; i++) {
					totalBags += Integer.parseInt(tableModel.getValueAt(i, 1).toString());
					totalNetWeight += Double.parseDouble(tableModel.getValueAt(i, 6).toString());
					totalLoadingCost += Double.parseDouble(tableModel.getValueAt(i, 5).toString());
					totalVegCost += Double.parseDouble(tableModel.getValueAt(i, 8).toString());
				}

				tableModel.addRow(new Object[] { "Total", totalBags, "-", "-", "-", totalLoadingCost, totalNetWeight,
						"-", totalVegCost });
				// Generate PDF report
				String selectedDateString = null;
				System.out.println("selected date =" + selectedDate);
				if (selectedDate != null) {
					// Format the date to a specific string format
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

					selectedDateString = dateFormat.format(selectedDate);
					System.out.println("selectedDateString=" + selectedDateString);

				}

				String fileName = Paths.get(System.getProperty("user.home"), "Desktop", selectedDateString + ".pdf")
						.toString();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				Document document = new Document();
				Document maildocument = new Document();
				PdfWriter.getInstance(document, new FileOutputStream(fileName));
				PdfWriter.getInstance(maildocument, outputStream);
				document.open();
				maildocument.open();

				// Create the paragraph with the bold font
				Paragraph paragraph = new Paragraph("Jai Balram");

				// Set alignment to center
				paragraph.setAlignment(Element.ALIGN_CENTER);

				// Add the paragraph to the document
				document.add(paragraph);
				document.add(new Paragraph("Date: " + selectedDate));
				document.add(new Paragraph("\n")); // Add a line space
				document.add(new Paragraph("\nFormer Details:\n"));
				document.add(new Paragraph("\n"));

				maildocument.add(new Paragraph("Jai Balram"));
				maildocument.add(new Paragraph("Date: " + selectedDate));
				maildocument.add(new Paragraph("\n")); // Add a line space
				maildocument.add(new Paragraph("\nFormer Details:\n"));
				maildocument.add(new Paragraph("\n"));

				PdfPTable pdfTable = new PdfPTable(columnNames.length);
				for (String columnName : columnNames) {
					pdfTable.addCell(new PdfPCell(new Phrase(columnName)));
				}

				for (int i = 0; i < tableModel.getRowCount(); i++) {
					for (int j = 0; j < columnNames.length; j++) {
						Object cellValue = tableModel.getValueAt(i, j);
						String cellText = (cellValue == null) ? "" : String.valueOf(cellValue);
						pdfTable.addCell(new PdfPCell(new Phrase(cellText)));
					}
				}

				document.add(pdfTable);
				maildocument.add(pdfTable);

				// Add advance and net cost
				double advanceAmount;
				String descriptionText = "";
				try {
					advanceAmount = Double.parseDouble(advanceField.getText());
					descriptionText = descriptionField.getText().trim();

				} catch (NumberFormatException e1) {
					// If parsing fails (e.g., empty field or invalid input), set to 0
					advanceAmount = 0;
				}
				double Cost_of_Bags = totalBags * 30;
				double TractorAmount = totalBags * 25;
				double netCost = totalVegCost + advanceAmount + Cost_of_Bags + TractorAmount + totalLoadingCost;

				document.add(new Paragraph("\nAdvance Amount: " + String.format("%.2f", advanceAmount)));
				document.add(new Paragraph("  Description: " + descriptionText));
				document.add(new Paragraph("\nCost of Bags: " + String.format("%.2f", Cost_of_Bags)));
				document.add(new Paragraph("\nCost of Bags: " + String.format("%.2f", Cost_of_Bags)));
				document.add(new Paragraph("\nTractor cost " + String.format("%.2f", TractorAmount)));
				document.add(new Paragraph("\nTotal Loading Cost: " + String.format("%.2f", totalLoadingCost)));
				document.add(new Paragraph("\nNet Cost of the Day: " + String.format("%.2f", netCost)));

				maildocument.add(new Paragraph("\nAdvance Amount: " + String.format("%.2f", advanceAmount)));
				maildocument.add(new Paragraph("  Description: " + descriptionText));
				maildocument.add(new Paragraph("\nCost of Bags: " + String.format("%.2f", Cost_of_Bags)));
				maildocument.add(new Paragraph("\nTractor cost " + String.format("%.2f", TractorAmount)));
				maildocument.add(new Paragraph("\nTotal Loading Cost: " + String.format("%.2f", totalLoadingCost)));
				maildocument.add(new Paragraph("\nNet Cost of the Day: " + String.format("%.2f", netCost)));
				maildocument.close();
				document.close();

				byte[] pdfData = outputStream.toByteArray();
				SendMailWithGeneratedPDF.sendMail(pdfData, selectedDateString);
				System.out.println("PDF report generated on Desktop: " + fileName);
				JOptionPane.showMessageDialog(frame, "PDF report generated on Desktop: " + fileName);

			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(frame, "Error generating report: " + ex.getMessage());
			}
		});

		// Clear button to reset all data
		JButton clearButton = new JButton("Clear Data");
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Clear table data
				tableModel.setRowCount(0);

				// Reset form fields
				dateChooser.setDate(new Date()); // Set current date as default
				dateChooser.setDateFormatString("yyyy-MM-dd");

				// dateChooser.setText(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
				advanceField.setText("");
				descriptionField.setText("");
				dropdown.setSelectedIndex(0);
			}
		});

		// Panel for buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(submitButton);
		buttonPanel.add(clearButton);
		mainPanel.add(buttonPanel);

		frame.setVisible(true);
	}
}
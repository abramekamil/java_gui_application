/*
Serves as presentation slides and transcript
----------------------------------------------------------------
Unit 6: Mastery Assignment - Configurator GUI Application
Kamil Abramek
Professional Studies, Southwestern College
CSCI431PA2023FALL02PS3: Java Programming Principles 2
Professor David Hays
December 17, 2023
----------------------------------------------------------------
This application allows the user to configure and order their computer system base on either Intel or AMD chipsets.
Application features:
1. Displays dynamic changes to the system configuration on the fly
2. Provides dynamic changes to the running total.
3. Calculates the total cost using calculate button
4. Resets the application using clear button
5. Gives the user summary of the configuration and allows them to place an order
5. Close the application using the exit button
----------------------------------------------------------------
References:
Intel Icon Source: https://www.freeiconspng.com/img/11630
AMD Icon Source: https://www.flaticon.com/free-icon/amd_732178
----------------------------------------------------------------
*/

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ComputerConfiguratorGUI extends JFrame {
    private Configurator configurator;
    private JComboBox<String> baseSystemComboBox;
    private JComboBox<String> processorComboBox;
    private JComboBox<String> graphicsCardComboBox;
    private JComboBox<String> memoryComboBox;
    private JCheckBox[] applicationCheckBoxes;
    private JRadioButton[] opticalDriveButtons;
    private JRadioButton[] hardDriveButtons;
    private JRadioButton[] osRadioButtons;
    private JTextArea configurationTextArea;
    private JButton calculateButton;
    private JButton clearButton;
    private JButton orderButton;
    private JButton exitButton;
    private JLabel iconLabel;
    public void setWindowSize(int width, int height) {
        setSize(width, height);
    } // Takes parameters from main
    private boolean calculateClicked = false; // Flag to check if the "Calculate" button is clicked
    private double totalPriceBeforeTaxAndShipping = 0.0; // To display price before total calculation are made

    // Set the main GUI
    public ComputerConfiguratorGUI() {
        configurator = new IntelConfigurator();

        setTitle("Configurator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeComponents();
        addComponentsToFrame();
        pack();
        baseSystemComboBox.setSelectedItem("Intel");
        iconLabel.setIcon(new ImageIcon("intel.png"));

    }
    // Initialize all components in GUI
    private void initializeComponents() {
        baseSystemComboBox = new JComboBox<>(new String[]{"Intel", "AMD"});
        processorComboBox = new JComboBox<>();

        // Initialize processorComboBox based on the initial selection
        updateProcessorOptions();

        baseSystemComboBox.addActionListener(e -> {
            updateProcessorOptions();
            updateConfiguration(); // Update configuration when the base system changes
        });

        applicationCheckBoxes = new JCheckBox[]{
                new JCheckBox("Microsoft Office Home and Student 2013 (+$139)"),
                new JCheckBox("Microsoft Office Home & Business 2013 (+$219)"),
                new JCheckBox("Accounting package (+$399)"),
                new JCheckBox("Graphics package (+499)")
        };

        graphicsCardComboBox = new JComboBox<>(new String[]{
                "Included: Integrated 3D Graphics",
                "<html>NVIDIA GeForce G310 512MB DDR3 <font color='blue'>(+$80)</font></html",
                "<html>NVIDIA GeForce GT620 1GB DDR3 <font color='blue'>(+$169)</font></html",
                "<html>NVIDIA GeForce GT640 1GB GDDR5 <font color='blue'>(+$490)</font></html",
        });
        memoryComboBox = new JComboBox<>(new String[]{
                //<html>Intel® Celeron® G1620 2.70GHz <font color='blue'>(+$50.0)</font></html>
                "Included: 4GB Dual Channel DDR3 1600MHz - 1 DIMMs",
                "<html>6GB Dual Channel DDR3 1600MHz - 1 DIMMs <font color='blue'>(+$28)</font></html",
                "<html>8GB Dual Channel DDR3 1600MHz - 1 DIMMs <font color='blue'>(+$58)</font></html",
                "<html>12GB Dual Channel DDR3 1600MHz - 1 DIMMs <font color='blue'>(+$108)</font></html",
                "<html>16GB Dual Channel DDR3 1600MHz - 1 DIMMs <font color='blue'>(+$176)</font></html"
        });

        opticalDriveButtons = new JRadioButton[]{
                new JRadioButton("CD-ROM (Included with Base Package)"),
                new JRadioButton("DVD Drive (+$17)"),
                new JRadioButton("Combo DVD/CD-RW (+$40)"),
                new JRadioButton("DVD and CD-RW (+$79)"),
        };
        ButtonGroup opticalDriveGroup = new ButtonGroup();
        for (JRadioButton radioButton : opticalDriveButtons) {
            opticalDriveGroup.add(radioButton);
        }
        opticalDriveButtons[0].setSelected(true);

        hardDriveButtons = new JRadioButton[]{
                new JRadioButton("125GB (Included with Base Package)"),
                new JRadioButton("250GB (+$27)"),
                new JRadioButton("500GB (+$50)"),
                new JRadioButton("1TGB (+$89)"),
        };
        ButtonGroup hardDriveGroup = new ButtonGroup();
        for (JRadioButton radioButton : hardDriveButtons) {
            hardDriveGroup.add(radioButton);
        }
        hardDriveButtons[0].setSelected(true);


        osRadioButtons = new JRadioButton[]{
                new JRadioButton("Windows 8.1 (Included with Base Package)"),
                new JRadioButton("Windows 8.1 Pro (+$59)"),
                new JRadioButton("Linux (-$89)")
        };
        ButtonGroup osButtonGroup = new ButtonGroup();
        for (JRadioButton radioButton : osRadioButtons) {
            osButtonGroup.add(radioButton);
        }
        osRadioButtons[0].setSelected(true);

        // Icon Label
        iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        iconLabel.setVerticalAlignment(JLabel.TOP);

        configurationTextArea = new JTextArea();
        configurationTextArea.setEditable(false);

        // Calculate the total
        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(e -> updateConfiguration());

        // Clear all selections
        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearConfiguration());


        // Display the order message
        orderButton = new JButton("Order");
        orderButton.addActionListener(e -> processOrder());

        // Exit the application
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        addOptionListeners();
    }

    // Change combo box selection based configuration selected
    private void updateProcessorOptions() {
        processorComboBox.removeAllItems();

        if (baseSystemComboBox.getSelectedItem().equals("Intel")) {
            processorComboBox.addItem("Included Processor: Intel® Celeron® G1610");
            processorComboBox.addItem("<html>Intel® Celeron® G1620 2.70GHz <font color='blue'>(+$50.0)</font></html>");
            processorComboBox.addItem("<html>Intel® Celeron® G1630 2.80GHz <font color='blue'>(+$90.0)</font></html>");
            processorComboBox.addItem("<html>Intel® Celeron® G1820 2.70GHz <font color='blue'>(+$105.0)</font></html>");
            processorComboBox.addItem("<html>Intel® Celeron® G1830 2.80GHz <font color='blue'>(+$130.0)</font></html>");
        } else if (baseSystemComboBox.getSelectedItem().equals("AMD")) {
            processorComboBox.addItem("Included Processor: AMD FX-2100 for Desktops");
            processorComboBox.addItem("<html>AMD FX-8350 for Desktops <font color='blue'>(+$25.0)</font></html>");
            processorComboBox.addItem("<html>AMD FX-9590 for Desktops <font color='blue'>(+$90.0)</font></html>");
            processorComboBox.addItem("<html>AMD FX-4100 for Desktops <font color='blue'>(+$187.0)</font></html>");
            processorComboBox.addItem("<html>AMD FX-4300 for Desktops <font color='blue'>(+$280.0)</font></html>");
        }
    }

    //Add all components to frame
    private void addComponentsToFrame() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Base System Section
        addComponentToPanel(mainPanel, new JLabel("Base System:"), 0, 0);
        addComponentToPanel(mainPanel, baseSystemComboBox, 1, 0);

        // Processor Section
        addComponentToPanel(mainPanel, new JLabel("Processor:"), 0, 1);
        addComponentToPanel(mainPanel, processorComboBox, 1, 1);

        // Memory Section
        addComponentToPanel(mainPanel, new JLabel("Memory:"), 0, 2);
        addComponentToPanel(mainPanel, memoryComboBox, 1, 2);

        // Graphics Section
        addComponentToPanel(mainPanel, new JLabel("Graphics Card:"), 0, 3);
        addComponentToPanel(mainPanel, graphicsCardComboBox, 1, 3);

        // Operating System Section
        addComponentToPanel(mainPanel, new JLabel("Operating System:"), 0, 4);
        for (int i = 0; i < osRadioButtons.length; i++) {
            addComponentToPanel(mainPanel, osRadioButtons[i], 1, 4 + i);
        }

        // Optical Drive Section
        addComponentToPanel(mainPanel, new JLabel("Optical Drive:"), 0, 5 + osRadioButtons.length);
        for (int i = 0; i < opticalDriveButtons.length; i++) {
            addComponentToPanel(mainPanel, opticalDriveButtons[i], 1, 5 + osRadioButtons.length + i);
        }

        // Hard Drive Section
        addComponentToPanel(mainPanel, new JLabel("Hard Drive:"), 0, 6 + osRadioButtons.length + opticalDriveButtons.length);
        for (int i = 0; i < hardDriveButtons.length; i++) {
            addComponentToPanel(mainPanel, hardDriveButtons[i], 1, 6 + osRadioButtons.length + opticalDriveButtons.length + i);
        }

        // Application Packages Section
        addComponentToPanel(mainPanel, new JLabel("Application Packages:"), 0, 7 + osRadioButtons.length + opticalDriveButtons.length + hardDriveButtons.length);
        for (int i = 0; i < applicationCheckBoxes.length; i++) {
            addComponentToPanel(mainPanel, applicationCheckBoxes[i], 1, 7 + osRadioButtons.length + opticalDriveButtons.length + hardDriveButtons.length + i);
        }

        // Configuration Text Area Section
        JPanel configurationPanel = new JPanel(new BorderLayout());
        configurationPanel.add(new JScrollPane(configurationTextArea), BorderLayout.CENTER);

        // Icon Panel
        JPanel iconPanel = new JPanel(new BorderLayout());
        iconPanel.add(iconLabel, BorderLayout.SOUTH);

        configurationPanel.add(iconPanel, BorderLayout.NORTH); // Display Icon Panel above Configuration Text Area

        // Button Panel Section
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(orderButton);
        buttonPanel.add(exitButton);

        // Final Layout
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.WEST);
        add(configurationPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Add components to panel
    private void addComponentToPanel(JPanel panel, JComponent component, int gridx, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 15, 5, 5);

        panel.add(new JLabel(""), gbc);  // Add an empty JLabel to align with the labels
        gbc.gridx++;  // Increment gridx to place the component next to the empty label
        panel.add(component, gbc);
    }

    //Update Configuration
    private void updateConfiguration() {
        if (Objects.equals(baseSystemComboBox.getSelectedItem(), "Intel")) {
            configurator = new IntelConfigurator();
        } else {
            configurator = new AMDConfigurator();
        }

        // Set the icon based on the selected base system
        if (Objects.equals(baseSystemComboBox.getSelectedItem(), "Intel")) {
            iconLabel.setIcon(new ImageIcon("intel.png"));
        } else {
            iconLabel.setIcon(new ImageIcon("amd.png"));
        }

        int processorOption = processorComboBox.getSelectedIndex() + 1;
        configurator.addProcessor(processorOption);

        int graphicsCardOption = graphicsCardComboBox.getSelectedIndex() + 1;
        configurator.addGraphicsCard(graphicsCardOption);

        for (int i = 0; i < memoryComboBox.getItemCount(); i++) {
            if (memoryComboBox.getSelectedIndex() == i) {
                configurator.addMemory(i + 1);
                break;
            }
        }

        for (JRadioButton hardDriveButton : hardDriveButtons) {
            if (hardDriveButton.isSelected()) {
                double hardDrivePrice = getHardDrivePrice(hardDriveButton.getText());
                configurator.addHardDrive(hardDriveButton.getText(), hardDrivePrice);
                break;
            }
        }

        for (JRadioButton opticalDriveButton : opticalDriveButtons) {
            if (opticalDriveButton.isSelected()) {
                double opticalDrivePrice = getOpticalDrivePrice(opticalDriveButton.getText());
                configurator.addOpticalDrive(opticalDriveButton.getText(), opticalDrivePrice);
                break;
            }
        }

        for (int i = 0; i < osRadioButtons.length; i++) {
            if (osRadioButtons[i].isSelected()) {
                configurator.addOperatingSystem(osRadioButtons[i].getText(), i == 1 ? 59.0 : (i == 2 ? -89.0 : 0.0));
                break;
            }
        }

        for (JCheckBox checkBox : applicationCheckBoxes) {
            if (checkBox.isSelected()) {
                configurator.addApplicationPackage(checkBox.getText(), getApplicationPackagePrice(checkBox.getText()));
            }
        }

        totalPriceBeforeTaxAndShipping = configurator.getTotalPrice(); // Calculate Price Before Tax And Shipping

        if (calculateClicked) {
            configurator.addSalesTax(0.078);
            configurator.addShippingAndHandling(0.025);

            double totalPriceAfterTaxAndShipping = configurator.getTotalPrice();

            String configurationSummary = configurator.getConfigurationSummary() +
                    "\nTotal Price (Before Tax and Shipping): $" + String.format("%.2f", totalPriceBeforeTaxAndShipping) +
                    "\nTotal Price (After Tax and Shipping): $" + String.format("%.2f", totalPriceAfterTaxAndShipping);

            configurationTextArea.setText(configurationSummary);
        } else {
            String configurationSummary = configurator.getConfigurationSummary() +
                    "\nTotal Price (Before Tax and Shipping): $" + String.format("%.2f", totalPriceBeforeTaxAndShipping);

            configurationTextArea.setText(configurationSummary);
        }
    }

    private double getOpticalDrivePrice(String option) {
        return switch (option) {
            case "DVD Drive (+$17)" -> 17.0;
            case "Combo DVD/CD-RW (+$40)" -> 50.0;
            case "DVD and CD-RW (+$79)" -> 79.0;
            default -> 0.0;
        };
    }
    private double getHardDrivePrice(String option) {
        return switch (option) {
            case "250GB (+$27)" -> 27.0;
            case "500GB (+$50)" -> 50.0;
            case "1TGB (+$89)" -> 89.0;
            default -> 0.0;
        };
    }
    private double getApplicationPackagePrice(String packageName) {
        return switch (packageName) {
            case "Microsoft Office Home and Student 2013 (+$139)" -> 139.0;
            case "Microsoft Office Home & Business 2013 (+$219)" -> 219.0;
            case "Accounting package (+$399)" -> 399.0;
            case "Graphics package (+499)" -> 499.0;
            default -> 0.0;
        };
    }
    private void clearConfiguration() {
        configurator.resetConfiguration();
        baseSystemComboBox.setSelectedIndex(0);
        processorComboBox.setSelectedIndex(0);
        graphicsCardComboBox.setSelectedIndex(0);
        memoryComboBox.setSelectedIndex(0);
        osRadioButtons[0].setSelected(true);
        opticalDriveButtons[0].setSelected(true);
        hardDriveButtons[0].setSelected(true);
        for (JCheckBox checkBox : applicationCheckBoxes) {
            checkBox.setSelected(false);
        }
        configurationTextArea.setText("");

        // Set calculateClicked to false when clearing the configuration
        calculateClicked = false;
    }

    private void processOrder() {
        double totalPrice = configurator.getTotalPrice();
        String formattedTotalPrice = String.format("%.2f", totalPrice);

        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to place this order?\n\n" + configurator.getConfigurationSummary() +
                        "\nTotal Price (Before Tax and Shipping): $" + String.format("%.2f", totalPriceBeforeTaxAndShipping) +
                        "\nTotal Price: $" + formattedTotalPrice,
                "Confirm Order", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                    "Order Processed!\nThank you for your purchase.",
                    "Order Processed", JOptionPane.INFORMATION_MESSAGE);
            clearConfiguration();
        }
    }


    private void addOptionListeners() {
        processorComboBox.addActionListener(e -> updateConfiguration());
        memoryComboBox.addActionListener(e -> updateConfiguration());
        graphicsCardComboBox.addActionListener(e -> updateConfiguration());

        for (JRadioButton radioButton : opticalDriveButtons) {
            radioButton.addActionListener(e -> updateConfiguration());
        }

        for (JRadioButton radioButton : hardDriveButtons) {
            radioButton.addActionListener(e -> updateConfiguration());
        }

        for (JRadioButton radioButton : osRadioButtons) {
            radioButton.addActionListener(e -> updateConfiguration());
        }

        for (JCheckBox checkBox : applicationCheckBoxes) {
            checkBox.addActionListener(e -> updateConfiguration());
        }

        calculateButton.addActionListener(e -> {
            calculateClicked = true;
            updateConfiguration();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ComputerConfiguratorGUI gui = new ComputerConfiguratorGUI();
            gui.setWindowSize(970, 750);
            gui.setLocationRelativeTo(null); // Center the JFrame on the screen
            gui.setVisible(true);
        });
    }
}
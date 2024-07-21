import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

public class CreateUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel panelAllStudent;
    private JPanel panelAddStudent;
    private CardLayout cardLayout;
    private BoxLayout boxLayout;
    private GridLayout gridLayout;
    private JPanel panelInformationStudent;
    public CreateUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("Student Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initMainPanel();

        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }
    ListStudent listStudent;
    StudentTableModel studentTableModel;
    JTable tableStudent;
    ImageRenderer imageRenderer;

    private void createTable() {
        if (panelAllStudent!=null) {
            /*listStudent = new ListStudent();
            listStudent.importStudentListFromFileBinary("students.bin");
            String[] columnName = new String[]{"ID","Name","GPA","Image","Address","Note"};
            String[][] data = listStudent.get_studentList().stream()
                    .map(student -> new String[]{String.valueOf(student.get_id()), student.get_name(), String.valueOf(student.get_gpa()),
                            student.get_image(), student.get_address(), student.get_note()})
                    .toArray(String[][]::new);
            JTable table = new JTable(data, columnName);
            panelAllStudent.add(new JScrollPane(table));*/
            listStudent = new ListStudent();
            listStudent.importStudentListFromFileBinary("students.bin");
            studentTableModel = new StudentTableModel();
            studentTableModel.setStudentList(listStudent);
            tableStudent = new JTable(studentTableModel);
            imageRenderer = new ImageRenderer();

            tableStudent.getColumnModel().getColumn(3).setCellRenderer(imageRenderer);
            tableStudent.getColumnModel().getColumn(3).setPreferredWidth(100);

            tableStudent.getColumnModel().getColumn(1).setPreferredWidth(50);
            tableStudent.getColumnModel().getColumn(1).setPreferredWidth(100);
            //tableStudent.getColumnModel().getColumn(4).setPreferredWidth(150);
            tableStudent.getColumnModel().getColumn(5).setPreferredWidth(150);
            tableStudent.setRowHeight(100);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment( JLabel.CENTER );
            tableStudent.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            tableStudent.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

            tableStudent.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = tableStudent.rowAtPoint(e.getPoint());

                    ImageIcon imageIcon = (ImageIcon) tableStudent.getValueAt(row,3);
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    labelMainAvatar.setIcon(new ImageIcon(image));
                    labelMainAvatar.setText("");
                    /*imageRenderer.setClickedRow(row);
                    tableStudent.repaint();*/
                }
            });
            tableStudent.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    boolean rowSelected = tableStudent.getSelectedRow() !=-1;
                    buttonDelete.setEnabled(rowSelected);
                    buttonUpdate.setEnabled(rowSelected);
                    if (!rowSelected) {
                        labelMainAvatar.setIcon(null);
                        labelMainAvatar.setText("Avatar placeholder");
                    }
                }
            });
            //tableStudent.setPreferredSize(new Dimension(100,100));

            panelAllStudent.add(new JScrollPane(tableStudent),BorderLayout.CENTER);
        }
    }
    private void initPanelAllStudent() {
        panelAllStudent = new JPanel();
        panelAllStudent.setBackground(Color.WHITE);
        panelAllStudent.setLayout(new BorderLayout());
        initPanelStudentOperation();
        createTable();
        initTitlePanel();
        panelAllStudent.add(panelTitle, BorderLayout.PAGE_START);
    }
    JPanel panelTitle;
    private void initTitlePanel() {
        panelTitle = new JPanel();
        panelTitle.setLayout(new FlowLayout());
        JLabel labelTitle = new JLabel("STUDENT MANAGEMENT");
        panelTitle.add(labelTitle);
    }
    JButton buttonAddStudent;
    JButton buttonSort;
    JButton buttonUpdate;
    JButton buttonDelete;
    JButton buttonImportFromBinary;
    JButton buttonExportIntoBinary;
    JButton buttonImportFromCSV;
    JButton buttonExportIntoCSV;
    JPanel panelStudentOperation;
    JComboBox comboBoxType;
    JComboBox comboBoxOrder;
    int selectedRowTable = -1;
    JLabel labelMainAvatar;
    private void initPanelStudentOperation() {
        labelMainAvatar = new JLabel();
        labelMainAvatar.setIcon(null);
        labelMainAvatar.setText("Avatar placeholder");
        labelMainAvatar.setForeground(Color.BLACK);
        labelMainAvatar.setPreferredSize(new Dimension(50,50));
        buttonAddStudent = new JButton("Add student");
        buttonAddStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonSave.setVisible(true);
                buttonSave2.setVisible(false);
                cardLayout.show(mainPanel,"panelAddStudent");
            }
        });

        buttonSort = new JButton("Sort");
        buttonSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBoxOrder.getSelectedIndex()!=-1
                        && comboBoxOrder.getSelectedIndex()!=-1) {
                    String order = comboBoxOrder.getSelectedItem().toString();
                    String type = comboBoxType.getSelectedItem().toString();

                    if (order.equals("Ascending")){
                        if (type.equals("ID")) {
                            studentTableModel.sortByIdAscending();
                        } else if (type.equals("GPA")) {
                            studentTableModel.sortByGPAAscending();
                        }
                    } else if (order.equals("Descending")){
                        if (type.equals("ID")) {
                            studentTableModel.sortByIdDescending();
                        } else if (type.equals("GPA")) {
                            studentTableModel.sortByGPADescending();
                        }
                    }
                    // System.out.println(order+" - "+type);
                }
            }
        });

        buttonUpdate = new JButton("Update Student");
        buttonUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableStudent.getSelectedRow();
                if (selectedRow != -1) {
                    selectedRowTable = selectedRow;
                    cardLayout.show(mainPanel,"panelAddStudent");
                    buttonSave.setVisible(false);
                    buttonSave2.setVisible(true);
                   // buttonSave = getButtonSave2();
                    System.out.println(tableStudent.getModel().getValueAt(selectedRow,0));
                    textFieldId.setText(String.valueOf(studentTableModel.getValueAt(selectedRow,0)));
                    textFieldName.setText(String.valueOf(studentTableModel.getValueAt(selectedRow, 1)));
                    textFieldGpa.setText(String.valueOf(studentTableModel.getValueAt(selectedRow,2)));
                    textFieldAddress.setText(String.valueOf(studentTableModel.getValueAt(selectedRow,4)));
                    textFieldNote.setText(String.valueOf(studentTableModel.getValueAt(selectedRow,5)));
                  //  labelImage.setIcon();
                    /*int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    double gpa = Double.parseDouble(gpaField.getText());

                    Student updatedStudent = new Student(id, name, gpa);
                    studentTableModel.updateStudent(selectedRow, updatedStudent);*/
                }
            }
        });
        buttonUpdate.setEnabled(false);
        buttonDelete = new JButton("Delete Student");
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableStudent.getSelectedRow();
                if (selectedRow != -1) {
                   studentTableModel.deleteStudent(selectedRow);
                }
            }
        });
        buttonDelete.setEnabled(false);
        buttonImportFromBinary = new JButton("Import Binary");
        buttonImportFromBinary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readFileBinary();
            }
        });
        buttonExportIntoBinary = new JButton("Export Binary");
        buttonExportIntoBinary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFileBinary();
            }
        });
        buttonImportFromCSV     = new JButton("Import CSV");
        buttonImportFromCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readFileCSV();
            }
        });
        buttonExportIntoCSV     = new JButton("Export CSV");
        buttonExportIntoCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFileCSV();
            }
        });

        initComboBoxType();
        initComboBoxOrder();

        panelStudentOperation = new JPanel();
        panelStudentOperation.setLayout(new GridLayout(0,1));
        panelStudentOperation.add(labelMainAvatar);
        panelStudentOperation.add(comboBoxType);
        panelStudentOperation.add(comboBoxOrder);
        panelStudentOperation.add(buttonSort);
        panelStudentOperation.add(buttonAddStudent);
        panelStudentOperation.add(buttonUpdate);
        panelStudentOperation.add(buttonDelete);
        panelStudentOperation.add(buttonImportFromBinary);
        panelStudentOperation.add(buttonExportIntoBinary);
        panelStudentOperation.add(buttonImportFromCSV);
        panelStudentOperation.add(buttonExportIntoCSV);
        panelAllStudent.add(panelStudentOperation, BorderLayout.LINE_START);
    }
    private void saveFileCSV() {
        JFileChooser fileChooser = new JFileChooser();
        int selection = fileChooser.showSaveDialog(frame); // Pass parent component (optional)
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileFilter(new CSVFileFilter());
        if (selection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();

            // Check if file already exists (optional)
            if (selectedFile.exists()) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "File already exists. Overwrite?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) {
                    return; // User cancelled overwrite
                }

            }

            // Write your code to create and write data to the file
            try  {
                studentTableModel.getStudentList().exportStudentListToFileCSV(selectedFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void readFileCSV() {
        JFileChooser fileChooser = new JFileChooser();
        int selection = fileChooser.showOpenDialog(null); // Pass parent component (optional)
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileFilter(new CSVFileFilter());
        /*FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
        fileChooser.setFileFilter(filter);*/
        if (selection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
               studentTableModel.getStudentList().importStudentListFromFileCSV(selectedFile.getAbsolutePath());
               studentTableModel.fireTableDataChanged();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error reading file!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }

    }
    private void readFileBinary()  {
        JFileChooser fileChooser = new JFileChooser();
        int selection = fileChooser.showOpenDialog(null); // Pass parent component (optional)
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileFilter(new BinaryFileFilter());
        /*FileNameExtensionFilter filter = new FileNameExtensionFilter("Binary Files", "bin");
        fileChooser.setFileFilter(filter);*/
        if (selection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                studentTableModel.getStudentList().importStudentListFromFileBinary(selectedFile.getAbsolutePath());
                studentTableModel.fireTableDataChanged();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error reading file!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }

    }
    private void saveFileBinary()  {
        JFileChooser fileChooser = new JFileChooser();
        int selection = fileChooser.showSaveDialog(frame); // Pass parent component (optional)
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileFilter(new BinaryFileFilter());
        if (selection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();

            // Check if file already exists (optional)
            if (selectedFile.exists()) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "File already exists. Overwrite?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) {
                    return; // User cancelled overwrite
                }
            }

            // Write your code to create and write data to the file
            try  {
                studentTableModel.getStudentList().exportStudentListToFileBinary(selectedFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void initComboBoxType() {
        String[] type = new String[]{"ID","GPA"};
        comboBoxType = new JComboBox(type);
    }
    private void initComboBoxOrder() {
        String[] order = new String[]{"Ascending","Descending"};
        comboBoxOrder = new JComboBox(order);
    }
    JButton buttonSave;
    JPanel panelTextField;
    private void initPanelInformationStudentInAddPanel() {
        panelInformationStudent = new JPanel();
        panelInformationStudent.setBackground(Color.WHITE);
        panelTextField = new JPanel();
        panelTextField.setBackground(Color.WHITE);
        gridLayout = new GridLayout(0,2);
        panelTextField.setLayout(gridLayout);
        panelInformationStudent.setLayout(new BoxLayout(panelInformationStudent, BoxLayout.Y_AXIS));
        createPanelID();
        createPanelName();
        createPanelGpa();
        createPanelAddress();
        createPanelNote();
        createPanelImage();

        panelInformationStudent.add(panelTextField);
        initPanelSaveCancel();
        panelInformationStudent.add(panelSaveCancel);

    }
    JButton buttonCancel;
    JPanel panelSaveCancel;
    private void initPanelSaveCancel() {
        panelSaveCancel = new JPanel();
        panelSaveCancel.setBackground(Color.WHITE);
        panelSaveCancel.setLayout(new BoxLayout(panelSaveCancel,BoxLayout.X_AXIS));

        buttonSave = getButtonSave();
        buttonSave2 = getButtonSave2();
        buttonSave2.setVisible(false);
        buttonCancel = getButtonCancel();

        panelSaveCancel.add(buttonSave2);
        panelSaveCancel.add(buttonSave);
        panelSaveCancel.add(buttonCancel);
    }
    private  JButton getButtonCancel() {
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
                cardLayout.show(mainPanel,"panelAllStudent");
            }
        });
        return buttonCancel;
    }
    private void clearForm() {
        imagePath = "";
        textFieldId.setText("");
        textFieldName.setText("");
        textFieldGpa.setText("");
        textFieldNote.setText("");
        textFieldAddress.setText("");
    }
    private JButton getButtonSave() {
        //System.out.println("row: " +selectedRowTable);
        JButton buttonSave = new JButton("Save");
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isValidInput()) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. The ID is integer, the GPA is double, name cannot empty.");

                } else {
                    int id = Integer.parseInt(textFieldId.getText());
                    String name = textFieldName.getText();
                    double gpa = Double.parseDouble(textFieldGpa.getText());
                    String note = textFieldNote.getText();
                    String address = textFieldAddress.getText();
                    Student student = new Student(id, name, gpa, imagePath, address, note);
                    //System.out.println("row 2: " +selectedRowTable);
                    studentTableModel.addStudent(student);
                    clearForm();
                    labelImage.setIcon(null);
                    fileChooserGlobal.setSelectedFile(null);
                    //initPanelAllStudent();
                    cardLayout.show(mainPanel, "panelAllStudent");
                    imageRenderer.repaint();
                    tableStudent.updateUI();

                    studentTableModel.fireTableDataChanged();
                    tableStudent.requestFocus();
                }
            }
        });
        return buttonSave;
    }
    JButton buttonSave2;
    private JButton getButtonSave2() {
        //System.out.println("row: " +selectedRowTable);
        JButton buttonSave = new JButton("Save");
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isValidInput()) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. The ID is integer, the GPA is double, name cannot empty.");

                } else {
                    int id = Integer.parseInt(textFieldId.getText());
                    String name = textFieldName.getText();
                    double gpa = Double.parseDouble(textFieldGpa.getText());
                    String note = textFieldNote.getText();
                    String address = textFieldAddress.getText();
                    if (imagePath == null || imagePath.equals("")) {
                        imagePath = String.valueOf(studentTableModel.getStudentList().getStudentAt(selectedRowTable).get_image());
                    }
                    Student student = new Student(id, name, gpa, imagePath, address, note);

                    studentTableModel.updateStudent(selectedRowTable, student);
                    //studentTableModel.addStudent(student);
                    clearForm();
                    labelImage.setIcon(null);
                    fileChooserGlobal.setSelectedFile(null);
                    cardLayout.show(mainPanel, "panelAllStudent");
                    imageRenderer.repaint();
                    tableStudent.updateUI();
                    studentTableModel.fireTableDataChanged();
                    tableStudent.requestFocus();
                }
            }
        });
        return buttonSave;
    }
    JButton buttonChooseImage;
    JLabel labelImage;
    JPanel panelImage;
    private void createPanelImage() {
        labelImage = new JLabel();
        labelImage.setPreferredSize(new Dimension(100, 100));
        getButtonChooseImage();

        panelImage = new JPanel();
       /// panelImage.setLayout(new BoxLayout(panelImage, BoxLayout.Y_AXIS));
        panelImage.setLayout(new FlowLayout());
        panelImage.setBackground(Color.WHITE);
        panelImage.add(labelImage);
        panelImage.add(buttonChooseImage);

        panelTextField.add(panelImage);
    }
    String imagePath;
    JFileChooser fileChooserGlobal = new JFileChooser();
    private void getButtonChooseImage() {
        buttonChooseImage  = new JButton("Choose Image");
        buttonChooseImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooserGlobal.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooserGlobal.getSelectedFile();
                    ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
                    imagePath = FileUtility.copyFileFromSourceFolderToRootFolder(selectedFile.getAbsolutePath(), selectedFile.getName());
                    imagePath = "/Assets/"+selectedFile.getName();
                    System.out.println(imagePath);
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    labelImage.setIcon(new ImageIcon(scaledImage));
                }
                fileChooserGlobal.setSelectedFile(null);
            }
        });
    }
    JLabel labelNote;
    JTextField textFieldNote;
    JPanel panelNote;
    private void createPanelNote() {
        labelNote = new JLabel("Note");
        textFieldNote = new JTextField();

        panelNote = new JPanel();
        panelNote.setLayout(new BoxLayout(panelNote, BoxLayout.Y_AXIS));
        panelNote.setBackground(Color.LIGHT_GRAY);
        panelNote.add(labelNote);
        panelNote.add(textFieldNote);

        panelTextField.add(panelNote);
    }
    JLabel labelId;
    JTextField textFieldId;
    JPanel panelId;
    private void createPanelID() {
        labelId = new JLabel("ID");
        textFieldId = new JTextField();

        panelId = new JPanel();
        panelId.setLayout(new BoxLayout(panelId, BoxLayout.Y_AXIS));
        panelId.setBackground(Color.LIGHT_GRAY);
        panelId.add(labelId);
        textFieldId.setToolTipText("Enter an integer number");
        setHint(textFieldId,"Enter an integer number");
        panelId.add(textFieldId);
        panelTextField.add(panelId);
    }
    JLabel labelName;
    JTextField textFieldName;
    JPanel panelName;
    private void createPanelName() {
        labelName = new JLabel("Name");
        textFieldName = new JTextField();

        panelName = new JPanel();
        panelName.setLayout(new BoxLayout(panelName, BoxLayout.Y_AXIS));
        panelName.setBackground(Color.LIGHT_GRAY);
        panelName.add(labelName);
        panelName.add(textFieldName);
        panelTextField.add(panelName);
    }
    JLabel labelGpa;
    JTextField textFieldGpa;
    JPanel panelGpa;
    JFormattedTextField formattedTextFieldGpa;
    private void createPanelGpa() {
        labelGpa = new JLabel("GPA");
        textFieldGpa = new JTextField();
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Double.class);
        formatter.setMinimum(0);
        formatter.setMaximum(10);
        formatter.setAllowsInvalid(true);
        // If you want the value to be committed on each keystroke instead of focus lost
        formatter.setCommitsOnValidEdit(true);
        formattedTextFieldGpa = new JFormattedTextField(formatter);
        panelGpa = new JPanel();
        panelGpa.setLayout(new BoxLayout(panelGpa, BoxLayout.Y_AXIS));
        panelGpa.setBackground(Color.LIGHT_GRAY);
        panelGpa.add(labelGpa);
        //panelGpa.add(formattedTextFieldGpa);
        textFieldGpa.setToolTipText("Enter a double number");
        setHint(textFieldGpa,"Enter a double number");
        panelGpa.add(textFieldGpa);
        panelTextField.add(panelGpa);
    }
    private boolean isValidInput() {
        try {
            int id = Integer.parseInt(textFieldId.getText());
            double gpa = Double.parseDouble(textFieldGpa.getText());
            String name = textFieldName.getText();
            if (name==null || name.equals(""))
            {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    JLabel labelAddress;
    JTextField textFieldAddress;
    JPanel panelAddress;
    private void createPanelAddress() {
         labelAddress = new JLabel("Address");
         textFieldAddress = new JTextField();
         panelAddress = new JPanel();
         panelAddress.setLayout(new BoxLayout(panelAddress, BoxLayout.Y_AXIS));
         panelAddress.setBackground(Color.LIGHT_GRAY);
         panelAddress.add(labelAddress);
         panelAddress.add(textFieldAddress);
        panelTextField.add(panelAddress);
    }
    private void initPanelAddStudent() {
        //boxLayout = new BoxLayout(panelAddStudent,BoxLayout.Y_AXIS);
        panelAddStudent = new JPanel();
        panelAddStudent.setBackground(Color.WHITE);
        panelAddStudent.setLayout(new BoxLayout(panelAddStudent,BoxLayout.Y_AXIS));

        initPanelReturn();
        panelReturn.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelReturn.setAlignmentY(Component.TOP_ALIGNMENT);
        panelAddStudent.add(panelReturn);

        initPanelInformationStudentInAddPanel();
        panelAddStudent.add(panelInformationStudent);

    }

    JPanel panelReturn;
    private void initPanelReturn() {
        panelReturn = new JPanel();
        panelReturn.setLayout(new BoxLayout(panelReturn, BoxLayout.X_AXIS));
        panelReturn.setBackground(Color.WHITE);
        JButton buttonReturn = new JButton("Return");
        buttonReturn.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show (mainPanel,"panelAllStudent");
            }
        });
        panelReturn.add(buttonReturn);
    }
    private void initMainPanel() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.WHITE);
        initPanelAllStudent();
        initPanelAddStudent();

        mainPanel.add(panelAllStudent,"panelAllStudent");
        mainPanel.add(panelAddStudent,"panelAddStudent");

        cardLayout.show(mainPanel,"panelAllStudent");
    }
    public static void create() {
        JFrame frame = new JFrame("All Student Management");

        ListStudent listStudent = new ListStudent();
        listStudent.importStudentListFromFileBinary("students.bin");
        //listStudent.printStudentListByIdAscending();
        String[] columnName = new String[]{"ID","Name","GPA","Image","Address","Note"};
        String[][] data = listStudent.get_studentList().stream()
                .map(student -> new String[]{String.valueOf(student.get_id()), student.get_name(), String.valueOf(student.get_gpa()),
                        student.get_image(), student.get_address(), student.get_note()})
                .toArray(String[][]::new);

       /* for (String[] row: data) {
            for (String col: row) {
                System.out.print(col+" | ");
            }
            System.out.println();
        }*/

        /*DefaultTableModel model = new DefaultTableModel(data, columnName);
        JTable table = new JTable(model);*/

        JTable table = new JTable(data, columnName);

        // Creating instance of JButton
        JButton buttonAddStudent = new JButton("Add student");
        buttonAddStudent.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
               // System.out.println("them tml");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        frame.add(buttonAddStudent);
        frame.add(new JScrollPane(table));

        // using no layout managers
        // frame.setLayout(new BoxLayout(frame,BoxLayout.Y_AXIS));
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);

    }
    public static void setHint(JTextField textField, String hint) {
        Font originalFont = textField.getFont();
        Font italicFont = originalFont.deriveFont(Font.ITALIC);

        textField.setText(hint);
        textField.setForeground(Color.GRAY);
        textField.setFont(italicFont);

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(hint)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    textField.setFont(originalFont);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(hint);
                    textField.setForeground(Color.GRAY);
                    textField.setFont(italicFont);
                }
            }
        });
    }
}

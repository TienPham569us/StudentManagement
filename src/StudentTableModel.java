import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class StudentTableModel extends AbstractTableModel {
    private ListStudent listStudent;
    //private Map<String, ImageIcon> imageCache;
    final private String[] columnNames = {"ID", "Name", "GPA","Image","Address","Note"};

    public StudentTableModel() {
        listStudent = new ListStudent();
        //this.imageCache = new HashMap<>();

    }

    public void setStudentList(ListStudent students) {
        this.listStudent = students;

        // Create a CountDownLatch to track image loading progress
        /*CountDownLatch latch = new CountDownLatch(listStudent.getNumberOfStudent());
        // Pre-load the images and store them in the image cache
        String rootFolder = FileUtility.getRootProjectDirectory();
        for (int i=0;i<listStudent.getNumberOfStudent();i++) {
            String imagePath = rootFolder + "\\" + listStudent.getStudentAt(i).get_image();
            *//*ImageIcon imageIcon = new ImageIcon(getImage(imagePath));
            imageCache.put(imagePath, imageIcon);*//*
            loadImage(imagePath, latch);
        }

        try {
            // Wait for all images to be loaded
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        fireTableDataChanged();
    }

    /*private void loadImage(String imagePath, CountDownLatch latch) {
        ImageIconLoader loader = new ImageIconLoader(imagePath, latch);
        loader.start();
    }

    private class ImageIconLoader extends Thread {
        private String imagePath;
        private CountDownLatch latch;

        public ImageIconLoader(String imagePath, CountDownLatch latch) {
            this.imagePath = imagePath;
            this.latch = latch;
        }

        @Override
        public void run() {
            ImageIcon imageIcon = new ImageIcon(getImage(imagePath));
            imageCache.put(imagePath, imageIcon);
            latch.countDown();
        }
    }*/


    public ListStudent getStudentList() {
        return this.listStudent;
    }
    public void addStudent(Student student) {
        listStudent.addStudent(student);

        /*String rootFolder = FileUtility.getRootProjectDirectory();
        String imagePath = rootFolder + "\\" + student.get_image();
        ImageIcon imageIcon = new ImageIcon(getImage(imagePath));
        imageCache.put(imagePath, imageIcon);*/

        fireTableDataChanged();
    }
    public void deleteStudent(int index) {
        listStudent.deleteStudent(index);
        fireTableDataChanged();
    }
    public void updateStudent(int rowIndex, Student student) {
        System.out.println("stm row update:"+ rowIndex);
        listStudent.setStudent(rowIndex, student);

        /*String rootFolder = FileUtility.getRootProjectDirectory();
        String imagePath = rootFolder + "\\" + student.get_image();
        ImageIcon imageIcon = new ImageIcon(getImage(imagePath));
        imageCache.put(imagePath, imageIcon);*/

        fireTableDataChanged();
    }
    /*public void updateStudent(int index, int id, String name, double gpa, String image, String address, String note) {
        listStudent.updateStudent();
        fireTableDataChanged();
    }*/

   /* public void updateStudent(int index, Student student) {
        listStudent.setStudent(index, student);
        fireTableDataChanged();
    }*/
    public void sortByIdAscending() {
        //Collections.sort(listStudent, Comparator.comparingInt(Student::get_id));
        listStudent.sortStudentListByIdAscending();
        fireTableDataChanged();
    }
    public void sortByIdDescending() {
        //Collections.sort(listStudent, Comparator.comparingInt(Student::get_id));
        listStudent.sortStudentListByIdDescending();
        fireTableDataChanged();
    }

    public void sortByGPAAscending() {
        //Collections.sort(listStudent, Comparator.comparingDouble(Student::get_gpa));
        listStudent.sortStudentListByGPAAscending();
        fireTableDataChanged();
    }

    public void sortByGPADescending() {
        //Collections.sort(listStudent, Comparator.comparingDouble(Student::get_gpa));
        listStudent.sortStudentListByGPADescending();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return listStudent.getNumberOfStudent();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student student = listStudent.getStudentAt(rowIndex);
        String rootFolder = FileUtility.getRootProjectDirectory();
        switch (columnIndex) {
            case 0:
                return student.get_id();
            case 1:
                return student.get_name();
            case 2:
                return student.get_gpa();
            case 3:
                /*System.out.println(student.get_image());
                System.out.println(getImage(student.get_image()));*/
                return new ImageIcon(getImage(rootFolder + "\\"+student.get_image()));
                /*String imagePath = FileUtility.getRootProjectDirectory() + "\\" + student.get_image();
                return imageCache.get(imagePath);*/
            case 4:
                return student.get_address();
            case 5:
                return student.get_note();

            default:
                return null;
        }
    }
    private Image getImage(String path) {
        // Load and return an Image from the specified path
        return scaleImage(Toolkit.getDefaultToolkit().getImage(path),90,90);
       // return new Image(path);
    }
    private Image scaleImage(Image image, int targetWidth, int targetHeight) {
        BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();

        return scaledImage;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Integer.class;
        } else if (columnIndex == 2) {
            return Double.class;
        } else if (columnIndex == 3) {
            return ImageIcon.class;
        }
        return super.getColumnClass(columnIndex);
    }
}
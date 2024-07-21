import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

import static javax.swing.text.StyleConstants.setIcon;


class ImageRenderer extends DefaultTableCellRenderer{//implements TableCellRenderer {
    /*private final DefaultTableCellRenderer defaultRenderer;
    private int clickedRow = -1;

    public ImageRenderer() {
        defaultRenderer = new DefaultTableCellRenderer();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Image) {
            JLabel label = new JLabel();
            label.setHorizontalAlignment(SwingConstants.CENTER);
            ImageIcon icon = new ImageIcon((Image) value);
            label.setIcon(icon);
            label.setText("");
            *//*if (row == clickedRow) {

            } else {
                label.setIcon(null);
                label.setText("Click to view image");
            }*//*

            return label;
        } else {
            return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    public void setClickedRow(int row) {
        clickedRow = row;
    }*/
   /* private final DefaultTableCellRenderer defaultRenderer;

    public ImageRenderer() {
        defaultRenderer = new DefaultTableCellRenderer();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Image) {
            ImageIcon icon = new ImageIcon((Image) value);
            JLabel label = new JLabel(icon);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            return label;
        } else {
            return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }*/
    /*@Override
    protected void setValue(Object value) {
        //System.out.println("renderer: "+value);
        if (value instanceof Image )  {//|| value instanceof
            // Set the image as the icon for the renderer
            setIcon(new ImageIcon((Image) value));
        } else {
            // Clear the icon if the value is not an image
            System.out.println("null");
            setIcon(null);
        }
    }*/


   /* private ImageIcon icon;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setIcon((ImageIcon)value); // Store the image path as the value
        ImageIcon temp = (ImageIcon)value;
        icon = new ImageIcon(String.valueOf(temp));
        setText(""); // Remove default text rendering
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (icon == null) {
            //String imagePath = (String) getValue();
           // icon = new ImageIcon(getIcon());
        }

        if (icon != null) {
            int iconWidth = icon.getIconWidth();
            int iconHeight = icon.getIconHeight();
            int x = (getWidth() - iconWidth) / 2;
            int y = (getHeight() - iconHeight) / 2;
            icon.paintIcon(this, g, x, y);
        }
    }*/

    public ImageRenderer() {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
             boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,row, column);
        if (value instanceof Image) {
            setIcon(new ImageIcon((Image) value));
            setText("");
        } else if (value instanceof  ImageIcon) {
            //System.out.println("ImageIcon");
            setIcon((ImageIcon)value);
            setText("");
        } else {
            System.out.println("null");
            setIcon(null);
            setText((value==null) ? "" : value.toString());
        }

        return this;
    }
}
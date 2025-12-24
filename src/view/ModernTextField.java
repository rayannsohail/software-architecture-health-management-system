package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// A helper class to create modern JTextFields and JTextAreas
public class ModernTextField {

    public static JTextField createTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.DARK_GRAY);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(5, 10, 5, 10) // padding
        ));
        field.setCaretColor(new Color(33, 150, 243)); // blue caret
        return field;
    }

    public static JPasswordField createPasswordField(int columns) {
        JPasswordField field = new JPasswordField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.DARK_GRAY);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        field.setCaretColor(new Color(33, 150, 243));
        return field;
    }

    public static JTextArea createTextArea(int rows, int cols) {
        JTextArea area = new JTextArea(rows, cols);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setForeground(Color.DARK_GRAY);
        area.setBackground(Color.WHITE);
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        area.setCaretColor(new Color(33, 150, 243));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }
}

package view;

import controller.PatientController;
import model.Patient;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.MaskFormatter;

public class PatientView extends JPanel {

    private PatientController controller;

    private JLabel lblPatientId;
    private JTextField txtFirstName, txtLastName, txtNhs;
    private JComboBox<String> cmbGender;
    private JFormattedTextField dateField;
    private JFormattedTextField phoneField;
    private JTextField txtEmail;
    private JTextField txtAddress, txtPostcode;
    private JTextField txtEmergencyName, txtEmergencyPhone;
    private JFormattedTextField registrationDateField;
    private JLabel lblGpSurgery;
    private JButton btnAdd;
    private boolean isEditing = false;
    private Patient editingPatient;
    private JLabel title;

    public PatientView() {

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));


        title = new JLabel("Register New Patient");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(76, 175, 80)); 
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);


        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(true);
        form.setBackground(new Color(250, 250, 250));
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 12, 10, 12);
        gc.fill = GridBagConstraints.HORIZONTAL;

        lblPatientId = new JLabel("P001");

        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            dateField = new JFormattedTextField(dateMask);
            dateField.setHorizontalAlignment(JTextField.LEFT);
            dateField.setValue(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        } catch (Exception e) {
            dateField = new JFormattedTextField();
            dateField.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        }
        txtNhs = new JTextField();
        cmbGender = new JComboBox<>(new String[]{"Male", "Female", "Not to specify"});
        phoneField = new JFormattedTextField();
        ((AbstractDocument) phoneField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        txtEmail = new JTextField();
        txtAddress = new JTextField();
        txtPostcode = new JTextField();
        txtEmergencyName = new JTextField();
        txtEmergencyPhone = new JTextField();
        ((AbstractDocument) txtEmergencyPhone.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        try {
            MaskFormatter regDateMask = new MaskFormatter("##/##/####");
            regDateMask.setPlaceholderCharacter('_');
            registrationDateField = new JFormattedTextField(regDateMask);
            registrationDateField.setHorizontalAlignment(JTextField.LEFT);
            registrationDateField.setValue(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        } catch (Exception e) {
            registrationDateField = new JFormattedTextField();
            registrationDateField.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        }
        lblGpSurgery = new JLabel("S001");

        int row = 0;
        addRow(form, gc, row++, "Patient ID", lblPatientId);
        addRow(form, gc, row++, "First Name", txtFirstName);
        addRow(form, gc, row++, "Last Name", txtLastName);
        addRow(form, gc, row++, "Date of Birth (DD/MM/YYYY)", dateField);
        addRow(form, gc, row++, "NHS Number", txtNhs);
        addRow(form, gc, row++, "Gender", cmbGender);
        addRow(form, gc, row++, "Phone", phoneField);
        addRow(form, gc, row++, "Email", txtEmail);
        addRow(form, gc, row++, "Address", txtAddress);
        addRow(form, gc, row++, "Postcode", txtPostcode);
        addRow(form, gc, row++, "Emergency Name", txtEmergencyName);
        addRow(form, gc, row++, "Emergency Phone", txtEmergencyPhone);
        addRow(form, gc, row++, "Registration Date (DD/MM/YYYY)", registrationDateField);
        addRow(form, gc, row++, "GP Surgery ID", lblGpSurgery);

        add(form, BorderLayout.CENTER);


        btnAdd = new JButton("Register Patient");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdd.addActionListener(e -> onAdd());

        JPanel bottom = new JPanel();
        bottom.add(btnAdd);
        add(bottom, BorderLayout.SOUTH);
    }

    private void addRow(JPanel panel, GridBagConstraints gc, int row,
                        String label, JComponent field) {

        gc.gridy = row;

        gc.gridx = 0;
        gc.weightx = 0.3;
        panel.add(new JLabel(label), gc);

        gc.gridx = 1;
        gc.weightx = 0.7;
        panel.add(field, gc);
    }

    public void setController(PatientController controller) {
        this.controller = controller;
        lblGpSurgery.setText(controller.generateGpId());
    }

    public void updateIdLabel(String id) {
        lblPatientId.setText(id);
    }

    public void updateGpIdLabel(String id) {
        lblGpSurgery.setText(id);
    }

    public void editPatient(Patient p) {
        isEditing = true;
        editingPatient = p;
        title.setText("Edit Patient");
        btnAdd.setText("Update Patient");
        lblPatientId.setText(p.getId());
        txtFirstName.setText(p.getFirstName());
        txtLastName.setText(p.getLastName());
        dateField.setText(p.getDateOfBirth());
        txtNhs.setText(p.getNhsNumber());
        cmbGender.setSelectedItem(p.getGender());
        phoneField.setText(p.getPhoneNumber());
        txtEmail.setText(p.getEmail());
        txtAddress.setText(p.getAddress());
        txtPostcode.setText(p.getPostcode());
        txtEmergencyName.setText(p.getEmergencyContactName());
        txtEmergencyPhone.setText(p.getEmergencyContactPhone());
        registrationDateField.setText(p.getRegistrationDate());
        lblGpSurgery.setText(p.getGpSurgeryId());
    }

    private void resetToNewMode() {
        isEditing = false;
        editingPatient = null;
        title.setText("Register New Patient");
        btnAdd.setText("Register Patient");
        clearFields();
        lblPatientId.setText(controller.generateNewId());
        lblGpSurgery.setText(controller.generateGpId());
    }

    private void clearFields() {
        txtFirstName.setText("");
        txtLastName.setText("");
        dateField.setText("");
        txtNhs.setText("");
        cmbGender.setSelectedIndex(0);
        phoneField.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtPostcode.setText("");
        txtEmergencyName.setText("");
        txtEmergencyPhone.setText("");
        registrationDateField.setText("");
    }

    private void onAdd() {
        if (controller == null) return;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String patientId = lblPatientId.getText();

        Patient p;
        if (isEditing && editingPatient != null) {
            p = editingPatient;
        } else {
            p = new Patient();
        }

        p.setId(patientId);
        p.setFirstName(txtFirstName.getText());
        p.setLastName(txtLastName.getText());
        p.setDateOfBirth(dateField.getText());
        p.setNhsNumber(txtNhs.getText());
        p.setGender((String) cmbGender.getSelectedItem());
        p.setPhoneNumber(phoneField.getText());
        p.setEmail(txtEmail.getText());
        p.setAddress(txtAddress.getText());
        p.setPostcode(txtPostcode.getText());
        p.setEmergencyContactName(txtEmergencyName.getText());
        p.setEmergencyContactPhone(txtEmergencyPhone.getText());
        p.setRegistrationDate(registrationDateField.getText());
        p.setGpSurgeryId(lblGpSurgery.getText());

        if (isEditing) {
            controller.updatePatient(p);
            JOptionPane.showMessageDialog(this, "Patient updated successfully!");
            resetToNewMode();
        } else {
            controller.addPatient(p);
            JOptionPane.showMessageDialog(this, "Patient registered successfully!");
        }
    }
}

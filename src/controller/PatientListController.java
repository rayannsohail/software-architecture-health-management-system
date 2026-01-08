package controller;

import model.Patient;
import model.PatientRepository;
import view.PatientListView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class PatientListController {

    private final PatientRepository repository;
    private final PatientListView view;
    private PatientController patientController;

    public PatientListController(PatientRepository repository, PatientListView view) {
        this.repository = repository;
        this.view = view;
        this.view.setController(this);
        refreshView();
    }

    public void setPatientController(PatientController patientController) {
        this.patientController = patientController;
    }

    public PatientListView getView() {
        return view;
    }

    public void refreshView() {
        view.setPatients(repository.getAll());
    }

    public void deleteSelected() {
        int row = view.getTable().getSelectedRow();
        if (row >= 0) {
            String id = (String) view.getTable().getValueAt(row, 0);
            Patient p = repository.findById(id);
            if (p != null) {
                repository.remove(p);
                repository.saveAll();
                refreshView();
                javax.swing.JOptionPane.showMessageDialog(view, "Patient deleted successfully!");
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(view, "Please select a patient to delete.");
        }
    }

    public void editSelected() {
        int row = view.getTable().getSelectedRow();
        if (row >= 0) {
            String id = (String) view.getTable().getValueAt(row, 0);
            Patient p = repository.findById(id);
            if (p != null) {
                showEditDialog(p);
            }
        } else {
            JOptionPane.showMessageDialog(view, "Please select a patient to edit.");
        }
    }

    private void showEditDialog(Patient p) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(view), "Edit Patient", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(view);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields
        JTextField txtFirstName = new JTextField(p.getFirstName());
        JTextField txtLastName = new JTextField(p.getLastName());
        JTextField txtDateOfBirth = new JTextField(p.getDateOfBirth());
        JTextField txtNhs = new JTextField(p.getNhsNumber());
        JComboBox<String> cmbGender = new JComboBox<>(new String[]{"Male", "Female", "Not to specify"});
        cmbGender.setSelectedItem(p.getGender());
        JTextField txtPhone = new JTextField(p.getPhoneNumber());
        JTextField txtEmail = new JTextField(p.getEmail());
        JTextField txtAddress = new JTextField(p.getAddress());
        JTextField txtPostcode = new JTextField(p.getPostcode());
        JTextField txtEmergencyName = new JTextField(p.getEmergencyContactName());
        JTextField txtEmergencyPhone = new JTextField(p.getEmergencyContactPhone());
        JTextField txtRegistrationDate = new JTextField(p.getRegistrationDate());
        JTextField txtGpSurgeryId = new JTextField(p.getGpSurgeryId());

        // components
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1; panel.add(txtFirstName, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1; panel.add(txtLastName, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1; panel.add(txtDateOfBirth, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("NHS Number:"), gbc);
        gbc.gridx = 1; panel.add(txtNhs, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1; panel.add(cmbGender, gbc);

        gbc.gridx = 0; gbc.gridy = 5; panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; panel.add(txtPhone, gbc);

        gbc.gridx = 0; gbc.gridy = 6; panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; panel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 7; panel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; panel.add(txtAddress, gbc);

        gbc.gridx = 0; gbc.gridy = 8; panel.add(new JLabel("Postcode:"), gbc);
        gbc.gridx = 1; panel.add(txtPostcode, gbc);

        gbc.gridx = 0; gbc.gridy = 9; panel.add(new JLabel("Emergency Name:"), gbc);
        gbc.gridx = 1; panel.add(txtEmergencyName, gbc);

        gbc.gridx = 0; gbc.gridy = 10; panel.add(new JLabel("Emergency Phone:"), gbc);
        gbc.gridx = 1; panel.add(txtEmergencyPhone, gbc);

        gbc.gridx = 0; gbc.gridy = 11; panel.add(new JLabel("Registration Date:"), gbc);
        gbc.gridx = 1; panel.add(txtRegistrationDate, gbc);

        gbc.gridx = 0; gbc.gridy = 12; panel.add(new JLabel("GP Surgery ID:"), gbc);
        gbc.gridx = 1; panel.add(txtGpSurgeryId, gbc);

        dialog.add(panel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update patient
                p.setFirstName(txtFirstName.getText());
                p.setLastName(txtLastName.getText());
                p.setDateOfBirth(txtDateOfBirth.getText());
                p.setNhsNumber(txtNhs.getText());
                p.setGender((String) cmbGender.getSelectedItem());
                p.setPhoneNumber(txtPhone.getText());
                p.setEmail(txtEmail.getText());
                p.setAddress(txtAddress.getText());
                p.setPostcode(txtPostcode.getText());
                p.setEmergencyContactName(txtEmergencyName.getText());
                p.setEmergencyContactPhone(txtEmergencyPhone.getText());
                p.setRegistrationDate(txtRegistrationDate.getText());
                p.setGpSurgeryId(txtGpSurgeryId.getText());

                repository.saveAll();
                refreshView();
                dialog.dispose();
                JOptionPane.showMessageDialog(view, "Patient updated successfully!");
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
}
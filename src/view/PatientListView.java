package view;

import controller.PatientListController;
import model.Patient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientListView extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private PatientListController controller;

    public PatientListView() {
        setLayout(new BorderLayout());

        String[] columns = {
            "ID", "First Name", "Last Name", "DOB", "NHS Number", "Gender",
            "Phone", "Email", "Address", "Postcode", "Emergency Name",
            "Emergency Phone", "Registration Date", "GP Surgery ID"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // Title
        JLabel title = new JLabel("All Patients");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // Buttons Panel
        JPanel bottom = new JPanel();
        JButton btnEdit = new JButton("Edit Selected Patient");
        btnEdit.addActionListener(e -> {
            if (controller != null) {
                controller.editSelected();
            }
        });
        bottom.add(btnEdit);

        JButton btnDelete = new JButton("Delete Selected Patient");
        btnDelete.addActionListener(e -> {
            if (controller != null) {
                controller.deleteSelected();
            }
        });
        bottom.add(btnDelete);
        add(bottom, BorderLayout.SOUTH);
    }

    public void setController(PatientListController controller) {
        this.controller = controller;
    }

    public JTable getTable() {
        return table;
    }

    public void setPatients(List<Patient> patients) {
        model.setRowCount(0);
        for (Patient p : patients) {
            model.addRow(new Object[]{
                p.getId(),
                p.getFirstName(),
                p.getLastName(),
                p.getDateOfBirth(),
                p.getNhsNumber(),
                p.getGender(),
                p.getPhoneNumber(),
                p.getEmail(),
                p.getAddress(),
                p.getPostcode(),
                p.getEmergencyContactName(),
                p.getEmergencyContactPhone(),
                p.getRegistrationDate(),
                p.getGpSurgeryId()
            });
        }
    }
}
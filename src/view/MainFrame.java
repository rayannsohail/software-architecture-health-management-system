package view;

import controller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private enum UserRole {
        PATIENT,
        CLINICIAN,
        ADMINISTRATOR
    }

    private PatientController pc;
    private PatientListController plc;
    private ClinicianController cc;
    private AppointmentController ac;
    private PrescriptionController prc;
    private ReferralController rc;

    private JPanel roleSelectionPanel;
    private JTabbedPane tabs;

    public MainFrame(
            PatientController pc,
            PatientListController plc,
            ClinicianController cc,
            AppointmentController ac,
            PrescriptionController prc,
            ReferralController rc) {

        super("Healthcare Management System (HMS)");

        this.pc = pc;
        this.plc = plc;
        this.cc = cc;
        this.ac = ac;
        this.prc = prc;
        this.rc = rc;

        initializeRoleSelection();
        setContentPane(roleSelectionPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void initializeRoleSelection() {
        roleSelectionPanel = new JPanel();
        roleSelectionPanel.setLayout(new BorderLayout());
        roleSelectionPanel.setBackground(new Color(240, 248, 240));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(240, 248, 240));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 20, 40));

        // Main Title
        JLabel mainTitle = new JLabel("Healthcare Management System");
        mainTitle.setFont(new Font("SansSerif", Font.BOLD, 32));
        mainTitle.setForeground(new Color(46, 125, 50));
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitle = new JLabel("Please select your role to continue");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtitle.setForeground(new Color(69, 90, 100));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(mainTitle);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(subtitle);

        // Roles Panel
        JPanel rolesPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        rolesPanel.setBackground(new Color(240, 248, 240));
        rolesPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        // Patient Role Card
        JPanel patientCard = createRoleCard("Patient", "Access your personal health records and appointments",
                                          new Color(76, 175, 80), UserRole.PATIENT);
        rolesPanel.add(patientCard);

        // Clinician Role Card
        JPanel clinicianCard = createRoleCard("Clinician", "Manage patients, appointments, referrals and prescriptions",
                                            new Color(33, 150, 243), UserRole.CLINICIAN);
        rolesPanel.add(clinicianCard);

        // Administrator Role Card
        JPanel adminCard = createRoleCard("Administrator", "Oversee clinicians, appointments and referrals",
                                        new Color(156, 39, 176), UserRole.ADMINISTRATOR);
        rolesPanel.add(adminCard);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(240, 248, 240));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40));

        JLabel footerLabel = new JLabel("© 2025 Healthcare Management System");
        footerLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        footerLabel.setForeground(new Color(149, 165, 166));
        footerPanel.add(footerLabel);

        roleSelectionPanel.add(headerPanel, BorderLayout.NORTH);
        roleSelectionPanel.add(rolesPanel, BorderLayout.CENTER);
        roleSelectionPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createRoleCard(String title, String description, Color accentColor, UserRole role) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224), 1),
            BorderFactory.createEmptyBorder(25, 20, 25, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Make card clickable
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showRoleInterface(role);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(new Color(248, 248, 248));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(Color.WHITE);
            }
        });

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(accentColor);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));

        // Description
        JLabel descLabel = new JLabel("<html><center>" + description + "</center></html>");
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        descLabel.setForeground(new Color(97, 97, 97));
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(titleLabel, BorderLayout.CENTER);
        card.add(descLabel, BorderLayout.SOUTH);

        return card;
    }

    private void showRoleInterface(UserRole role) {
        tabs = new JTabbedPane();

        switch (role) {
            case PATIENT:
                // Patient: Patients page and Appointments page
                tabs.addTab("Patients", pc.getView());
                ac.getView().setPatientMode(true);
                tabs.addTab("Appointments", ac.getView());
                break;

            case CLINICIAN:
                // Clinician: All Patients page, Appointments page, Referrals page, and Prescriptions page
                tabs.addTab("All Patients", plc.getView());
                ac.getView().setPatientMode(false);
                tabs.addTab("Appointments", ac.getView());
                tabs.addTab("Referrals", rc.getView());
                tabs.addTab("Prescriptions", prc.getView());
                break;

            case ADMINISTRATOR:
                // Administrator: Clinicians page, Appointments page, and Referrals page
                tabs.addTab("Clinicians", cc.getView());
                ac.getView().setPatientMode(false);
                tabs.addTab("Appointments", ac.getView());
                tabs.addTab("Referrals", rc.getView());
                break;
        }

        // Add a back button to return to role selection
        JPanel tabPanel = new JPanel(new BorderLayout());
        JButton backButton = new JButton("← Back to Role Selection");
        backButton.setBackground(new Color(76, 175, 80));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            setContentPane(roleSelectionPanel);
            revalidate();
            repaint();
        });

        tabPanel.add(backButton, BorderLayout.NORTH);
        tabPanel.add(tabs, BorderLayout.CENTER);

        setContentPane(tabPanel);
        revalidate();
        repaint();
    }
}

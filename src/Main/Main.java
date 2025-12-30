package Main;

import controller.*;
import model.*;
import view.*;
import javax.swing.*;
import java.awt.*;

// Import FlatLaf
import com.formdev.flatlaf.FlatLightLaf;

public class Main {

    public static void main(String[] args) {

        // ✅ Set modern FlatLaf theme
        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }

        SwingUtilities.invokeLater(() -> {

            String basePath = System.getProperty("user.dir");
            String dataPath = basePath + "/src/data/";

            try {
                // ================================
                // REPOSITORIES
                // ================================
                PatientRepository pr = new PatientRepository(dataPath + "patients.csv");
                ClinicianRepository cr = new ClinicianRepository(dataPath + "clinicians.csv");
                FacilityRepository fr = new FacilityRepository(dataPath + "facilities.csv");
                AppointmentRepository ar = new AppointmentRepository(dataPath + "appointments.csv");
                PrescriptionRepository pResR = new PrescriptionRepository(dataPath + "prescriptions.csv");
                ReferralRepository rR = new ReferralRepository(dataPath + "referrals.csv");

                // ================================
                // REFERRAL MANAGER
                // ================================
                ReferralManager rm = ReferralManager.getInstance(
                        rR, pr, cr, fr,
                        dataPath + "referrals_output.txt"
                );

                // ================================
                // VIEWS
                // ================================
                PatientView pv = new PatientView();
                PatientListView plv = new PatientListView();
                ClinicianView cv = new ClinicianView();
                AppointmentView av = new AppointmentView();
                PrescriptionView presV = new PrescriptionView();
                ReferralView rv = new ReferralView();

                // ================================
                // CONTROLLERS
                // ================================
                PatientListController plc = new PatientListController(pr, plv);
                PatientController pc = new PatientController(pr, pv, plc);
                plc.setPatientController(pc);
                ClinicianController cc = new ClinicianController(cr, cv);
                AppointmentController ac = new AppointmentController(ar, pr, cr, fr, av);
                PrescriptionController prc = new PrescriptionController(pResR, pr, cr, ar, presV);
                ReferralController rc = new ReferralController(rm, pr, cr, fr, ar, rv);

                // ================================
                // MAIN FRAME
                // ================================
                MainFrame frame = new MainFrame(pc, plc, cc, ac, prc, rc);
                frame.setSize(1000, 600); // Larger size for professional role selection screen
                frame.setLocationRelativeTo(null);
                
                // ✅ Modern UI tweaks with light green theme
                UIManager.put("Button.arc", 20);
                UIManager.put("Component.focusWidth", 2);
                UIManager.put("Button.background", new Color(76, 175, 80)); // Light green
                UIManager.put("Button.foreground", Color.WHITE);
                UIManager.put("Panel.background", new Color(240, 248, 240)); // Very light green
                UIManager.put("TabbedPane.background", new Color(240, 248, 240));
                UIManager.put("Table.selectionBackground", new Color(76, 175, 80));
                UIManager.put("Table.selectionForeground", Color.WHITE);

                SwingUtilities.updateComponentTreeUI(frame);

                frame.setVisible(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error loading data files:\n" + e.getMessage(),
                        "Data Load Error",
                        JOptionPane.ERROR_MESSAGE
                );
                e.printStackTrace();
            }
        });
    }
}

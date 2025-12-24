package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository {

    private final List<Patient> patients = new ArrayList<>();
    private final String csvPath;

    public PatientRepository(String csvPath) {
        this.csvPath = csvPath;
        load();
    }

    public List<String> getAllIds() {
    List<String> ids = new ArrayList<>();
    for (Patient p : patients) ids.add(p.getId());
    return ids;
}

    // ============================================================
    // LOAD PATIENTS FROM CSV (all 14 fields)
    // ============================================================
    private void load() {
        try {
            for (String[] row : CsvUtils.readCsv(csvPath)) {

                Patient p = new Patient(
                        row[0],   // patient_id
                        row[1],   // first_name
                        row[2],   // last_name
                        row[3],   // date_of_birth
                        row[4],   // nhs_number
                        row[5],   // gender
                        row[6],   // phone_number
                        row[7],   // email
                        row[8],   // address
                        row[9],   // postcode
                        row[10],  // emergency_contact_name
                        row[11],  // emergency_contact_phone
                        row[12],  // registration_date
                        row[13]   // gp_surgery_id
                );

                patients.add(p);
            }

        } catch (IOException ex) {
            System.err.println("Failed to load patients: " + ex.getMessage());
        }
    }

    // ============================================================
    // AUTO-ID GENERATOR  (Fills gaps: P001, P002, P003, ...)
    // ============================================================
    public String generateNewId() {
        java.util.Set<Integer> existing = new java.util.HashSet<>();
        for (Patient p : patients) {
            try {
                int num = Integer.parseInt(p.getId().substring(1));
                existing.add(num);
            } catch (Exception ignore) {}
        }
        int next = 1;
        while (existing.contains(next)) next++;
        return String.format("P%03d", next);
    }

    // ============================================================
    // ADD PATIENT + APPEND TO CSV
    // ============================================================
    public void addAndAppend(Patient p) {
        patients.add(p);

        try {
            CsvUtils.appendLine(csvPath, new String[]{
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

        } catch (IOException ex) {
            System.err.println("Failed to append patient: " + ex.getMessage());
        }
    }

    public List<Patient> getAll() {
        return patients;
    }

    public void remove(Patient p) {
        patients.remove(p);
    }

    public Patient findById(String id) {
        for (Patient p : patients) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    // ============================================================
    // SAVE ALL PATIENTS TO CSV
    // ============================================================
    public void saveAll() {
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(csvPath))) {
            bw.write("patient_id,first_name,last_name,date_of_birth,nhs_number,gender,phone_number,email,address,postcode,emergency_contact_name,emergency_contact_phone,registration_date,gp_surgery_id\n");
            for (Patient p : patients) {
                bw.write(String.join(",",
                    p.getId(), p.getFirstName(), p.getLastName(), p.getDateOfBirth(), p.getNhsNumber(), p.getGender(),
                    p.getPhoneNumber(), p.getEmail(), p.getAddress(), p.getPostcode(), p.getEmergencyContactName(),
                    p.getEmergencyContactPhone(), p.getRegistrationDate(), p.getGpSurgeryId()
                ));
                bw.newLine();
            }
        } catch (java.io.IOException e) {
            System.err.println("Failed to save patients: " + e.getMessage());
        }
    }
}

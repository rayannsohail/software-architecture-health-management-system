package controller;

import model.Patient;
import model.PatientRepository;
import view.PatientView;

public class PatientController {

    private final PatientRepository repository;
    private final PatientView view;
    private final PatientListController listController;

    public PatientController(PatientRepository repository, PatientView view, PatientListController listController) {
        this.repository = repository;
        this.view = view;
        this.listController = listController;
        this.view.setController(this);
        this.view.updateIdLabel(repository.generateNewId());
        // ❌ NO refreshView()
    }

    public PatientView getView() {
        return view;
    }


    public void addPatient(Patient p) {
        repository.addAndAppend(p);
        if (listController != null) {
            listController.refreshView();
        }
        view.updateIdLabel(repository.generateNewId());
        view.updateGpIdLabel(repository.generateNewId());
    }


    public void updatePatient(Patient p) {
        repository.saveAll();
        if (listController != null) {
            listController.refreshView();
        }
    }


    public void editPatient(Patient p) {
        view.editPatient(p);
    }


    public String generateNewId() {
        return repository.generateNewId();
    }


    public String generateGpId() {
        java.util.Set<Integer> existing = new java.util.HashSet<>();
        for (Patient p : repository.getAll()) {
            try {
                String gpId = p.getGpSurgeryId();
                if (gpId.startsWith("S")) {
                    int num = Integer.parseInt(gpId.substring(1));
                    existing.add(num);
                }
            } catch (Exception ignore) {}
        }
        int next = 1;
        while (existing.contains(next)) next++;
        return String.format("S%03d", next);
    }
}

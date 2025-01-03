package Client;

import exportmanagerInterface.ExportCriteria;
import exportmanagerInterface.IExportManager;

/*
Dies ist unsere schnittstelle zwischen Backend (Business logic) und GUI.

Nehmen wir an. Ein Benutzer klickt auf einen Button im UI, der zumbeispiel für das exportieren zuständlich ist.


*/
public class Access {

    public IExportManager exportManager;

    public void exportToPdf(ExportCriteria criteria) {
        exportManager.export(criteria);
    }

}

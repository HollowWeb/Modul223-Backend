package exportmanagerService;

import exportmanagerInterface.ExportCriteria;
import exportmanagerInterface.ExportResponse;

public class ExportManager {

    /*

    Hier kommt die eigentliche Logik hin. für das Exportieren.

    Diese Methode wird dann von der IExportManager klasse die von der Access Klasse (also unsere pseudo API aufgerufen wird) getriggert.
    */

    ExportResponse export(ExportCriteria criteria) {
        ExportResponse result = new ExportResponse("");
        /*
        exportFile(criteria);
        */

        return result;

    }
}

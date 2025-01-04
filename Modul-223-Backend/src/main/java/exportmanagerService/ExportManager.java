package exportmanagerService;

import exportmanagerInterface.ExportCriteria;
import exportmanagerInterface.ExportResponse;
import exportmanagerInterface.IExportManager;

/**
 * Manages the export to a pdf file.
 */
public class ExportManager implements IExportManager {

    @Override
    public ExportResponse export(ExportCriteria criteria) {
        ExportResponse result = new ExportResponse("");
        /*
        Hier kommt die eigentliche Logik hin. für das Exportieren.
        Diese Methode wird dann von der IExportManager klasse die von der Access Klasse (also unsere pseudo API aufgerufen wird) getriggert.
        */

        return result;
    }



}

package export.exportmanagerInterface;


/**
 * Our Interface for the Export service including the export method.
 */
public interface IExportManager {

    /*
    Das ist die Schnittstelle des Managers !!

    Diese "export" Methode wird dann in der "ExportManager" Klasse gehanhabt. und wie wir sehen gibt die ExportResponse dann das "Resultat" oder fehler meldung zurück.
    In unserem fall wird das Exportieren kein Ergebnis zurückgegeben werden, da nur ein File im Rechner erstellt wird.
    */
    ExportResponse export(ExportCriteria criteria);

}

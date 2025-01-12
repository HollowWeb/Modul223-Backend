package export.exportmanagerInterface;

/**
 * Includes the error message data.
 */
public class ExportResponse {

    /*
    Das ist die Antwort Klasse. D.h die liefert am schluss das Resultat oder eine Fehlermeldung sobald eine passiert.
     */

    public String errormessage;

    public ExportResponse(String errormessage) {
        this.errormessage = errormessage;
    }
}

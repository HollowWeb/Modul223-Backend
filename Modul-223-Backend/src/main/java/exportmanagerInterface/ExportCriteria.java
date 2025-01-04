package exportmanagerInterface;

/**
 * Includes the criteria data for the export.
 */
public class ExportCriteria {

    /*
    Das ist die Kriterien klasse. Also Datenklasse die alle wichtige Attribute für das Exportieren hält.
    z.B:
     */

    public String content;
    public String title;
    public String author;

    /*
    und so weiter
     */

    /**
     * Initializes a new <see="ExportCriteria">ExportCriteria</see="ExportCriteria"> class with the criteria data.
     * @param content maybe content of the page.
     * @param title Title of the page for further filtering use cases.
     * @param author author who has written on the page for monitoring use cases.
     */
    public ExportCriteria(String content, String title, String author) {
        this.content = content;
        this.title = title;
        this.author = author;
    }
}

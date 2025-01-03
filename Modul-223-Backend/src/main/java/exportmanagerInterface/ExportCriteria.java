package exportmanagerInterface;

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

    public ExportCriteria(String content, String title, String author) {
        this.content = content;
        this.title = title;
        this.author = author;
    }


}

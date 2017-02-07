package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by dan on 07/02/17.
 *
 * Model for a competence
 */
public class Competence {

    // Description of the competence
    private final StringProperty description;

    // Id of the competence
    private final StringProperty id;

    /**
     * Constructor
     * @param desc
     * @param id
     */
    public Competence (String desc, String id) {
        description = new SimpleStringProperty(desc);
        this.id = new SimpleStringProperty(id);
    }

    /**
     * Description getter.
     * @return
     */
    public String getDescription() { return description.get(); }

    /**
     * Description property.
     * @return
     */
    public StringProperty descriptionProperty() { return description; }

    /**
     * Id getter.
     * @return
     */
    public String getId() { return id.get(); }

    /**
     * Id property.
     * @return
     */
    public StringProperty idProperty() { return id; }
}

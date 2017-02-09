package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Created by dan on 07/02/17.
 * @author Daniele Paolini
 * Rating model class.
 */
public class Rating {

    // Attributes
    private StringProperty competence;
    private StringProperty rate;
    private String valutationID;

    /**
     * Constructor
     * @param rid
     * @param comp
     * @param rt
     */
    public Rating(String rid, String comp, String rt) {
        valutationID = rid;
        competence = new SimpleStringProperty(comp);
        rate = new SimpleStringProperty(rt);
    }

    /**
     * Valuation ID getter.
     * @return
     */
    public String getValutationID() { return valutationID; }

    /**
     * Valuation ID setter.
     * @param vid
     */
    public void setValutationID(String vid) { valutationID = vid; }

    /**
     * Competence getter.
     * @return
     */
    public String getCompetence() { return competence.get(); }

    /**
     * Competence setter.
     * @param comp
     */
    public void setCompetence(String comp) {competence.set(comp);}

    /**
     * Rate getter.
     * @return
     */
    public String getRate() { return rate.get(); }

    /**
     * Rate setter.
     * @param rt
     */
    public void setRate(String rt) { rate.set(rt); }

    /**
     * Competence property.
     * @return
     */
    public StringProperty competenceProperty() { return competence; }

    /**
     * Rate property.
     * @return
     */
    public StringProperty rateProperty() { return rate; }
}

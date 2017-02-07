package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

enum Rate {
    A,
    B,
    C,
    D
}

/**
 * Created by dan on 07/02/17.
 * @author Daniele Paolini
 * Rating model class.
 */
public class Rating {

    // Attributes
    private ObservableList<Competence> competences;
    private ObservableList<Rate> rates;
    private LocalDate date;
    private final String ratingId;
    private final String studentId;

    /**
     * Constructor.
     * @param sid
     * @param rid
     * @param dd
     */
    public Rating(String sid, String rid, LocalDate dd) {
        ratingId = rid;
        studentId = sid;
        date = dd;
        competences = FXCollections.observableArrayList();
        rates = FXCollections.observableArrayList();
    }

    /**
     * Rating ID getter.
     * @return
     */
    public String getRatingId() { return ratingId; }

    /**
     * Student ID getter.
     * @return
     */
    public String getStudentId() { return studentId; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate dd) { date = dd; }

    /**
     * Competence getter.
     * @return
     */
    public Competence getCompetence(int index) { return competences.get(index); }

    /**
     * Rate setter.
     * @param index
     * @return
     */
    public Rate getRate(int index) { return rates.get(index); }

    /**
     * Rate specific setter. Remember to check the return after call it to see if the operation has succeeded.
     * @param index
     * @param r
     * @return
     */
    public boolean setRate(int index, Rate r) {
        if(r!=null && index >= 0 && index < competences.size()) {
            rates.set(index, r);
            return true;
        }
        return false;
    }

    /**
     * Rates setter. Remember to check the return after call it to see if the operation has succeeded.
     * @param rs
     * @return
     */
    public boolean setRates(List<Rate> rs) {
        if(rs.size() == competences.size() && !rs.isEmpty()){
            System.err.println("Error in setRates, size of the competence list and the rates list are not equal!");
            return false;
        }
        rates.addAll(rs);
        return true;
    }
}

package model;

import java.time.LocalDate;

/**
 * Created by dan on 09/02/17.
 * Model class for a valutation.
 * @Author Daniele Paolini
 */
public class Valuation {

    // Attributes
    private String studentId;
    private String valuationId;
    private LocalDate valuationDate;

    /**
     * Constructor
     * @param studentId
     * @param valuationId
     * @param valutationDate
     */
    public Valuation(String studentId, String valuationId, LocalDate valutationDate) {
        this.studentId = studentId;
        this.valuationId = valuationId;
        this.valuationDate = valutationDate;
    }

    /**
     * Student id setter.
     * @param id
     */
    public void setStudentId(String id) { studentId = id; }

    /**
     * Student id getter.
     * @return
     */
    public String getStudentId() { return studentId; }

    /**
     * Valuation id setter.
     * @param id
     */
    public void setValuationId(String id) { valuationId = id; }

    /**
     * Valuation id getter.
     * @return
     */
    public String getValuationId() { return valuationId; }

    /**
     * Valuation data setter.
     * @param date
     */
    public void setValuationDate(LocalDate date) { valuationDate = date; }

    /**
     * Valuation data getter.
     * @return
     */
    public LocalDate getValuationDate() { return valuationDate; }

    @Override
    public String toString() {
        return "Codice: ".concat(this.valuationId).concat(" - Effettuata in data ").concat(this.valuationDate.toString());
    }
}

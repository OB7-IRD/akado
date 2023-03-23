package fr.ird.driver.observe.business.data.ps.localmarket;

import fr.ird.driver.observe.business.data.DataEntity;
import io.ultreia.java4all.util.SingletonSupplier;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Survey extends DataEntity {

    private String comment;
    private Date date;
    private Integer number;
    private Supplier<Set<SurveyPart>> surveyPart = SingletonSupplier.of(LinkedHashSet::new);

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Set<SurveyPart> getSurveyPart() {
        return surveyPart.get();
    }

    public void setSurveyPart(Supplier<Set<SurveyPart>> surveyPart) {
        this.surveyPart = Objects.requireNonNull(surveyPart);
    }
}

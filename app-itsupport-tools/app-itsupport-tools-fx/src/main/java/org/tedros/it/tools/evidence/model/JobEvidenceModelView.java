package org.tedros.it.tools.evidence.model;

import java.util.Date;

import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.presenter.TEntityPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.it.tools.entity.JobEvidence;
import org.tedros.it.tools.entity.JobEvidenceItem;
import org.tedros.person.model.Employee;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

//@TSetting(JobEvidenceSettings.class)
@TPresenter()
public class JobEvidenceModelView extends TEntityModelView<JobEvidence> {

    private SimpleStringProperty name;

    private String description;

    private SimpleStringProperty tool;

    private SimpleStringProperty issueNumber;

    private SimpleStringProperty issueTitle;

    private SimpleStringProperty issueLink;

    private SimpleObjectProperty<Employee> employee;

    private SimpleObjectProperty<Date> executionDate;

    private ITObservableList<JobEvidenceItem> items;

    public JobEvidenceModelView(JobEvidence entity) {
        super(entity);
    }

}

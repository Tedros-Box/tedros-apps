package org.tedros.it.tools.evidence.model;

import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.it.tools.evidence.setting.CaptureEvidenceSettings;

import javafx.beans.property.SimpleStringProperty;

@TSetting(CaptureEvidenceSettings.class)
@TPresenter(model=CaptureEvidenceModel.class,
decorator=@TDecorator(type=TViewDecorator.class, viewTitle="Captura de Evidências"),
behavior=@TBehavior(type=TViewBehavior.class))
public class CaptureEvidenceMV extends TModelView<CaptureEvidenceModel> {

	@TVBox(pane=@TPane(children={"header"}))
	@TText(textStyle = TTextStyle.LARGE, text="Nenhuma tela capturada é usado para monitorar as atividades do usuário.")
	@TFieldBox(node=@TNode(parse = true, id=TFieldBox.TITLE))
	private SimpleStringProperty header;
	  
	public CaptureEvidenceMV(CaptureEvidenceModel model) {
		super(model);
	}

	public SimpleStringProperty getHeader() {
		return header;
	}

	public void setHeader(SimpleStringProperty header) {
		this.header = header;
	}
}

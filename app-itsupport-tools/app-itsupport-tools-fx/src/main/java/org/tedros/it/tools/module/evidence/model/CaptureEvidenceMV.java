package org.tedros.it.tools.module.evidence.model;

import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.component.TComponent;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.module.evidence.component.EvidenceMonitorComponent;

import javafx.beans.property.SimpleStringProperty;

@TPresenter(model=CaptureEvidenceModel.class,
decorator=@TDecorator(type=TViewDecorator.class, viewTitle=ItToolsKey.VIEW_CAPTURE_EVIDENCE),
behavior=@TBehavior(type=TViewBehavior.class))
public class CaptureEvidenceMV extends TModelView<CaptureEvidenceModel> {

	@TText(textStyle = TTextStyle.LARGE, text=ItToolsKey.TEXT_NO_SCREENS_CAPTURED)
	@TFieldBox(node=@TNode(parse = true, id=TFieldBox.TITLE))
	private SimpleStringProperty header;
	
	@TComponent(type=EvidenceMonitorComponent.class)
	private SimpleStringProperty monitorComponent;
	
	  
	public CaptureEvidenceMV(CaptureEvidenceModel model) {
		super(model);
	}

	public SimpleStringProperty getHeader() {
		return header;
	}

	public void setHeader(SimpleStringProperty header) {
		this.header = header;
	}

	public SimpleStringProperty getMonitorComponent() {
		return monitorComponent;
	}

	public void setMonitorComponent(SimpleStringProperty monitorComponent) {
		this.monitorComponent = monitorComponent;
	}
}

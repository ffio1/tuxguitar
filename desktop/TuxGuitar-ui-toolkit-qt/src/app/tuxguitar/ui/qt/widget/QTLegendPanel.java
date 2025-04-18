package app.tuxguitar.ui.qt.widget;

import app.tuxguitar.ui.widget.UILegendPanel;
import io.qt.widgets.QGroupBox;

public class QTLegendPanel extends QTLayoutContainer<QGroupBox> implements UILegendPanel {

	public QTLegendPanel(QTContainer parent) {
		super(new QGroupBox(parent.getContainerControl()), parent);
	}

	public String getText() {
		return this.getControl().title();
	}

	public void setText(String text) {
		this.getControl().setTitle(text);
	}
}

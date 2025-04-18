package app.tuxguitar.ui.qt.event;

import app.tuxguitar.ui.event.UIMenuEvent;
import app.tuxguitar.ui.event.UIMenuHideListenerManager;
import app.tuxguitar.ui.qt.QTComponent;
import org.qtjambi.qt.core.QEvent;

public class QTMenuHideListenerManager extends UIMenuHideListenerManager implements QTEventHandler, QTSignalHandler {

	private QTComponent<?> control;

	public QTMenuHideListenerManager(QTComponent<?> control) {
		this.control = control;
	}

	public void handle() {
		this.onMenuHide(new UIMenuEvent(this.control));
	}

	public boolean handle(QEvent event) {
		this.handle();

		return true;
	}
}

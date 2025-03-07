package org.herac.tuxguitar.ui.qt.event;

import org.herac.tuxguitar.ui.event.UIModifyEvent;
import org.herac.tuxguitar.ui.event.UIModifyListenerManager;
import org.herac.tuxguitar.ui.qt.QTComponent;
import io.qt.core.QEvent;

public class QTModifyListenerManager extends UIModifyListenerManager implements QTEventHandler, QTSignalHandler {
	
	private QTComponent<?> control;
	
	public QTModifyListenerManager(QTComponent<?> control) {
		this.control = control;
	}
	
	public void handle() {
		this.onModify(new UIModifyEvent(this.control));
	}
	
	public boolean handle(QEvent event) {
		this.handle();
		
		return true;
	}
}

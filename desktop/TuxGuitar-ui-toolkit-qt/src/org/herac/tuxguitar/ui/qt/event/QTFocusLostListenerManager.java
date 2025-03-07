package org.herac.tuxguitar.ui.qt.event;

import org.herac.tuxguitar.ui.event.UIFocusEvent;
import org.herac.tuxguitar.ui.event.UIFocusLostListenerManager;
import org.herac.tuxguitar.ui.qt.QTComponent;
import io.qt.core.QEvent;

public class QTFocusLostListenerManager extends UIFocusLostListenerManager implements QTEventHandler {
	
	private QTComponent<?> control;
	
	public QTFocusLostListenerManager(QTComponent<?> control) {
		this.control = control;
	}
	
	public boolean handle(QEvent event) {
		if(!this.control.isDisposed()) {
			this.onFocusLost(new UIFocusEvent(this.control));
		}
		return true;
	}
}

package org.herac.tuxguitar.ui.qt.event;

import org.herac.tuxguitar.ui.event.UIKeyEvent;
import org.herac.tuxguitar.ui.event.UIKeyReleasedListenerManager;
import org.herac.tuxguitar.ui.qt.QTComponent;
import org.herac.tuxguitar.ui.qt.resource.QTKey;
import io.qt.core.QEvent;
import io.qt.gui.QKeyEvent;

public class QTKeyReleasedListenerManager extends UIKeyReleasedListenerManager implements QTEventHandler {
	
	private QTComponent<?> control;
	
	public QTKeyReleasedListenerManager(QTComponent<?> control) {
		this.control = control;
	}
	
	public void handle(QKeyEvent event) {
		this.onKeyReleased(new UIKeyEvent(this.control, QTKey.getCombination(event)));
	}
	
	public boolean handle(QEvent event) {
		this.handle((QKeyEvent) event);
		
		return true;
	}
}
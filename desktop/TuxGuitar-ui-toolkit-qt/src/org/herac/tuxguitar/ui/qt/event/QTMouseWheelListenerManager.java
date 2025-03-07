package org.herac.tuxguitar.ui.qt.event;

import org.herac.tuxguitar.ui.event.UIMouseWheelEvent;
import org.herac.tuxguitar.ui.event.UIMouseWheelListenerManager;
import org.herac.tuxguitar.ui.qt.QTComponent;
import org.herac.tuxguitar.ui.resource.UIPosition;
import io.qt.core.QEvent;
import io.qt.gui.QWheelEvent;

public class QTMouseWheelListenerManager extends UIMouseWheelListenerManager implements QTEventHandler {
	
	private QTComponent<?> control;
	
	public QTMouseWheelListenerManager(QTComponent<?> control) {
		this.control = control;
	}
	
	public void handle(QWheelEvent event) {
// TODO QT 5->6 //		this.onMouseWheel(new UIMouseWheelEvent(this.control, new UIPosition(event.x(), event.y()), 2, event.delta()));
	}
	
	public boolean handle(QEvent event) {
		this.handle((QWheelEvent) event);
		
		return true;
	}
}

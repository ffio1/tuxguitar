package org.herac.tuxguitar.ui.qt.event;

import org.herac.tuxguitar.ui.event.UIPaintEvent;
import org.herac.tuxguitar.ui.event.UIPaintListenerManager;
import org.herac.tuxguitar.ui.qt.resource.QTPainter;
import org.herac.tuxguitar.ui.qt.widget.QTCanvas;
import io.qt.core.QEvent;
import io.qt.gui.QPainter;

public class QTPaintListenerManager extends UIPaintListenerManager implements QTEventHandler {
	
	private QTCanvas control;
	
	public QTPaintListenerManager(QTCanvas control) {
		this.control = control;
	}
	
	public boolean handle(QEvent event) {
		QPainter qPainter = new QPainter(this.control.getPaintDeviceInterface());
		QTPainter qtPainter = new QTPainter(qPainter);
		
		this.onPaint(new UIPaintEvent(this.control, qtPainter));
		
		qtPainter.dispose();
		
		return true;
	}
}

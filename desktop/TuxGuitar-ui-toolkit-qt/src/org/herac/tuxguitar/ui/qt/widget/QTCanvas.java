package org.herac.tuxguitar.ui.qt.widget;

import org.herac.tuxguitar.ui.event.UIPaintListener;
import org.herac.tuxguitar.ui.qt.event.QTPaintListenerManager;
import org.herac.tuxguitar.ui.resource.UISize;
import org.herac.tuxguitar.ui.widget.UICanvas;
import io.qt.core.QEvent.Type;
import io.qt.gui.QPaintDevice;
import io.qt.widgets.QFrame;
import io.qt.widgets.QFrame.Shape;

public class QTCanvas extends QTWidget<QFrame> implements UICanvas {
	
	private QTPaintListenerManager paintListener;
	
	public QTCanvas(QTContainer parent, boolean bordered) {
		super(new QFrame(parent.getContainerControl()), parent);
		
		this.paintListener = new QTPaintListenerManager(this);
		this.getControl().setAutoFillBackground(true);
		this.getControl().setFrameShape(bordered ? Shape.StyledPanel : Shape.NoFrame);
	}
	
	public QPaintDevice getPaintDeviceInterface() {
		return this.getControl();
	}
	
	public void computePackedSize(Float fixedWidth, Float fixedHeight) {
		UISize packedSize = this.getPackedSize();
		if( fixedWidth != null ) {
			packedSize.setWidth(fixedWidth);
		}
		if( fixedHeight != null ) {
			packedSize.setHeight(fixedHeight);
		}
		this.setPackedSize(packedSize);
	}
	
	public void addPaintListener(UIPaintListener listener) {
		if( this.paintListener.isEmpty() ) {
			this.getEventFilter().connect(Type.Paint, this.paintListener);
		}
		this.paintListener.addListener(listener);
	}
	
	public void removePaintListener(UIPaintListener listener) {
		this.paintListener.removeListener(listener);
		if( this.paintListener.isEmpty() ) {
			this.getEventFilter().disconnect(Type.Paint, this.paintListener);
		}
	}
}

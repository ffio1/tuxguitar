package app.tuxguitar.app.view.component.table;

import app.tuxguitar.app.view.util.TGBufferedPainterListenerLocked;
import app.tuxguitar.app.view.util.TGBufferedPainterLocked.TGBufferedPainterHandle;
import app.tuxguitar.ui.UIFactory;
import app.tuxguitar.ui.event.UIMouseDoubleClickListener;
import app.tuxguitar.ui.event.UIMouseDownListener;
import app.tuxguitar.ui.event.UIMouseEvent;
import app.tuxguitar.ui.event.UIMouseUpListener;
import app.tuxguitar.ui.layout.UITableLayout;
import app.tuxguitar.ui.resource.UIColor;
import app.tuxguitar.ui.resource.UIPainter;
import app.tuxguitar.ui.widget.UICanvas;
import app.tuxguitar.ui.widget.UIPanel;

public class TGTableRow {

	private TGTable table;
	private UIPanel row;
	private TGTableRowTextCell number;
	private TGTableRowButtonsCell soloMute;
	private TGTableRowTextCell name;
	private TGTableRowTextCell instrument;
	private UICanvas painter;

	private UIMouseUpListener mouseUpListenerLabel;
	private UIMouseDownListener mouseDownListenerLabel;
	private UIMouseDoubleClickListener mouseDoubleClickListenerLabel;

	private UIMouseUpListener mouseUpListenerCanvas;
	private UIMouseDownListener mouseDownListenerCanvas;
	private UIMouseDoubleClickListener mouseDoubleClickListenerCanvas;

	private TGTableCanvasPainter paintListenerCanvas;

	public TGTableRow(TGTable table){
		this.table = table;
		this.init();
	}

	public void init(){
		UIFactory uiFactory = this.table.getUIFactory();
		MouseListenerLabel mouseListenerLabel = new MouseListenerLabel();
		MouseListenerCanvas mouseListenerCanvas = new MouseListenerCanvas();

		this.row = uiFactory.createPanel(this.table.getRowControl(), false);
		this.row.setLayout(new TGTableRowLayout(this));

		this.number = new TGTableRowTextCell(this);
		this.number.addMouseDownListener(mouseListenerLabel);
		this.number.addMouseUpListener(mouseListenerLabel);
		this.number.addMouseDoubleClickListener(mouseListenerLabel);

		this.soloMute = new TGTableRowButtonsCell(this);
		this.soloMute.getLayout().set(UITableLayout.MARGIN_LEFT, 0f);
		this.soloMute.getLayout().set(UITableLayout.MARGIN_RIGHT, 0f);

		this.name = new TGTableRowTextCell(this);
		this.name.addMouseDownListener(mouseListenerLabel);
		this.name.addMouseUpListener(mouseListenerLabel);
		this.name.addMouseDoubleClickListener(mouseListenerLabel);

		this.instrument = new TGTableRowTextCell(this);
		this.instrument.addMouseDownListener(mouseListenerLabel);
		this.instrument.addMouseUpListener(mouseListenerLabel);
		this.instrument.addMouseDoubleClickListener(mouseListenerLabel);

		this.painter = uiFactory.createCanvas(this.row, false);
		this.painter.addMouseDownListener(mouseListenerCanvas);
		this.painter.addMouseUpListener(mouseListenerCanvas);
		this.painter.addMouseDoubleClickListener(mouseListenerCanvas);
		this.painter.addPaintListener(new TGBufferedPainterListenerLocked(this.table.getContext(), new TGTableRowPaintHandle()));
		this.table.appendListeners(this.painter);
	}

	public void setBgColor(UIColor background){
		this.number.setBgColor(background);
		this.soloMute.setBgColor(background);
		this.name.setBgColor(background);
		this.instrument.setBgColor(background);
	}

	public void setFgColor(UIColor foreground){
		this.number.setFgColor(foreground);
		this.soloMute.setFgColor(foreground);
		this.name.setFgColor(foreground);
		this.instrument.setFgColor(foreground);
	}

	public void dispose(){
		this.row.dispose();
	}

	public TGTable getTable() {
		return this.table;
	}

	public UIPanel getControl() {
		return this.row;
	}

	public UICanvas getPainter() {
		return this.painter;
	}

	public TGTableRowTextCell getInstrument() {
		return this.instrument;
	}

	public TGTableRowTextCell getName() {
		return this.name;
	}

	public TGTableRowTextCell getNumber() {
		return this.number;
	}

	public TGTableRowButtonsCell getSoloMute() {
		return this.soloMute;
	}

	public UIMouseUpListener getMouseUpListenerLabel() {
		return mouseUpListenerLabel;
	}

	public void setMouseUpListenerLabel(UIMouseUpListener mouseUpListenerLabel) {
		this.mouseUpListenerLabel = mouseUpListenerLabel;
	}

	public UIMouseDownListener getMouseDownListenerLabel() {
		return mouseDownListenerLabel;
	}

	public void setMouseDownListenerLabel(UIMouseDownListener mouseDownListenerLabel) {
		this.mouseDownListenerLabel = mouseDownListenerLabel;
	}

	public UIMouseDoubleClickListener getMouseDoubleClickListenerLabel() {
		return mouseDoubleClickListenerLabel;
	}

	public void setMouseDoubleClickListenerLabel(UIMouseDoubleClickListener mouseDoubleClickListenerLabel) {
		this.mouseDoubleClickListenerLabel = mouseDoubleClickListenerLabel;
	}

	public UIMouseUpListener getMouseUpListenerCanvas() {
		return mouseUpListenerCanvas;
	}

	public void setMouseUpListenerCanvas(UIMouseUpListener mouseUpListenerCanvas) {
		this.mouseUpListenerCanvas = mouseUpListenerCanvas;
	}

	public UIMouseDownListener getMouseDownListenerCanvas() {
		return mouseDownListenerCanvas;
	}

	public void setMouseDownListenerCanvas(UIMouseDownListener mouseDownListenerCanvas) {
		this.mouseDownListenerCanvas = mouseDownListenerCanvas;
	}

	public UIMouseDoubleClickListener getMouseDoubleClickListenerCanvas() {
		return mouseDoubleClickListenerCanvas;
	}

	public void setMouseDoubleClickListenerCanvas(UIMouseDoubleClickListener mouseDoubleClickListenerCanvas) {
		this.mouseDoubleClickListenerCanvas = mouseDoubleClickListenerCanvas;
	}

	public TGTableCanvasPainter getPaintListenerCanvas() {
		return this.paintListenerCanvas;
	}

	public void setPaintListenerCanvas(TGTableCanvasPainter paintListenerCanvas) {
		this.paintListenerCanvas = paintListenerCanvas;
	}

	private class MouseListenerLabel implements UIMouseUpListener, UIMouseDownListener, UIMouseDoubleClickListener{

		public MouseListenerLabel(){
			super();
		}

		public void onMouseDoubleClick(UIMouseEvent event) {
			if( getMouseDoubleClickListenerLabel() != null){
				getMouseDoubleClickListenerLabel().onMouseDoubleClick(event);
			}
		}

		public void onMouseDown(UIMouseEvent event) {
			if( getMouseDownListenerLabel() != null){
				getMouseDownListenerLabel().onMouseDown(event);
			}
		}

		public void onMouseUp(UIMouseEvent event) {
			if( getMouseUpListenerLabel() != null){
				getMouseUpListenerLabel().onMouseUp(event);
			}
		}
	}

	private class MouseListenerCanvas implements UIMouseUpListener, UIMouseDownListener, UIMouseDoubleClickListener{

		public MouseListenerCanvas(){
			super();
		}

		public void onMouseDoubleClick(UIMouseEvent event) {
			if( getMouseDoubleClickListenerCanvas() != null){
				getMouseDoubleClickListenerCanvas().onMouseDoubleClick(event);
			}
		}

		public void onMouseDown(UIMouseEvent event) {
			if( getMouseDownListenerCanvas() != null){
				getMouseDownListenerCanvas().onMouseDown(event);
			}
		}

		public void onMouseUp(UIMouseEvent event) {
			if( getMouseUpListenerCanvas() != null){
				getMouseUpListenerCanvas().onMouseUp(event);
			}
		}
	}

	private class TGTableRowPaintHandle implements TGBufferedPainterHandle {

		public TGTableRowPaintHandle(){
			super();
		}

		public void paintControl(UIPainter painter) {
			if( TGTableRow.this.getPaintListenerCanvas() != null ){
				TGTableRow.this.getPaintListenerCanvas().paintTrack(TGTableRow.this, painter);
			}
		}

		public UICanvas getPaintableControl() {
			return TGTableRow.this.getPainter();
		}
	}
}

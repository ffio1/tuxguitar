package app.tuxguitar.ui.swt.toolbar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.ToolItem;
import app.tuxguitar.ui.event.UISelectionListener;
import app.tuxguitar.ui.menu.UIMenu;
import app.tuxguitar.ui.menu.UIPopupMenu;
import app.tuxguitar.ui.resource.UIPosition;
import app.tuxguitar.ui.swt.event.SWTSelectionListenerManager;
import app.tuxguitar.ui.swt.menu.SWTPopupMenu;
import app.tuxguitar.ui.toolbar.UIToolActionMenuItem;

public class SWTToolActionMenuItem extends SWTToolItem implements UIToolActionMenuItem {

	private UIPopupMenu menu;
	private SWTSelectionListenerManager selectionListener;

	public SWTToolActionMenuItem(ToolItem item, SWTToolBar parent) {
		super(item, parent);

		this.selectionListener = new SWTSelectionListenerManager(this);
		this.menu = new SWTPopupMenu(this.getParent().getControl().getShell());
		this.getControl().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				SWTToolActionMenuItem.this.onSelect(event);
			}
		});
	}

	public void addSelectionListener(UISelectionListener listener) {
		this.selectionListener.addListener(listener);
	}

	public void removeSelectionListener(UISelectionListener listener) {
		this.selectionListener.removeListener(listener);
	}

	public UIMenu getMenu() {
		return this.menu;
	}

	public void openMenu() {
		Rectangle rect = this.getControl().getBounds();
		Point pt = this.getControl().getParent().toDisplay(new Point(rect.x, rect.y));

		this.menu.open(new UIPosition(pt.x, pt.y + rect.height));
	}

	public void onSelect(SelectionEvent event) {
		if( event.detail == SWT.ARROW ) {
			this.openMenu();
		} else {
			this.selectionListener.widgetSelected(event);
		}
	}
}

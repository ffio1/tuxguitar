package org.herac.tuxguitar.ui.qt.chooser;

import org.herac.tuxguitar.ui.chooser.UIPrinterChooser;
import org.herac.tuxguitar.ui.chooser.UIPrinterChooserHandler;
import org.herac.tuxguitar.ui.qt.printer.QTPrinter;
import org.herac.tuxguitar.ui.qt.widget.QTAbstractWindow;
import io.qt.printsupport.QPrintDialog;
import io.qt.printsupport.QPrinter;
import io.qt.widgets.QDialog;

public class QTPrinterChooser implements UIPrinterChooser {

	private QTAbstractWindow<?> window;
	
	public QTPrinterChooser(QTAbstractWindow<?> window) {
		this.window = window;
	}
	
	public void choose(UIPrinterChooserHandler selectionHandler) {
		QTPrinter selection = null;
		QPrintDialog dialog = new QPrintDialog(this.window.getControl());
		if( dialog.exec() == QDialog.DialogCode.Accepted.value() ) {
			QPrinter printer = dialog.printer();
			if( printer != null ) {
				selection = new QTPrinter(printer);
			}
		}
		selectionHandler.onSelectPrinter(selection);
	}
}

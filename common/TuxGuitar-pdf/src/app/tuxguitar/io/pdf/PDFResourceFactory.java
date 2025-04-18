package app.tuxguitar.io.pdf;

import java.io.InputStream;

import app.tuxguitar.ui.resource.UIColor;
import app.tuxguitar.ui.resource.UIColorModel;
import app.tuxguitar.ui.resource.UIFont;
import app.tuxguitar.ui.resource.UIFontModel;
import app.tuxguitar.ui.resource.UIImage;
import app.tuxguitar.ui.resource.UIResourceFactory;

public class PDFResourceFactory implements UIResourceFactory {

	public PDFResourceFactory(){
		super();
	}

	public UIColor createColor( int red, int green, int blue ){
		return new PDFColor( red, green , blue );
	}

	public UIColor createColor( UIColorModel cm ){
		return this.createColor(cm.getRed(), cm.getGreen(), cm.getBlue());
	}

	public UIFont createFont( String name, float height, boolean bold, boolean italic ){
		return new PDFFont( name , height , bold , italic );
	}

	public UIFont createFont( UIFontModel fm ){
		return this.createFont(fm.getName(), fm.getHeight(), fm.isBold(), fm.isItalic());
	}

	public UIImage createImage( float width, float height ){
		throw new PDFUnsupportedOperationException();
	}

	public UIImage createImage(InputStream inputStream) {
		throw new PDFUnsupportedOperationException();
	}
}

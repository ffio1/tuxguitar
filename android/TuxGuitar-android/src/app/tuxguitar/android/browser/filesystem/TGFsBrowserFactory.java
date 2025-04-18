package app.tuxguitar.android.browser.filesystem;

import app.tuxguitar.android.activity.TGActivity;
import app.tuxguitar.android.activity.TGActivityController;
import app.tuxguitar.android.activity.TGActivityPermissionRequest;
import app.tuxguitar.android.browser.model.TGBrowserFactory;
import app.tuxguitar.android.browser.model.TGBrowserFactoryHandler;
import app.tuxguitar.android.browser.model.TGBrowserFactorySettingsHandler;
import app.tuxguitar.android.browser.model.TGBrowserSettings;
import app.tuxguitar.util.TGContext;

public class TGFsBrowserFactory implements TGBrowserFactory{

	public static final String BROWSER_TYPE = "file.system";
	public static final String BROWSER_NAME = "File System";

	public static final String[] PERMISSIONS = {
		"android.permission.READ_EXTERNAL_STORAGE",
		"android.permission.WRITE_EXTERNAL_STORAGE"
	};

	private TGContext context;
	private TGFsBrowserSettingsFactory settingsFactory;

	public TGFsBrowserFactory(TGContext context, TGFsBrowserSettingsFactory settingsFactory) {
		this.context = context;
		this.settingsFactory = settingsFactory;
	}

	public String getType(){
		return BROWSER_TYPE;
	}

	public String getName(){
		return BROWSER_NAME;
	}

	public void createBrowser(final TGBrowserFactoryHandler handler, final TGBrowserSettings settings) {
		this.runWithPermissions(new Runnable() {
			public void run() {
				handler.onCreateBrowser(new TGFsBrowser(TGFsBrowserFactory.this.context, TGFsBrowserSettings.createInstance(settings)));
			}
		});
	}

	public void createSettings(final TGBrowserFactorySettingsHandler handler) {
		this.runWithPermissions(new Runnable() {
			public void run() {
				TGFsBrowserFactory.this.settingsFactory.createSettings(handler);
			}
		});
	}

	public void runWithPermissions(final Runnable runnable) {
		TGActivity activity = TGActivityController.getInstance(this.context).getActivity();
		if( activity != null ) {
			new TGActivityPermissionRequest(activity, PERMISSIONS, null, new Runnable() {
				public void run() {
					runnable.run();
				}
			}, null).process();
		}
	}
}

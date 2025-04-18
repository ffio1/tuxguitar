package app.tuxguitar.community.browser;

import app.tuxguitar.app.TuxGuitar;
import app.tuxguitar.app.ui.TGApplication;
import app.tuxguitar.app.view.dialog.browser.main.TGBrowserDialog;
import app.tuxguitar.app.view.main.TGWindow;
import app.tuxguitar.app.view.util.TGDialogUtil;
import app.tuxguitar.community.TGCommunitySingleton;
import app.tuxguitar.community.auth.TGCommunityAuthDialog;
import app.tuxguitar.ui.UIFactory;
import app.tuxguitar.ui.event.UISelectionEvent;
import app.tuxguitar.ui.event.UISelectionListener;
import app.tuxguitar.ui.layout.UITableLayout;
import app.tuxguitar.ui.widget.UIButton;
import app.tuxguitar.ui.widget.UILabel;
import app.tuxguitar.ui.widget.UILegendPanel;
import app.tuxguitar.ui.widget.UIPanel;
import app.tuxguitar.ui.widget.UIReadOnlyTextField;
import app.tuxguitar.ui.widget.UIWindow;
import app.tuxguitar.util.TGContext;

public class TGBrowserAuthDialog {

	private TGContext context;
	private Runnable onSuccess;

	public TGBrowserAuthDialog(TGContext context, Runnable onSuccess) {
		this.context = context;
		this.onSuccess = onSuccess;
	}

	public void open() {
		TGBrowserDialog browser = TGBrowserDialog.getInstance(this.context);
		this.open(!browser.isDisposed() ? browser.getWindow() : TGWindow.getInstance(this.context).getWindow());
	}

	public void open(final UIWindow parent) {
		final UIFactory uiFactory = TGApplication.getInstance(this.context).getFactory();
		final UITableLayout dialogLayout = new UITableLayout();
		final UIWindow dialog = uiFactory.createWindow(parent, true, false);

		dialog.setLayout(dialogLayout);
		dialog.setImage(TuxGuitar.getInstance().getIconManager().getAppIcon());
		dialog.setText(TuxGuitar.getProperty("tuxguitar-community.browser-dialog.title"));

		UITableLayout groupLayout = new UITableLayout();
		UILegendPanel group = uiFactory.createLegendPanel(dialog);
		group.setLayout(groupLayout);
		group.setText(TuxGuitar.getProperty("tuxguitar-community.browser-dialog.account"));
		dialogLayout.set(group, 1, 1, UITableLayout.ALIGN_FILL, UITableLayout.ALIGN_FILL, true, true);

		//-------USERNAME---------------------------------
		UILabel usernameLabel = uiFactory.createLabel(group);
		usernameLabel.setText(TuxGuitar.getProperty("tuxguitar-community.browser-dialog.account.user") + ":");
		groupLayout.set(usernameLabel, 1, 1, UITableLayout.ALIGN_RIGHT, UITableLayout.ALIGN_CENTER, false, false);

		final UIReadOnlyTextField usernameText = uiFactory.createReadOnlyTextField(group);
		usernameText.setText( TGCommunitySingleton.getInstance(getContext()).getAuth().getUsername() );
		groupLayout.set(usernameText, 1, 2, UITableLayout.ALIGN_FILL, UITableLayout.ALIGN_CENTER, true, false, 1, 1, 250f, null, null);

		final UIButton usernameChooser = uiFactory.createButton(group);
		usernameChooser.setText("...");
		usernameChooser.addSelectionListener(new UISelectionListener() {
			public void onSelect(UISelectionEvent event) {
				new TGCommunityAuthDialog(getContext(), new Runnable() {
					public void run() {
						TGCommunitySingleton.getInstance(getContext()).getAuth().update();
						usernameText.setText( TGCommunitySingleton.getInstance(getContext()).getAuth().getUsername() );
					}
				}, null).open(dialog);
			}
		});
		groupLayout.set(usernameChooser, 1, 3, UITableLayout.ALIGN_CENTER, UITableLayout.ALIGN_CENTER, false, false);

		//------------------BUTTONS--------------------------
		UITableLayout buttonsLayout = new UITableLayout(0f);
		UIPanel buttons = uiFactory.createPanel(dialog, false);
		buttons.setLayout(buttonsLayout);
		dialogLayout.set(buttons, 2, 1, UITableLayout.ALIGN_RIGHT, UITableLayout.ALIGN_FILL, true, true);

		UIButton buttonOK = uiFactory.createButton(buttons);
		buttonOK.setText(TuxGuitar.getProperty("ok"));
		buttonOK.setDefaultButton();
		buttonOK.addSelectionListener(new UISelectionListener() {
			public void onSelect(UISelectionEvent event) {
				onFinish(dialog, TGBrowserAuthDialog.this.onSuccess);
			}
		});
		buttonsLayout.set(buttonOK, 1, 1, UITableLayout.ALIGN_FILL, UITableLayout.ALIGN_FILL, true, true, 1, 1, 80f, 25f, null);

		UIButton buttonCancel = uiFactory.createButton(buttons);
		buttonCancel.setText(TuxGuitar.getProperty("cancel"));
		buttonCancel.addSelectionListener(new UISelectionListener() {
			public void onSelect(UISelectionEvent event) {
				onFinish(dialog, null);
			}
		});
		buttonsLayout.set(buttonCancel, 1, 2, UITableLayout.ALIGN_FILL, UITableLayout.ALIGN_FILL, true, true, 1, 1, 80f, 25f, null);
		buttonsLayout.set(buttonCancel, UITableLayout.MARGIN_RIGHT, 0f);

		TGDialogUtil.openDialog(dialog, TGDialogUtil.OPEN_STYLE_CENTER | TGDialogUtil.OPEN_STYLE_PACK);
	}

	public void onFinish(UIWindow dialog, Runnable runnable) {
		dialog.dispose();
		if( runnable != null ) {
			runnable.run();
		}
	}

	public TGContext getContext() {
		return context;
	}
}

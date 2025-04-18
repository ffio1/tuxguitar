package app.tuxguitar.app.view.dialog.settings;

import java.util.ArrayList;
import java.util.List;

import app.tuxguitar.app.TuxGuitar;
import app.tuxguitar.app.action.impl.settings.TGReloadSettingsAction;
import app.tuxguitar.app.system.config.TGConfigDefaults;
import app.tuxguitar.app.system.config.TGConfigManager;
import app.tuxguitar.app.ui.TGApplication;
import app.tuxguitar.app.view.component.tab.TablatureEditor;
import app.tuxguitar.app.view.controller.TGViewContext;
import app.tuxguitar.app.view.dialog.settings.items.LanguageOption;
import app.tuxguitar.app.view.dialog.settings.items.MainOption;
import app.tuxguitar.app.view.dialog.settings.items.SkinOption;
import app.tuxguitar.app.view.dialog.settings.items.SoundOption;
import app.tuxguitar.app.view.dialog.settings.items.StylesOption;
import app.tuxguitar.app.view.dialog.settings.items.TGSettingsOption;
import app.tuxguitar.app.view.util.TGCursorController;
import app.tuxguitar.app.view.util.TGDialogUtil;
import app.tuxguitar.editor.action.TGActionProcessor;
import app.tuxguitar.ui.UIFactory;
import app.tuxguitar.ui.event.UISelectionEvent;
import app.tuxguitar.ui.event.UISelectionListener;
import app.tuxguitar.ui.layout.UITableLayout;
import app.tuxguitar.ui.resource.UICursor;
import app.tuxguitar.ui.toolbar.UIToolBar;
import app.tuxguitar.ui.widget.UIButton;
import app.tuxguitar.ui.widget.UILayoutContainer;
import app.tuxguitar.ui.widget.UIPanel;
import app.tuxguitar.ui.widget.UIWindow;
import app.tuxguitar.util.properties.TGProperties;

public class TGSettingsEditor{

	private TGViewContext context;
	private TGCursorController cursorController;
	private TGConfigManager config;
	private TGProperties defaults;
	private UIWindow dialog;
	private List<TGSettingsOption> options;

	private List<Runnable> runnables;

	public TGSettingsEditor(TGViewContext context) {
		this.context = context;
		this.config = TGConfigManager.getInstance(this.context.getContext());
	}

	public void show() {
		final UIFactory uiFactory = this.getUIFactory();
		final UIWindow uiParent = this.context.getAttribute(TGViewContext.ATTRIBUTE_PARENT);
		final UITableLayout dialogLayout = new UITableLayout();

		this.dialog = uiFactory.createWindow(uiParent, true, false);
		this.dialog.setLayout(dialogLayout);
		this.dialog.setText(TuxGuitar.getProperty("settings.config"));

		//-------main-------------------------------------
		UIPanel mainComposite = uiFactory.createPanel(this.dialog, false);
		mainComposite.setLayout(new UITableLayout());
		dialogLayout.set(mainComposite, 1, 1, UITableLayout.ALIGN_FILL, UITableLayout.ALIGN_FILL, true, true);
		this.createComposites(mainComposite);

		//-------buttons-------------------------------------
		UITableLayout buttonsLayout = new UITableLayout();
		UIPanel buttons = uiFactory.createPanel(this.dialog, false);
		buttons.setLayout(buttonsLayout);
		dialogLayout.set(buttons, 2, 1, UITableLayout.ALIGN_RIGHT, UITableLayout.ALIGN_FILL, true, false);

		UIButton buttonDefaults = uiFactory.createButton(buttons);
		buttonDefaults.setText(TuxGuitar.getProperty("defaults"));
		buttonDefaults.addSelectionListener(new UISelectionListener() {
			public void onSelect(UISelectionEvent event) {
				dispose();
				setDefaults();
				applyConfig(true);
			}
		});
		buttonsLayout.set(buttonDefaults, 1, 1, UITableLayout.ALIGN_FILL, UITableLayout.ALIGN_FILL, true, true, 1, 1, 80f, 25f, null);

		UIButton buttonOK = uiFactory.createButton(buttons);
		buttonOK.setDefaultButton();
		buttonOK.setText(TuxGuitar.getProperty("ok"));
		buttonOK.addSelectionListener(new UISelectionListener() {
			public void onSelect(UISelectionEvent event) {
				updateOptions();
				dispose();
				applyConfig(false);
			}
		});
		buttonsLayout.set(buttonOK, 1, 2, UITableLayout.ALIGN_FILL, UITableLayout.ALIGN_FILL, true, true, 1, 1, 80f, 25f, null);

		UIButton buttonCancel = uiFactory.createButton(buttons);
		buttonCancel.setText(TuxGuitar.getProperty("cancel"));
		buttonCancel.addSelectionListener(new UISelectionListener() {
			public void onSelect(UISelectionEvent event) {
				dispose();
			}
		});
		buttonsLayout.set(buttonCancel, 1, 3, UITableLayout.ALIGN_FILL, UITableLayout.ALIGN_FILL, true, true, 1, 1, 80f, 25f, null);

		TGDialogUtil.openDialog(this.dialog,TGDialogUtil.OPEN_STYLE_CENTER | TGDialogUtil.OPEN_STYLE_PACK);
	}

	private void createComposites(UILayoutContainer parent) {
		UIFactory uiFactory = this.getUIFactory();
		UITableLayout parentLayout = (UITableLayout) parent.getLayout();

		UIToolBar toolBar = uiFactory.createVerticalToolBar(parent);
		parentLayout.set(toolBar, 1, 1, UITableLayout.ALIGN_FILL, UITableLayout.ALIGN_FILL, false, true);

		UIPanel option = uiFactory.createPanel(parent, false);
		option.setLayout(new UITableLayout(0f));
		parentLayout.set(option, 1, 2, UITableLayout.ALIGN_FILL, UITableLayout.ALIGN_FILL, true, true);

		initOptions(toolBar, option);

		if( this.options.size() > 0 ){
			select(this.options.get(0));
		}
	}

	private void initOptions(UIToolBar toolBar, UILayoutContainer parent){

		this.options = new ArrayList<TGSettingsOption>();
		this.options.add(new MainOption(this, toolBar, parent));
		this.options.add(new StylesOption(this, toolBar, parent));
		this.options.add(new LanguageOption(this, toolBar, parent));
		this.options.add(new SkinOption(this, toolBar, parent));
		this.options.add(new SoundOption(this, toolBar, parent));

		for(TGSettingsOption option : this.options) {
			option.createOption();
		}
	}

	public void loadCursor(UICursor cursor) {
		if(!this.isDisposed()) {
			if( this.cursorController == null || !this.cursorController.isControlling(this.dialog) ) {
				this.cursorController = new TGCursorController(this.context.getContext(), this.dialog);
			}
			this.cursorController.loadCursor(cursor);
		}
	}

	public void pack(){
		this.dialog.pack();
	}

	public void select(TGSettingsOption option){
		hideAll();
		option.setVisible(true);
		this.dialog.redraw();
	}

	private void hideAll(){
		for(TGSettingsOption option : this.options) {
			option.setVisible(false);
		}
	}

	protected void updateOptions(){
		for(TGSettingsOption option : this.options) {
			option.updateConfig();
		}
		this.config.save();
	}

	protected void setDefaults(){
		for(TGSettingsOption option : this.options) {
			option.updateDefaults();
		}
		this.config.save();
	}

	protected void applyConfig(final boolean force) {
		TGActionProcessor tgActionProcessor = new TGActionProcessor(this.context.getContext(), TGReloadSettingsAction.NAME);
		tgActionProcessor.setAttribute(TGReloadSettingsAction.ATTRIBUTE_FORCE, force);
		tgActionProcessor.process();
	}

	protected void dispose(){
		for(TGSettingsOption option : this.options) {
			option.dispose();
		}
		getWindow().dispose();
	}

	public TGProperties getDefaults(){
		if( this.defaults == null ){
			this.defaults = TGConfigDefaults.createDefaults();
		}
		return this.defaults;
	}

	public TGConfigManager getConfig(){
		return this.config;
	}

	public TablatureEditor getEditor(){
		return TuxGuitar.getInstance().getTablatureEditor();
	}

	public TGViewContext getViewContext() {
		return this.context;
	}

	public UIWindow getWindow(){
		return this.dialog;
	}

	public UIFactory getUIFactory() {
		return TGApplication.getInstance(this.context.getContext()).getFactory();
	}

	public void addSyncThread(Runnable runnable){
		this.runnables.add( runnable );
	}

	public boolean isDisposed() {
		return (this.dialog == null || this.dialog.isDisposed());
	}
}

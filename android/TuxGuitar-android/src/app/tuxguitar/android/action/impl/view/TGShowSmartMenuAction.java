package app.tuxguitar.android.action.impl.view;

import app.tuxguitar.action.TGActionContext;
import app.tuxguitar.android.action.TGActionBase;
import app.tuxguitar.android.view.tablature.TGSongViewController;
import app.tuxguitar.util.TGContext;

public class TGShowSmartMenuAction extends TGActionBase{

	public static final String NAME = "action.view.show-smart-menu";

	public TGShowSmartMenuAction(TGContext context) {
		super(context, NAME);
	}

	protected void processAction(TGActionContext context) {
		TGSongViewController.getInstance(getContext()).getSmartMenu().openSmartMenu(context);
	}
}

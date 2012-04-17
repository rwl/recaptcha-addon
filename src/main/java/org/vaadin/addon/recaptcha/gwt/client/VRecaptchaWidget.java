package org.vaadin.addon.recaptcha.gwt.client;

import com.claudiushauptmann.gwt.recaptcha.client.CustomTranslation;
import com.claudiushauptmann.gwt.recaptcha.client.RecaptchaWidget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;

public class VRecaptchaWidget extends RecaptchaWidget implements Paintable {

	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-recaptcha";

	/** The client side widget identifier */
	protected String paintableId;

	/** Reference to the server connection object. */
	ApplicationConnection client;

	public VRecaptchaWidget() {
		super("6LeeYtASAAAAAH-oArDa8zkFQLwUzE4UtLDxDkxZ");

	        // This method call of the Paintable interface sets the component
	        // style name in DOM tree
	        setStyleName(CLASSNAME);
	}

//	public VRecaptchaWidget(String key, String lang, String theme) {
//		super(key, lang, theme);
//	        setStyleName(CLASSNAME);
//	}
//
//	public VRecaptchaWidget(String key, String lang, String theme,
//			int tabIndex) {
//		super(key, lang, theme, tabIndex);
//	        setStyleName(CLASSNAME);
//	}
//
//	public VRecaptchaWidget(String key, String lang, String theme,
//			int tabIndex, CustomTranslation customTranslation) {
//		super(key, lang, theme, tabIndex, customTranslation);
//	        setStyleName(CLASSNAME);
//	}
//
//	public VRecaptchaWidget(String key, String lang, String theme,
//			int tabIndex, String customTheme) {
//		super(key, lang, theme, tabIndex, customTheme);
//	        setStyleName(CLASSNAME);
//	}
//
//	public VRecaptchaWidget(String key, String lang, String theme,
//			int tabIndex, CustomTranslation customTranslation,
//			String customTheme) {
//		super(key, lang, theme, tabIndex, customTranslation,
//				customTheme);
//	        setStyleName(CLASSNAME);
//	}

	public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
	        // This call should be made first.
	        // It handles sizes, captions, tooltips, etc. automatically.
	        if (client.updateComponent(this, uidl, true)) {
	            // If client.updateComponent returns true there has been no
	            // changes and we do not need to update anything.
	            return;
	        }

	        // Save reference to server connection object to be able to send
	        // user interaction later
	        this.client = client;

	        // Save the client side identifier (paintable id) for the widget
	        paintableId = uidl.getId();

	        // TODO replace dummy code with actual component logic
//	        getElement().setInnerHTML("It works!");

	        if (uidl.hasAttribute("reload")) {
	        	if (uidl.getBooleanVariable("reload"))
	        		reload();
	        }

	        if (uidl.hasAttribute("focusResponseField")) {
	        	if (uidl.getBooleanVariable("focusResponseField"))
	        		focusResponseField();
	        }

	        if (uidl.hasAttribute("showHelp")) {
	        	if (uidl.getBooleanVariable("showHelp"))
	        		showHelp();
	        }

	        if (uidl.hasAttribute("switchType")) {
	        	switchType(uidl.getStringAttribute("switchType"));
	        }

	}

	@Override
	public String getChallenge() {
		String challenge = super.getChallenge();

	        // Updating the state to the server can not be done before
	        // the server connection is known, i.e., before updateFromUIDL()
	        // has been called.
	        if (paintableId == null || client == null) {
	            return challenge;
	        }

	        // Communicate the user interaction parameters to server. This call will
	        // initiate an AJAX request to the server.
	        client.updateVariable(paintableId, "challenge", challenge, true);

		return challenge;
	}

	@Override
	public String getResponse() {
		String response = super.getResponse();

	        if (paintableId == null || client == null) return response;

	        client.updateVariable(paintableId, "response", response, true);

		return response;
	}

}

package org.vaadin.addon.recaptcha;

import java.util.Map;

import org.vaadin.addon.recaptcha.gwt.client.VRecaptchaComponent;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.ClientWidget;

/**
 * Server side component for the VRecaptchaComponent widget.
 */
@ClientWidget(VRecaptchaComponent.class)
public class RecaptchaComponent extends AbstractComponent {

	private String challenge;
	private String response;

	/** Paint (serialize) the component for the client. */
	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);

		// Superclass writes any common attributes in the paint target.
		super.paintContent(target);

//		target.addVariable(this, "reload", isReload());
	}

	/** Deserialize changes received from client. */
	@SuppressWarnings("unchecked")
	@Override
	public void changeVariables(Object source, Map variables) {

		if (variables.containsKey("challenge")) {
			challenge = (String) variables.get("challenge");
		}

		if (variables.containsKey("response")) {
			response = (String) variables.get("response");
		}
	}

}

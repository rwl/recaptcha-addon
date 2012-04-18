package org.vaadin.addon.recaptcha;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;

import org.vaadin.addon.recaptcha.gwt.client.VRecaptchaComponent;
import org.vaadin.addon.recaptcha.gwt.client.VRecaptchaWidget;

import com.claudiushauptmann.gwt.recaptcha.client.Recaptcha;
import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.service.ApplicationContext;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.TextField;

@ClientWidget(VRecaptchaWidget.class)
public class RecaptchaField extends TextField {

	private static final ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<HttpServletRequest>();

	public static HttpServletRequest getThreadLocalRequest() {
		return RecaptchaField.REQUEST.get();
	}

	private String challenge;
	private String response;
	private String privateKey = "6LeeYtASAAAAAH537ZY4VIgrLxJJqOiwjg5ZYTNi";
	private String publicKey = "6LeeYtASAAAAAH-oArDa8zkFQLwUzE4UtLDxDkxZ";
	private String type = Recaptcha.IMAGE;
	private boolean reload = false;
	private boolean focusResponseField = false;
	private boolean showHelp = false;

	private boolean listening = false;

	public RecaptchaField() {
		super();
		initField();
	}

	public RecaptchaField(Property dataSource) {
		super(dataSource);
		initField();
	}

	public RecaptchaField(String caption, Property dataSource) {
		super(caption, dataSource);
		initField();
	}

	public RecaptchaField(String caption, String value) {
		super(caption, value);
		initField();
	}

	public RecaptchaField(String caption) {
		super(caption);
		initField();
	}

	private void initField() {
	      setWidth("318px");
	      setHeight("130px");
	      setImmediate(true);
	}

	/** Paint (serialize) the component for the client. */
	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);

		if (!listening) {
			getApplication().getContext().addTransactionListener(new ApplicationContext.TransactionListener() {

				public void transactionStart(Application application,
						Object transactionData) {
					HttpServletRequest request = (HttpServletRequest) transactionData;
					RecaptchaField.REQUEST.set(request);
				}

				public void transactionEnd(Application application,
						Object transactionData) {
					RecaptchaField.REQUEST.remove();
				}
			});
			listening = true;
		}

		// Superclass writes any common attributes in the paint target.
		super.paintContent(target);

		if (reload) {
			target.addVariable(this, "reload", true);
			reload = false;
		}

		if (focusResponseField) {
			target.addVariable(this, "focusResponseField", true);
			focusResponseField = false;
		}

		if (showHelp) {
			target.addVariable(this, "showHelp", true);
			showHelp = false;
		}
	}

	/** Deserialize changes received from client. */
	@SuppressWarnings("unchecked")
	@Override
	public void changeVariables(Object source, Map variables) {
		super.changeVariables(source, variables);

		if (variables.containsKey("challenge")) {
			challenge = (String) variables.get("challenge");
		}

		if (variables.containsKey("response")) {
			response = (String) variables.get("response");
		}
	}

	@Override
	public void validate() throws InvalidValueException {
		super.validate();

		if (challenge == null || response == null)
			throw new InvalidValueException("null challenge/response");

		ReCaptcha r = ReCaptchaFactory.newReCaptcha(publicKey, privateKey, true);

		if (!r.checkAnswer(
				RecaptchaField.getThreadLocalRequest().getRemoteAddr().toString(), challenge,
				response).isValid()) {
			throw new InvalidValueException("reCaptcha incorrect");
		}
	}

	@Override
	public Object getValue() {
		return response;
	}

	@Override
	@Deprecated
	public void setValue(Object newValue) throws ReadOnlyException,
			ConversionException {
		super.setValue("");
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public void switchType(String type) {
		this.type = type;
		requestRepaint();
	}

	public void reload() {
		reload = true;
		requestRepaint();
	}

	public void focusResponseField() {
		focusResponseField = true;
		requestRepaint();
	}

	public void showHelp() {
		showHelp = true;
		requestRepaint();
	}

}


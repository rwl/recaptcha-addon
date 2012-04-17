package org.vaadin.addon.recaptcha;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;

import org.vaadin.addon.recaptcha.gwt.client.VRecaptchaComponent;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.service.ApplicationContext;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.TextField;

@ClientWidget(VRecaptchaComponent.class)
public class RecaptchaField extends TextField {

	private static final ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<HttpServletRequest>();

	public static HttpServletRequest getThreadLocalRequest() {
		return RecaptchaField.REQUEST.get();
	}

	private String challenge;
	private String response;
	private String privateKey = "6LeeYtASAAAAAH537ZY4VIgrLxJJqOiwjg5ZYTNi";
	private String publicKey = "6LeeYtASAAAAAH-oArDa8zkFQLwUzE4UtLDxDkxZ";

	public RecaptchaField() {
		super();
		listenForRequests();
	}

	public RecaptchaField(Property dataSource) {
		super(dataSource);
		listenForRequests();
	}

	public RecaptchaField(String caption, Property dataSource) {
		super(caption, dataSource);
		listenForRequests();
	}

	public RecaptchaField(String caption, String value) {
		super(caption, value);
		listenForRequests();
	}

	public RecaptchaField(String caption) {
		super(caption);
		listenForRequests();
	}

	private void listenForRequests() {
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
	}

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

		if (challenge == null || response == null) return;

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

}

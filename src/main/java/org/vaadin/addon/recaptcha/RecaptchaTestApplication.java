package org.vaadin.addon.recaptcha;

import com.claudiushauptmann.gwt.recaptcha.client.Recaptcha;
import com.vaadin.Application;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class RecaptchaTestApplication extends Application
{
    private Window window;

    @Override
    public void init()
    {
        window = new Window("Recaptcha Test");
        setMainWindow(window);

        VerticalLayout layout = new VerticalLayout();
        window.addComponent(layout);

        final RecaptchaField recaptcha = new RecaptchaField();
        recaptcha.switchType(Recaptcha.IMAGE);
        layout.addComponent(recaptcha);

        layout.addComponent(new Button("Submit", new Button.ClickListener() {

		public void buttonClick(ClickEvent event) {
			try {
				recaptcha.validate();
				window.showNotification("Passed captcha");
			} catch (InvalidValueException e) {
				window.showNotification("Failed captcha");
			}
			recaptcha.reload();
		}
	}));

        layout.addComponent(new Button("Reload", new Button.ClickListener() {
		public void buttonClick(ClickEvent event) {
			recaptcha.reload();
		}
	}));

        layout.addComponent(new Button("Focus response field", new Button.ClickListener() {
		public void buttonClick(ClickEvent event) {
			recaptcha.focusResponseField();
		}
	}));

        layout.addComponent(new Button("Show help", new Button.ClickListener() {
		public void buttonClick(ClickEvent event) {
			recaptcha.showHelp();
		}
	}));

        layout.addComponent(new Button("Switch type", new Button.ClickListener() {
        	String type = Recaptcha.AUDIO;
		public void buttonClick(ClickEvent event) {
			recaptcha.switchType(type);
			type = type == Recaptcha.IMAGE ? Recaptcha.AUDIO : Recaptcha.IMAGE;
		}
	}));
    }

}

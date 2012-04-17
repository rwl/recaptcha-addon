package org.vaadin.addon.recaptcha;

import com.vaadin.Application;
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
        layout.addComponent(recaptcha);

        layout.addComponent(new Button("Submit", new Button.ClickListener() {

		public void buttonClick(ClickEvent event) {
			recaptcha.validate();
		}
	}));
    }

}

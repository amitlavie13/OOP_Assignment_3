package view;

import utils.callbacks.MessageCallback;

public abstract class View {

    public abstract void display(String msg);
    public MessageCallback getCallback() {
        return this::display;
    }
}

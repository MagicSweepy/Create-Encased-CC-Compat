package net.Rarin.create_connected_encased.compat.kubejs;

public class KJSExternalHandlerImpl implements KJSExternalHandler{
    @Override
    public void dispatchRegisterEvent() {
        CCEncasedKJSEvents.REGISTER_SETS.post(new RegisterSetsEvent());
    }
}

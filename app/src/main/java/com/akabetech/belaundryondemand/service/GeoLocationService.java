package com.akabetech.belaundryondemand.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GeoLocationService extends IntentService {
    private static final String ACTION_LOCATION = "com.akabane.beasliappcustomer.service.action.GETLOCATION";

    private static final String PARAM_POSITION = "com.akabane.beasliappcustomer.service.extra.POSITION";

    public GeoLocationService() {
        super("GeoLocationService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String strLocation) {
        Intent intent = new Intent(context, GeoLocationService.class);
        intent.setAction(ACTION_LOCATION);
        intent.putExtra(PARAM_POSITION,strLocation);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            String location = intent.getStringExtra(PARAM_POSITION);
            handleLocation(location);
        }
    }

    private void handleLocation(String location){
        
    }
}

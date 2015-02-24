package gs.meetin.connector;

import android.content.Intent;

import com.google.gson.Gson;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import gs.meetin.connector.dto.SuggestionSource;

import java.util.ArrayList;


public class CalendarConnectorPlugin extends CordovaPlugin {

	public static final String ACTION_SIGN_IN = "signIn";
	public static final String ACTION_SIGN_OUT = "signOut";
	public static final String ACTION_START_SERVICE = "startService";
	public static final String ACTION_STOP_SERVICE = "stopService";
	public static final String ACTION_GET_CALENDARS = "getCalendars";
	public static final String ACTION_GET_EVENTS_FROM_CALENDAR = "getEventsFromCalendar";

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(final String action, final JSONArray data, final CallbackContext callbackContext) {

    	if (ACTION_SIGN_IN.equals(action)) {
        	return signIn(data, callbackContext);

        } else if (ACTION_SIGN_OUT.equals(action)) {
        	return signOut(callbackContext);

        } else if (ACTION_START_SERVICE.equals(action)) {
        	return startCalendarService(callbackContext);

        } else if (ACTION_STOP_SERVICE.equals(action)) {
        	return stopCalendarService(callbackContext);

        } else if (ACTION_GET_CALENDARS.equals(action)) {
        	return getCalendars(callbackContext);

    	} else if (ACTION_GET_EVENTS_FROM_CALENDAR.equals(action)) {
    		return getEventsFromCalendar(callbackContext);
        }

        return false;
    }

    private boolean signIn(final JSONArray data, final CallbackContext callbackContext) {

		try {
			String userId = data.getString(0);
			String token  = data.getString(1);
			String email  = data.getString(2);

	    	SessionManager sessionManager = new SessionManager(cordova.getActivity().getApplicationContext());
	    	sessionManager.signIn(userId, token, email);

            callbackContext.success();
            return true;

		} catch (JSONException e) {
			callbackContext.error(e.getMessage());
			return false;
		}
    }

    private boolean signOut(final CallbackContext callbackContext) {
    	SessionManager sessionManager = new SessionManager(cordova.getActivity().getApplicationContext());
    	sessionManager.signOut();

        callbackContext.success();
        return true;
    }

    private boolean startCalendarService(final CallbackContext callbackContext) {
        Intent serviceIntent = new Intent(cordova.getActivity().getApplicationContext(), ConnectorService.class);
        cordova.getActivity().startService(serviceIntent);

        callbackContext.success();
        return true;
    }

    private boolean stopCalendarService(final CallbackContext callbackContext) {
        Intent serviceIntent = new Intent(cordova.getActivity().getApplicationContext(), ConnectorService.class);
        cordova.getActivity().stopService(serviceIntent);

        callbackContext.success();
        return true;
    }

    private boolean getCalendars(final CallbackContext callbackContext) {
		ArrayList<SuggestionSource> suggestionSources = new CalendarManager().getCalendars(cordova.getActivity().getApplicationContext());
        String response = new Gson().toJson(suggestionSources);
        callbackContext.success(response);
        return true;
    }

    private boolean getEventsFromCalendar(final CallbackContext callbackContext) {

        callbackContext.success();
        return true;
    }
}

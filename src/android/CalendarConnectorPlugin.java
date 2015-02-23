package gs.meetin.connector;

import android.content.Intent;

import com.google.gson.Gson;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;

import gs.meetin.connector.dto.SuggestionSource;

import java.util.ArrayList;


public class CalendarConnectorPlugin extends CordovaPlugin {

	public static final String ACTION_GET_CALENDARS = "getCalendars";
	public static final String ACTION_GET_EVENTS_FROM_CALENDAR = "getEventsFromCalendar";
	public static final String ACTION_START_SERVICE = "startService";
	public static final String ACTION_STOP_SERVICE = "stopService";

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(final String action, final JSONArray data, final CallbackContext callbackContext) {

    	if (ACTION_GET_CALENDARS.equals(action)) {
    		ArrayList<SuggestionSource> suggestionSources = new CalendarManager().getCalendars(cordova.getActivity().getApplicationContext());
            String response = new Gson().toJson(suggestionSources);
            callbackContext.success(response);
            return true;
    	} else if (ACTION_GET_EVENTS_FROM_CALENDAR.equals(action)) {
    		ArrayList<SuggestionSource> suggestionSources = new CalendarManager().getCalendars(cordova.getActivity().getApplicationContext());
    		String response = new Gson().toJson(suggestionSources);
            callbackContext.success(response);
            return true;
        } else if (ACTION_START_SERVICE.equals(action)) {
        	startCalendarService();
            callbackContext.success();
            return true;
        }else if (ACTION_STOP_SERVICE.equals(action)) {
        	stopCalendarService();
            callbackContext.success();
            return true;
        }
        return false;
    }

    private void startCalendarService() {
        Intent serviceIntent = new Intent(cordova.getActivity().getApplicationContext(), ConnectorService.class);
        cordova.getActivity().startService(serviceIntent);
    }

    private void stopCalendarService() {
        Intent serviceIntent = new Intent(cordova.getActivity().getApplicationContext(), ConnectorService.class);
        cordova.getActivity().stopService(serviceIntent);
    }
}

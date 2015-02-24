package gs.meetin.connector;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import de.greenrobot.event.EventBus;
import gs.meetin.connector.events.SuggestionEvent;
import gs.meetin.connector.events.UIEvent;

import static gs.meetin.connector.events.Event.EventType.SET_BUTTONS_ENABLED;
import static gs.meetin.connector.events.Event.EventType.UPDATE_SUGGESTIONS;

public class ConnectorService extends IntentService {

    private boolean running = false;

    private SuggestionManager suggestionManager;

    public ConnectorService() {
        super("ConnectorService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Mtn.gs", "Starting service...");

        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        running = true;

        suggestionManager = new SuggestionManager(this);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        Log.d("Mtn.gs", "Stopping service...");
        running = false;
        suggestionManager = null;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        while (running) {
            synchronized (this) {
                try {
                    Log.d("Mtn.gs", "Syncing suggestions... ");

                    /*SuggestionEvent suggestionEvent = new SuggestionEvent(UPDATE_SUGGESTIONS);
                    suggestionEvent.putBoolean("forceUpdate", false);
                    EventBus.getDefault().post(suggestionEvent);*/

                    wait(Constants.updateInterval);
                } catch (Exception e) {
                }
            }
        }
    }

    public void onEvent(SuggestionEvent event) {
        switch (event.getType()) {

            case UPDATE_SUGGESTIONS:
                /*if(!suggestionManager.update(event.getBoolean("forceUpdate"))) {
                    EventBus.getDefault().post(new UIEvent(SET_BUTTONS_ENABLED));
                }*/
                break;
        }
    }
}

// Init the plugin
var CalendarConnector = function () {

};

CalendarConnector.prototype.failure = function(e) {
  console.log("error", e);
};

CalendarConnector.prototype.execute = function(name, callback, args) {
  ret = cordova.exec(
    callback, // called when signature capture is successful
    this.failure, // called when signature capture encounters an error
    'CalendarConnectorPlugin', // Tell cordova that we want to run "PushNotificationPlugin"
    name, // Tell the plugin the action we want to perform
    args // List of arguments to the plugin
  );
  return ret;
};

CalendarConnector.prototype.startService = function(callback) {
  this.execute("startService", callback, []);
};

CalendarConnector.prototype.stopService = function(callback) {
  this.execute("stopService", callback, []);
};

module.exports = new CalendarConnector();

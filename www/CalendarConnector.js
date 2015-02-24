// Init the plugin
var CalendarConnector = function () {

};

CalendarConnector.prototype.failure = function(e) {
  console.log("error", e);
};

CalendarConnector.prototype.execute = function(name, callback, args) {
  if (args.length === 0) {
    args = [];
  }

  ret = cordova.exec(
    callback, // called when signature capture is successful
    this.failure, // called when signature capture encounters an error
    'CalendarConnectorPlugin', // Tell cordova that we want to run "PushNotificationPlugin"
    name, // Tell the plugin the action we want to perform
    args // List of arguments to the plugin
  );
  return ret;
};

CalendarConnector.prototype.signIn = function(userId, token, email, callback) {
  this.execute("signIn", callback, [userId, token, email]);
};

CalendarConnector.prototype.signOut = function(callback) {
  this.execute("signOut", callback);
};

CalendarConnector.prototype.startService = function(callback) {
  this.execute("startService", callback);
};

CalendarConnector.prototype.stopService = function(callback) {
  this.execute("stopService", callback);
};

module.exports = new CalendarConnector();

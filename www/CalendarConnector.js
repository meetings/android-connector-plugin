// Init the plugin
var CalendarConnector = function () {

};

CalendarConnector.prototype.failure = function(e) {
  console.log("error", e);
};

CalendarConnector.prototype.execute = function(name, callback, args) {
  if (typeof(args) === "undefined") {
    args = [];
  }

  ret = cordova.exec(
    callback, // called when signature capture is successful
    this.failure, // called when signature capture encounters an error
    'CalendarConnectorPlugin', // Tell cordova that we want to run "CalendarConnectorPlugin"
    name, // Tell the plugin the action we want to perform
    args // List of arguments to the plugin
  );
  return ret;
};

CalendarConnector.prototype.init = function(appConfig, callback) {
  appConfig = appConfig || {};

  this.execute("init", callback, [appConfig.apiBaseUrl, appConfig.pollInterval]);
};

CalendarConnector.prototype.getUserId = function(callback) {
  this.execute("getUserId", callback);
};

CalendarConnector.prototype.getToken = function(callback) {
  this.execute("getToken", callback);
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

CalendarConnector.prototype.forceUpdate = function(callback) {
  this.execute("forceUpdate", callback);
};

module.exports = new CalendarConnector();

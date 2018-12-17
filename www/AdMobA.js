'use strict';


var AdMob = {};


AdMob.enum = {
    ADTYPE_BANNER: "banner",
    ADTYPE_INTERSTITIAL: "interstitial",
    ADTYPE_NATIVE: "native",
    ADTYPE_REWARDVIDEO: "rewardedvideo",

    EVENT_ON_AD_CLOSED: "onAdClosed",
    EVENT_ON_AD_FAILED_TO_LOAD: "onAdFailedToLoad",
    EVENT_ON_AD_LEFT_APPLICATION: "onAdLeftApplication",
    EVENT_ON_AD_OPENED: "onAdOpened",
    EVENT_ON_AD_LOADED: "onAdLoaded",
    EVENT_ON_AD_CLICKED: "onAdClicked",
    EVENT_ON_AD_IMPRESSION: "onAdImpression",
    EVENT_ON_REWARDED_VIDEO_STARTED: "onRewardedVideoStarted",
    EVENT_ON_REWARDED_VIDEO_COMPLETED: "onRewardedVideoCompleted",
    EVENT_ON_REWARDED: "onRewarded",
};


var initialized = false;

/**
 * Returns the state of initialization
 */
AdMob.isInitialized = function isInitialized() {
    return initialized;
};

/**
 * init
 * @param {Boolean} params.debug
 * @param {String} params.admobAppKey
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.init = function (params) {

    params = defaults(params, {debug: false});

    if (params.hasOwnProperty('admobAppKey') === false) {
        throw new Error('AdMob.init - admobAppKey is required');
    }

    callPlugin('init', [params.admobAppKey], function () {

        initialized = true;

        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);

};

/**
 * createInterstitial
 * @param {String} params.unitId
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.createInterstitial = function (params) {
    // "ca-app-pub-3940256099942544/1033173712"
    // params = defaults(params, {unitId: ""});

    if (params.hasOwnProperty('unitId') === false) {
        throw new Error('AdMob.createInterstitial - unitId is required');
    }

    callPlugin('createInterstitial', [params.unitId], function () {


        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);
};


/**
 * loadInterstitial
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.loadInterstitial = function (params) {

    callPlugin('loadInterstitial', [], function () {


        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);
};

/**
 * showInterstitial
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.showInterstitial = function (params) {

    callPlugin('showInterstitial', [], function () {


        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);
};

/**
 * createBanner
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.createBanner = function (params) {

    callPlugin('createBanner', [], function () {


        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);
};

/**
 * loadBanner
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.loadBanner = function (params) {

    callPlugin('loadBanner', [], function () {


        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);
};

/**
 * showBanner
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.showBanner = function (params) {

    callPlugin('showBanner', [], function () {


        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);
};


/**
 * hideBanner
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.hideBanner = function (params) {

    callPlugin('hideBanner', [], function () {


        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);
};


/**
 * Helper function to call cordova plugin
 * @param {String} name - function name to call
 * @param {Array} params - optional params
 * @param {Function} onSuccess - optional on sucess function
 * @param {Function} onFailure - optional on failure functioin
 */
function callPlugin(name, params, onSuccess, onFailure) {
    cordova.exec(function callPluginSuccess(result) {

        if (isFunction(onSuccess)) {
            onSuccess(result);
        }
    }, function callPluginFailure(error) {
        if (isFunction(onFailure)) {
            onFailure(error)
        }
    }, 'AdMobPlugin', name, params);
}


/**
 * Helper function to check if a function is a function
 * @param {Object} functionToCheck - function to check if is function
 */
function isFunction(functionToCheck) {
    var getType = {};
    var isFunction = functionToCheck && getType.toString.call(functionToCheck) === '[object Function]';
    return isFunction === true;
}


/**
 * Helper function to do a shallow defaults (merge). Does not create a new object, simply extends it
 * @param {Object} o - object to extend
 * @param {Object} defaultObject - defaults to extend o with
 */
function defaults(o, defaultObject) {

    if (typeof o === 'undefined') {
        return defaults({}, defaultObject);
    }

    for (var j in defaultObject) {
        if (defaultObject.hasOwnProperty(j) && o.hasOwnProperty(j) === false) {
            o[j] = defaultObject[j];
        }
    }

    return o;
}


if (typeof module !== undefined && module.exports) {
    module.exports = AdMob;
}



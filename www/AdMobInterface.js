'use strict';


var AdMobInterafce = function (params) {
    this.init(params);
};


var log = function (funcName, message) {
    console.log("AdMobInterafce" + message);
};


AdMobInterafce.prototype._initListener = function () {
    window.addEventListener(AdMob.EVENT_ON_AD_CLOSED, function (data) {
        switch (data["adType"]) {
            case AdMob.ADTYPE_BANNER:
                break;
            case AdMob.ADTYPE_INTERSTITIAL:
                break;
            case AdMob.ADTYPE_NATIVE:
                break;
            case AdMob.ADTYPE_REWARDVIDEO:
                break;
            default:
                break;
        }

    }.bind(this));
    window.addEventListener(AdMob.EVENT_ON_AD_FAILED_TO_LOAD, function (data) {
        switch (data["adType"]) {
            case AdMob.ADTYPE_BANNER:
                break;
            case AdMob.ADTYPE_INTERSTITIAL:
                break;
            case AdMob.ADTYPE_NATIVE:
                break;
            case AdMob.ADTYPE_REWARDVIDEO:
                break;
            default:
                break;
        }
    }.bind(this));
    window.addEventListener(AdMob.EVENT_ON_AD_LEFT_APPLICATION, function (data) {
        switch (data["adType"]) {
            case AdMob.ADTYPE_BANNER:
                break;
            case AdMob.ADTYPE_INTERSTITIAL:
                break;
            case AdMob.ADTYPE_NATIVE:
                break;
            case AdMob.ADTYPE_REWARDVIDEO:
                break;
            default:
                break;
        }
    }.bind(this));
    window.addEventListener(AdMob.EVENT_ON_AD_OPENED, function (data) {
        switch (data["adType"]) {
            case AdMob.ADTYPE_BANNER:
                break;
            case AdMob.ADTYPE_INTERSTITIAL:
                break;
            case AdMob.ADTYPE_NATIVE:
                break;
            case AdMob.ADTYPE_REWARDVIDEO:
                break;
            default:
                break;
        }
    }.bind(this));
    window.addEventListener(AdMob.EVENT_ON_AD_LOADED, function (data) {
        switch (data["adType"]) {
            case AdMob.ADTYPE_BANNER:
                break;
            case AdMob.ADTYPE_INTERSTITIAL:
                break;
            case AdMob.ADTYPE_NATIVE:
                break;
            case AdMob.ADTYPE_REWARDVIDEO:
                break;
            default:
                break;
        }
    }.bind(this));
    window.addEventListener(AdMob.EVENT_ON_AD_CLICKED, function (data) {
        switch (data["adType"]) {
            case AdMob.ADTYPE_BANNER:
                break;
            case AdMob.ADTYPE_INTERSTITIAL:
                break;
            case AdMob.ADTYPE_NATIVE:
                break;
            case AdMob.ADTYPE_REWARDVIDEO:
                break;
            default:
                break;
        }
    }.bind(this));
    window.addEventListener(AdMob.EVENT_ON_AD_IMPRESSION, function (data) {
        switch (data["adType"]) {
            case AdMob.ADTYPE_BANNER:
                break;
            case AdMob.ADTYPE_INTERSTITIAL:
                break;
            case AdMob.ADTYPE_NATIVE:
                break;
            case AdMob.ADTYPE_REWARDVIDEO:
                break;
            default:
                break;
        }
    }.bind(this));
    window.addEventListener(AdMob.EVENT_ON_REWARDED_VIDEO_STARTED, function (data) {
        switch (data["adType"]) {
            case AdMob.ADTYPE_BANNER:
                break;
            case AdMob.ADTYPE_INTERSTITIAL:
                break;
            case AdMob.ADTYPE_NATIVE:
                break;
            case AdMob.ADTYPE_REWARDVIDEO:
                break;
            default:
                break;
        }
    }.bind(this));
    window.addEventListener(AdMob.EVENT_ON_REWARDED_VIDEO_COMPLETED, function (data) {
        switch (data["adType"]) {
            case AdMob.ADTYPE_BANNER:
                break;
            case AdMob.ADTYPE_INTERSTITIAL:
                break;
            case AdMob.ADTYPE_NATIVE:
                break;
            case AdMob.ADTYPE_REWARDVIDEO:
                break;
            default:
                break;
        }
    }.bind(this));
    window.addEventListener(AdMob.EVENT_ON_REWARDED, function (data) {
        switch (data["adType"]) {
            case AdMob.ADTYPE_BANNER:
                break;
            case AdMob.ADTYPE_INTERSTITIAL:
                break;
            case AdMob.ADTYPE_NATIVE:
                break;
            case AdMob.ADTYPE_REWARDVIDEO:
                break;
            default:
                break;
        }
    }.bind(this));

};


/**
 * init
 * @param {Boolean} params.debug
 * @param {String} params.admobAppKey
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMobInterafce.prototype.init = function (params) {
    params = defaults(params, {debug: false});

    AdMob.init({
        debug: params.debug,
        admobAppKey: params.admobAppKey,
        onFailure: function () {
            log("init", "onFailure: init....");
        }.bind(this),
        onSuccess: function () {


            this._initListener();


        }.bind(this)


    });


};


AdMobInterafce.prototype.showBanner = function () {

};


AdMobInterafce.prototype.hideBanner = function () {

};


AdMobInterafce.prototype.showInterstitial = function () {

};


AdMobInterafce.prototype.showRewardedVideo = function () {

};


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
    module.exports = AdMobInterafce;
}
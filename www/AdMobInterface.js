'use strict';


/**
 * init
 * @param {Boolean} params.runType
 * @param {String} params.testDevice
 * @param {String} params.admobAppId
 * @param {String} params.admobUnitIdBanner
 * @param {String} params.admobUnitIdInterstitial
 * @param {String} params.admobUnitIdInterstitialVideo
 * @param {String} params.admobUnitIdRewardedVideo
 * @param {String} params.admobUnitIdNativeAdvanced
 * @param {String} params.admobUnitIdNativeAdvancedVideo
 */
var AdMobInterafce = function (params) {
    this.init(params);
};


var log = function (funcName, message) {
    console.log("<AdMobInterafce><log>-<" + funcName + "> msg:" + message);
};


AdMobInterafce.prototype._initListener = function () {
    window.addEventListener(AdMob.EVENT_ON_AD_CLOSED, function (data) {
        console.log("EVENT_ON_AD_CLOSED data: " + data);
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
        console.log("EVENT_ON_AD_FAILED_TO_LOAD data: " + data);
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
        console.log("EVENT_ON_AD_LEFT_APPLICATION data: " + data);
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
        console.log("EVENT_ON_AD_OPENED data: " + data);
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
        console.log("EVENT_ON_AD_LOADED data: " + data);
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
        console.log("EVENT_ON_AD_CLICKED data: " + data);
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
        console.log("EVENT_ON_AD_IMPRESSION data: " + data);
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
        console.log("EVENT_ON_REWARDED_VIDEO_STARTED data: " + data);
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
        console.log("EVENT_ON_REWARDED_VIDEO_COMPLETED data: " + data);
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
        console.log("EVENT_ON_REWARDED data: " + data);
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
 * @param {Boolean} params.runType
 * @param {String} params.testDevice
 * @param {String} params.admobAppId
 * @param {String} params.admobUnitIdBanner
 * @param {String} params.admobUnitIdInterstitial
 * @param {String} params.admobUnitIdInterstitialVideo
 * @param {String} params.admobUnitIdRewardedVideo
 * @param {String} params.admobUnitIdNativeAdvanced
 * @param {String} params.admobUnitIdNativeAdvancedVideo
 */
AdMobInterafce.prototype.init = function (params) {
    params = defaults(params, {
        runType: this.enum.RUN_TYPE_PROD,
        testDevice: "",
        admobAppId: "",
        admobUnitIdBanner: "",
        admobUnitIdInterstitial: "",
        admobUnitIdInterstitialVideo: "",
        admobUnitIdRewardedVideo: "",
        admobUnitIdNativeAdvanced: "",
        admobUnitIdNativeAdvancedVideo: ""
    });

    AdMob.init({
        runType: params.runType,
        testDevice: params.testDevice,
        admobAppId: params.admobAppId,
        admobUnitIdBanner: params.admobUnitIdBanner,
        admobUnitIdInterstitial: params.admobUnitIdInterstitial,
        admobUnitIdInterstitialVideo: params.admobUnitIdInterstitialVideo,
        admobUnitIdRewardedVideo: params.admobUnitIdRewardedVideo,
        admobUnitIdNativeAdvanced: params.admobUnitIdNativeAdvanced,
        admobUnitIdNativeAdvancedVideo: params.admobUnitIdNativeAdvancedVideo,


        onFailure: function () {
            log("init", "onFailure: init....");
        }.bind(this),


        onSuccess: function () {
            console.log("AdMob.init...onSuccess.....");

            this._initListener();


        }.bind(this)


    });


};


AdMobInterafce.prototype.showBanner = function () {
    AdMob.showBanner();
};


AdMobInterafce.prototype.hideBanner = function () {
    AdMob.hideBanner();
};


AdMobInterafce.prototype.showInterstitial = function (params) {
    AdMob.showInterstitial({});
};


AdMobInterafce.prototype.showRewardedVideo = function (params) {
    AdMob.showRewardedVideo();
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
package me.mingz.cordova.admob;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

public class AdMobPlugin extends CordovaPlugin {

    String TAG = "<AdMobPlugin>: ";

    public static final String ADTYPE_BANNER = "banner";
    public static final String ADTYPE_INTERSTITIAL = "interstitial";
    public static final String ADTYPE_NATIVE = "native";
    public static final String ADTYPE_REWARDVIDEO = "rewardedvideo";

    public static final String EVENT_ON_AD_CLOSED = "onAdClosed";
    public static final String EVENT_ON_AD_FAILED_TO_LOAD = "onAdFailedToLoad";
    public static final String EVENT_ON_AD_LEFT_APPLICATION = "onAdLeftApplication";
    public static final String EVENT_ON_AD_OPENED = "onAdOpened";
    public static final String EVENT_ON_AD_LOADED = "onAdLoaded";
    public static final String EVENT_ON_AD_CLICKED = "onAdClicked";
    public static final String EVENT_ON_AD_IMPRESSION = "onAdImpression";
    public static final String EVENT_ON_REWARDED_VIDEO_STARTED = "onRewardedVideoStarted";
    public static final String EVENT_ON_REWARDED_VIDEO_COMPLETED = "onRewardedVideoCompleted";
    public static final String EVENT_ON_REWARDED = "onRewarded";


    private InterstitialAd mInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;
    private AdView mBannerView;


    /*************************utils******************************/

    public String getErrorReason(int errorCode) {
        String errorReason = "";
        switch (errorCode) {
            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                errorReason = "Internal error";
                break;
            case AdRequest.ERROR_CODE_INVALID_REQUEST:
                errorReason = "Invalid request";
                break;
            case AdRequest.ERROR_CODE_NETWORK_ERROR:
                errorReason = "Network Error";
                break;
            case AdRequest.ERROR_CODE_NO_FILL:
                errorReason = "No fill";
                break;
            default:
                errorReason = "Unknown Error";
                break;
        }
        return errorReason;
    }


    private void fireWindowEvent(final String event) {
        final CordovaWebView view = this.webView;
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.loadUrl(String.format("javascript:cordova.fireWindowEvent('%s');", event));
            }
        });
    }

    private void fireWindowEvent(final String event, final JSONObject data) {
        final CordovaWebView view = this.webView;
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.loadUrl(String.format("javascript:cordova.fireWindowEvent('%s', %s);", event, data.toString()));
            }
        });
    }

    private void fireWindowEvent(final String event, final String adType) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.putOpt("adType", adType);
        } catch (JSONException e) {
            Log.e(TAG, "fireWindowEvent: e:", e);
        }
        fireWindowEvent(event, jsonObj);
    }


    private void fireWindowEvent(final String event, final String adType, final JSONObject data) {
        try {
            data.put("adType", adType);
        } catch (JSONException e) {
            Log.e(TAG, "fireWindowEvent: e: ", e);
        }

        fireWindowEvent(event, data);

    }


    private void fireWindowEvent(final String event, final String adType, final int errCode) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.putOpt("adType", adType);
            jsonObj.putOpt("errCode", errCode);
            jsonObj.putOpt("errReason", getErrorReason(errCode));
        } catch (JSONException e) {
            Log.e(TAG, "fireWindowEvent: e:", e);
        }
        fireWindowEvent(event, jsonObj);
    }


    private Activity getActivity() {
        return cordova.getActivity();
    }

    /*************************overwrite******************************/

    @Override
    protected void pluginInitialize() {
        super.pluginInitialize();
    }

    @Override
    public void onResume(boolean multitasking) {
        mRewardedVideoAd.resume(this.getActivity());
        mBannerView.pause();
        super.onResume(multitasking);
    }


    @Override
    public void onPause(boolean multitasking) {
        mRewardedVideoAd.pause(this.getActivity());
        mBannerView.resume();
        super.onPause(multitasking);
    }


    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this.getActivity());
        mBannerView.destroy();
        super.onDestroy();
    }


    @Override
    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {

        if (action.equals("init")) {
            this.execActionInit(args, callbackContext);
            return true;
        } else if (action.equals("createInterstitial")) {
            this.execActionCreateInterstitial(args, callbackContext);
            return true;
        } else if (action.equals("loadInterstitial")) {
            this.execActionLoadInterstitial(args, callbackContext);
            return true;
        } else if (action.equals("showInterstitial")) {
            this.execActionShowInterstitial(args, callbackContext);
            return true;
        } else if (action.equals("createRewardedVideo")) {
            this.execActionCreateRewardedVideo(args, callbackContext);
            return true;
        } else if (action.equals("loadRewardedVideo")) {
            this.execActionLoadRewardedVideo(args, callbackContext);
            return true;
        } else if (action.equals("showRewardedVideo")) {
            this.execActionShowRewardedVideo(args, callbackContext);
            return true;
        } else if (action.equals("createBanner")) {
            this.execActionCreateBanner(args, callbackContext);
            return true;
        } else if (action.equals("showBanner")) {
            this.execActionShowBanner(args, callbackContext);
            return true;
        } else if (action.equals("hideBanner")) {
            this.execActionHideBanner(args, callbackContext);
            return true;
        }
        return super.execute(action, args, callbackContext);
    }

    /*************************execute action******************************/

    private void execActionInit(CordovaArgs args, CallbackContext callbackContext) throws JSONException {


        final String admobAppKey = args.getString(0);


        this.init(admobAppKey);

        callbackContext.success();


    }


    private void init(String admobAppKey) {
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this.getActivity(), admobAppKey);
    }


    private void execActionCreateInterstitial(CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        final String unitId = args.getString(0);
        this.createInterstitial(unitId);
        callbackContext.success();

    }

    private void createInterstitial(final String unitId) {
        if (mInterstitialAd == null) {
            AdMobPlugin self = this;
            mInterstitialAd = new InterstitialAd(this.getActivity());
            mInterstitialAd.setAdUnitId(unitId);
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    // Code to be executed when when the interstitial ad is closed.
                    // Load the next interstitial.
//                    self.loadInterstitial();
                    fireWindowEvent(EVENT_ON_AD_CLOSED, ADTYPE_INTERSTITIAL);
                }

                @Override
                public void onAdFailedToLoad(int errCode) {
                    // Code to be executed when an ad request fails.
                    fireWindowEvent(EVENT_ON_AD_FAILED_TO_LOAD, ADTYPE_INTERSTITIAL, errCode);
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                    fireWindowEvent(EVENT_ON_AD_LEFT_APPLICATION, ADTYPE_INTERSTITIAL);
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when the ad is displayed.
                    fireWindowEvent(EVENT_ON_AD_OPENED, ADTYPE_INTERSTITIAL);
                }

                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    fireWindowEvent(EVENT_ON_AD_LOADED, ADTYPE_INTERSTITIAL);
                }

                @Override
                public void onAdClicked() {
                    // Clicked
                    fireWindowEvent(EVENT_ON_AD_CLICKED, ADTYPE_INTERSTITIAL);
                }

                @Override
                public void onAdImpression() {
                    // Impression
                    fireWindowEvent(EVENT_ON_AD_IMPRESSION, ADTYPE_INTERSTITIAL);
                }

            });
        }


    }


    private void execActionLoadInterstitial(CordovaArgs args, CallbackContext callbackContext) {
        this.loadInterstitial();
        callbackContext.success();
    }


    private void loadInterstitial() {
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
//            AdRequest adRequest = new AdRequest.Builder()
//                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                    .build();

            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        } else {
            Log.d(TAG, "loadInterstitial: isloading or is loaded");
        }

    }


    private void execActionShowInterstitial(CordovaArgs args, CallbackContext callbackContext) {
        this.showInterstitial();
        callbackContext.success();
    }

    private void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void execActionCreateRewardedVideo(CordovaArgs args, CallbackContext callbackContext) {
        this.createRewardedVideo();
        callbackContext.success();
    }


    private void createRewardedVideo() {
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this.getActivity());
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                fireWindowEvent(EVENT_ON_AD_LOADED, ADTYPE_REWARDVIDEO);
            }

            @Override
            public void onRewardedVideoAdOpened() {
                fireWindowEvent(EVENT_ON_AD_OPENED, ADTYPE_REWARDVIDEO);
            }

            @Override
            public void onRewardedVideoStarted() {
                fireWindowEvent(EVENT_ON_REWARDED_VIDEO_STARTED, ADTYPE_REWARDVIDEO);
            }

            @Override
            public void onRewardedVideoAdClosed() {
                fireWindowEvent(EVENT_ON_AD_CLOSED, ADTYPE_REWARDVIDEO);

            }

            @Override
            public void onRewardedVideoCompleted() {
                fireWindowEvent(EVENT_ON_REWARDED_VIDEO_COMPLETED, ADTYPE_REWARDVIDEO);
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                JSONObject jsonObj = new JSONObject();
                try {
                    jsonObj.putOpt("rewardItemType", rewardItem.getType());
                    jsonObj.putOpt("rewardItemAmount", rewardItem.getAmount());
                } catch (JSONException e) {
                    Log.d(TAG, "onRewarded: e: ", e);
                }


                fireWindowEvent(EVENT_ON_REWARDED, ADTYPE_REWARDVIDEO, jsonObj);
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                fireWindowEvent(EVENT_ON_AD_LEFT_APPLICATION, ADTYPE_REWARDVIDEO);
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                fireWindowEvent(EVENT_ON_AD_FAILED_TO_LOAD, ADTYPE_REWARDVIDEO, i);
            }


        });
    }

    private void execActionLoadRewardedVideo(CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        final String unitId = args.getString(0);
        this.loadRewardedVideo(unitId);
        callbackContext.success();

    }

    private void loadRewardedVideo(String unitId) {
        mRewardedVideoAd.loadAd(unitId, new AdRequest.Builder().build());
    }


    private void execActionShowRewardedVideo(CordovaArgs args, CallbackContext callbackContext) {

        this.showRewardedVideo();
        callbackContext.success();

    }

    private void showRewardedVideo() {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }


    private void createBannerView() {
        mBannerView = new AdView(this.getActivity());
        // TODO
        mBannerView.setAdSize(AdSize.BANNER);
//        bannerView.setAdSize(AdSize.SMART_BANNER);

    }


    private void execActionCreateBanner(CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        final String unitId = args.getString(0);
        this.createBanner(unitId);
        callbackContext.success();

    }


    private void createBanner(String unitId) {
        AdMobPlugin self = this;
        createBannerView();
        mBannerView.setAdUnitId(unitId);
        mBannerView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                // Load the next interstitial.
//                    self.loadBanner();
                fireWindowEvent(EVENT_ON_AD_CLOSED, ADTYPE_BANNER);
            }

            @Override
            public void onAdFailedToLoad(int errCode) {
                // Code to be executed when an ad request fails.
                fireWindowEvent(EVENT_ON_AD_FAILED_TO_LOAD, ADTYPE_BANNER, errCode);
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                fireWindowEvent(EVENT_ON_AD_LEFT_APPLICATION, ADTYPE_BANNER);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                fireWindowEvent(EVENT_ON_AD_OPENED, ADTYPE_BANNER);
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                fireWindowEvent(EVENT_ON_AD_LOADED, ADTYPE_BANNER);
            }

            @Override
            public void onAdClicked() {
                // Clicked
                fireWindowEvent(EVENT_ON_AD_CLICKED, ADTYPE_BANNER);
            }

            @Override
            public void onAdImpression() {
                // Impression
                fireWindowEvent(EVENT_ON_AD_IMPRESSION, ADTYPE_BANNER);
            }

        });
    }


    private void execActionLoadBanner(CordovaArgs args, CallbackContext callbackContext) {

        this.loadBanner();
        callbackContext.success();

    }

    private void loadBanner() {
        mBannerView.loadAd(new AdRequest.Builder().build());
    }

    private void execActionShowBanner(CordovaArgs args, CallbackContext callbackContext) {

        this.showBanner();
        callbackContext.success();

    }


    private void showBanner() {

    }

    private void execActionHideBanner(CordovaArgs args, CallbackContext callbackContext) {

        this.hideBanner();
        callbackContext.success();

    }


    private void hideBanner() {

    }


}
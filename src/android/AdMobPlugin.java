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


    public static final String ADSIZE_BANNER = "BANNER";//new AdSize(320, 50, "320x50_mb");
    public static final String ADSIZE_FULL_BANNER = "FULL_BANNER";//new AdSize(468, 60, "468x60_as");
    public static final String ADSIZE_LARGE_BANNER = "LARGE_BANNER";//new AdSize(320, 100, "320x100_as");
    public static final String ADSIZE_LEADERBOARD = "LEADERBOARD";//new AdSize(728, 90, "728x90_as");
    public static final String ADSIZE_MEDIUM_RECTANGLE = "MEDIUM_RECTANGLE";//new AdSize(300, 250, "300x250_as");
    public static final String ADSIZE_WIDE_SKYSCRAPER = "WIDE_SKYSCRAPER";//new AdSize(160, 600, "160x600_as");
    public static final String ADSIZE_SMART_BANNER = "SMART_BANNER";//new AdSize(-1, -2, "smart_banner");


    public static final String TEST_ADMOB_APP_ID = "ca-app-pub-3940256099942544~3347511713";
    public static final String TEST_ADMOB_UNIT_ID_BANNER = "ca-app-pub-3940256099942544/6300978111";
    public static final String TEST_ADMOB_UNIT_ID_INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712";
    public static final String TEST_ADMOB_UNIT_ID_INTERSTITIAL_VIDEO = "ca-app-pub-3940256099942544/8691691433";
    public static final String TEST_ADMOB_UNIT_ID_REWARDED_VIDEO = "ca-app-pub-3940256099942544/5224354917";
    public static final String TEST_ADMOB_UNIT_ID_NATIVE_ADVANCED = "ca-app-pub-3940256099942544/2247696110";
    public static final String TEST_ADMOB_UNIT_ID_NATIVE_ADVANCED_VIDEO = "ca-app-pub-3940256099942544/1044960115";


    private  String admobAppId;
    private  String admobUnitIdBanner;
    private  String admobUnitIdInterstitial;
    private  String admobUnitIdInterstitialVideo;
    private  String admobUnitIdRewardedVideo;
    private  String admobUnitIdNativeAdvanced;
    private  String admobUnitIdNativeAdvancedVideo;

    private  String testDevice = "33BE2250B43518CCDA7DE426D04EE231";
    private String runType = RUN_TYPE_PROD;


    public static final String RUN_TYPE_PROD = "RUN_TYPE_PROD";
    public static final String RUN_TYPE_TEST_WITH_TEST_ID = "RUN_TYPE_TEST_WITH_TEST_ID";
    public static final String RUN_TYPE_TEST_WITH_DEVICE = "RUN_TYPE_TEST_WITH_DEVICE";




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

    public AdSize getBannerSize(String size, int w, int h) {
        switch (size) {
            case ADSIZE_BANNER:
                return AdSize.BANNER;
            case ADSIZE_FULL_BANNER:
                return AdSize.FULL_BANNER;
            case ADSIZE_LARGE_BANNER:
                return AdSize.LARGE_BANNER;
            case ADSIZE_LEADERBOARD:
                return AdSize.LEADERBOARD;
            case ADSIZE_MEDIUM_RECTANGLE:
                return AdSize.MEDIUM_RECTANGLE;
            case ADSIZE_WIDE_SKYSCRAPER:
                return AdSize.WIDE_SKYSCRAPER;
            case ADSIZE_SMART_BANNER:
                return AdSize.SMART_BANNER;
            default:
                return new AdSize(w, h);
        }
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
        if (mRewardedVideoAd != null) {
            mRewardedVideoAd.resume(this.getActivity());
        }
        if (mBannerView != null) {
            mBannerView.pause();
        }


        super.onResume(multitasking);
    }


    @Override
    public void onPause(boolean multitasking) {
        if (mRewardedVideoAd != null) {
            mRewardedVideoAd.pause(this.getActivity());
        }
        if (mBannerView != null) {
            mBannerView.resume();
        }

        super.onPause(multitasking);
    }


    @Override
    public void onDestroy() {
        if (mRewardedVideoAd != null) {
            mRewardedVideoAd.destroy(this.getActivity());
        }
        if (mBannerView != null) {
            mBannerView.destroy();
        }
        super.onDestroy();
    }


    @Override
    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        JSONObject params = args.getJSONObject(0);

        if (action.equals("init")) {
            this.execActionInit(params, callbackContext);
            return true;
        } else if (action.equals("createInterstitial")) {
            this.execActionCreateInterstitial(params, callbackContext);
            return true;
        } else if (action.equals("loadInterstitial")) {
            this.execActionLoadInterstitial(params, callbackContext);
            return true;
        } else if (action.equals("showInterstitial")) {
            this.execActionShowInterstitial(params, callbackContext);
            return true;
        } else if (action.equals("createRewardedVideo")) {
            this.execActionCreateRewardedVideo(params, callbackContext);
            return true;
        } else if (action.equals("loadRewardedVideo")) {
            this.execActionLoadRewardedVideo(params, callbackContext);
            return true;
        } else if (action.equals("showRewardedVideo")) {
            this.execActionShowRewardedVideo(params, callbackContext);
            return true;
        } else if (action.equals("createBanner")) {
            this.execActionCreateBanner(params, callbackContext);
            return true;
        } else if (action.equals("loadBanner")) {
            this.execActionLoadBanner(params, callbackContext);
            return true;
        } else if (action.equals("showBanner")) {
            this.execActionShowBanner(params, callbackContext);
            return true;
        } else if (action.equals("hideBanner")) {
            this.execActionHideBanner(params, callbackContext);
            return true;
        }
        return super.execute(action, args, callbackContext);
    }

    /*************************execute action******************************/

    private void execActionInit(JSONObject args, CallbackContext callbackContext) throws JSONException{



        this.init(args);

        callbackContext.success();


    }


    private void init(JSONObject args) throws JSONException {
        this.testDevice = args.getString("testDevice");

        this.runType = args.getString("runType");

        if(this.runType.equals(RUN_TYPE_TEST_WITH_TEST_ID)){
            this.admobAppId = TEST_ADMOB_APP_ID;
            this.admobUnitIdBanner = TEST_ADMOB_UNIT_ID_BANNER;
            this.admobUnitIdInterstitial = TEST_ADMOB_UNIT_ID_INTERSTITIAL;
            this.admobUnitIdInterstitialVideo = TEST_ADMOB_UNIT_ID_INTERSTITIAL_VIDEO;
            this.admobUnitIdRewardedVideo = TEST_ADMOB_UNIT_ID_REWARDED_VIDEO;
            this.admobUnitIdNativeAdvanced = TEST_ADMOB_UNIT_ID_NATIVE_ADVANCED;
            this.admobUnitIdNativeAdvancedVideo = TEST_ADMOB_UNIT_ID_NATIVE_ADVANCED_VIDEO;
        }else{
            this.admobAppId = args.getString("admobAppId");
            this.admobUnitIdBanner = args.getString("admobUnitIdBanner");
            this.admobUnitIdInterstitial = args.getString("admobUnitIdInterstitial");
            this.admobUnitIdInterstitialVideo = args.getString("admobUnitIdInterstitialVideo");
            this.admobUnitIdRewardedVideo = args.getString("admobUnitIdRewardedVideo");
            this.admobUnitIdNativeAdvanced = args.getString("admobUnitIdNativeAdvanced");
            this.admobUnitIdNativeAdvancedVideo = args.getString("admobUnitIdNativeAdvancedVideo");

        }


        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this.getActivity(), this.admobAppId);
    }


    private void execActionCreateInterstitial(JSONObject args, CallbackContext callbackContext) throws JSONException {

        this.createInterstitial();
        callbackContext.success();

    }

    private void createInterstitial() {
        if (mInterstitialAd == null) {
            AdMobPlugin self = this;
            mInterstitialAd = new InterstitialAd(this.getActivity());
            mInterstitialAd.setAdUnitId(this.admobUnitIdInterstitial);
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


    private void execActionLoadInterstitial(JSONObject args, CallbackContext callbackContext) {
        this.loadInterstitial();
        callbackContext.success();
    }


    private void loadInterstitial() {
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest.Builder builder = new AdRequest.Builder();
            if(this.runType.equals(RUN_TYPE_TEST_WITH_DEVICE)){
                builder.addTestDevice(this.testDevice);
            }
            AdRequest adRequest = builder.build();

            mInterstitialAd.loadAd(adRequest);
        } else {
            Log.d(TAG, "loadInterstitial: isloading or is loaded");
        }

    }


    private void execActionShowInterstitial(JSONObject args, CallbackContext callbackContext) {
        this.showInterstitial();
        callbackContext.success();
    }

    private void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void execActionCreateRewardedVideo(JSONObject args, CallbackContext callbackContext) {
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

    private void execActionLoadRewardedVideo(JSONObject args, CallbackContext callbackContext) throws JSONException {

        this.loadRewardedVideo();
        callbackContext.success();

    }

    private void loadRewardedVideo() {
        AdRequest.Builder builder = new AdRequest.Builder();
        if(this.runType.equals(RUN_TYPE_TEST_WITH_DEVICE)){
            builder.addTestDevice(this.testDevice);
        }
        AdRequest adRequest = builder.build();
        mRewardedVideoAd.loadAd(this.admobUnitIdRewardedVideo, adRequest);
    }


    private void execActionShowRewardedVideo(JSONObject args, CallbackContext callbackContext) {

        this.showRewardedVideo();
        callbackContext.success();

    }

    private void showRewardedVideo() {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }


    private void createBannerView(AdSize size) {
        mBannerView = new AdView(this.getActivity());
        mBannerView.setAdSize(size);

    }


    private void execActionCreateBanner(JSONObject args, CallbackContext callbackContext) throws JSONException {
        final String size = args.getString("size");
        final int w = args.getInt("w");
        final int h = args.getInt("h");
        this.createBanner(getBannerSize(size, w, h));
        callbackContext.success();

    }


    private void createBanner(AdSize size) {
        AdMobPlugin self = this;
        createBannerView(size);
        mBannerView.setAdUnitId(this.admobUnitIdBanner);
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


    private void execActionLoadBanner(JSONObject args, CallbackContext callbackContext) {

        this.loadBanner();
        callbackContext.success();

    }

    private void loadBanner() {
        AdRequest.Builder builder = new AdRequest.Builder();
        if(this.runType.equals(RUN_TYPE_TEST_WITH_DEVICE)){
            builder.addTestDevice(this.testDevice);
        }
        AdRequest adRequest = builder.build();
        mBannerView.loadAd(adRequest);
    }

    private void execActionShowBanner(JSONObject args, CallbackContext callbackContext) {

        this.showBanner();
        callbackContext.success();

    }


    private void showBanner() {

    }

    private void execActionHideBanner(JSONObject args, CallbackContext callbackContext) {

        this.hideBanner();
        callbackContext.success();

    }


    private void hideBanner() {

    }


}
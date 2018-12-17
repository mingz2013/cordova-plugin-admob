window.onload = function () {

    function showlog(message) {
        document.getElementById("log").innerHTML = message;
        // document.write(message);
        console.log(message);
    }


    document.addEventListener('deviceready', function () {
        console.log("deviceready.....");

        //admob app Id
        var admobAppId = "ca-app-pub-3940256099942544~3347511713";
        console.log("admobAppId..." + admobAppId);

        /**
         * Initialize Iron Source Ads
         */
        AdMob.init({


            admobAppId: admobAppId,
            debug: true,


            onSuccess: function () {

                console.log("init.....onSuccess....");


            },
            onFailure: function (data) {
                console.log("onFailure...." + data);
            }
        });

    });


    document.getElementById("loadInterstitialAds").onclick = function () {
        IronSourceAds.loadInterstitial();
    };

    document.getElementById("showInterstitialAds").onclick = function () {
        IronSourceAds.hasInterstitial({
            onSuccess: function (available) {
                if (available) {
                    //Show Interstitial
                    IronSourceAds.showInterstitial();
                }
            }
        });
    };

    document.getElementById("showRewardVideoAds").onclick = function () {
        IronSourceAds.hasRewardedVideo({
            onSuccess: function (available) {
                if (available) {
                    //Show offerwall
                    IronSourceAds.showRewardedVideo();
                }
            }
        });
    };


    document.getElementById("showOfferwall").onclick = function () {
        IronSourceAds.hasOfferwall({
            onSuccess: function (available) {
                if (available) {
                    //Show offerwall
                    IronSourceAds.showOfferwall();
                }
            }
        });
    };


    document.getElementById("loadBannerAds").onclick = function () {
        IronSourceAds.loadBanner();
    };


    document.getElementById("showBannerAds").onclick = function () {
        IronSourceAds.showBanner();
    };

    document.getElementById("hidebannerAds").onclick = function () {
        IronSourceAds.hideBanner();
    };


};


window._adMobInterafce = null;

window.onload = function () {

    function showlog(message) {
        document.getElementById("log").innerHTML = message;
        // document.write(message);
        console.log(message);
    }

    document.addEventListener('deviceready', function () {
        showlog("deviceready.....");

        window._adMobInterafce = new AdMobInterafce({});


    });

    document.getElementById("showInterstitialAds").onclick = function () {
        window._adMobInterafce.showInterstitial();
    };

    document.getElementById("showRewardVideoAds").onclick = function () {
        window._adMobInterafce.showRewardedVideo();
    };

    document.getElementById("showBannerAds").onclick = function () {
        window._adMobInterafce.showBanner();
    };

    document.getElementById("hidebannerAds").onclick = function () {
        window._adMobInterafce.hideBanner();
    };


};


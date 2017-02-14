package netfoxs.coms.callcarddialer.Bean;

/**
 * Created by WIIS on 5/31/2016.
 */
public class Adsclass {

    public String adsimage;
    public String adstime;
    public String adsurl;

    public Adsclass(String adsimage, String adstime, String adsurl) {
        this.adsimage = adsimage;
        this.adstime = adstime;
        this.adsurl = adsurl;
    }

    public String getAdsimage() {
        return adsimage;
    }

    public void setAdsimage(String adsimage) {
        this.adsimage = adsimage;
    }

    public String getAdstime() {
        return adstime;
    }

    public void setAdstime(String adstime) {
        this.adstime = adstime;
    }

    public String getAdsurl() {
        return adsurl;
    }

    public void setAdsurl(String adsurl) {
        this.adsurl = adsurl;
    }
}

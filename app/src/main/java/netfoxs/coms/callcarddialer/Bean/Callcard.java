package netfoxs.coms.callcarddialer.Bean;

import com.orm.SugarRecord;

/**
 * Created by WIIS on 6/3/2016.
 */
public class Callcard extends SugarRecord {
    public String cardeName;
    public String callNumber;
    public String callCode;
    public int position;
    public String state;

    public Callcard() {
    }

    public Callcard(String cardeName, String callNumber, String callCode, int position, String state) {
        this.cardeName = cardeName;
        this.callNumber = callNumber;
        this.callCode = callCode;
        this.position = position;
        this.state = state;
    }

    public String getCardeName() {
        return cardeName;
    }

    public void setCardeName(String cardeName) {
        this.cardeName = cardeName;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getCallCode() {
        return callCode;
    }

    public void setCallCode(String callCode) {
        this.callCode = callCode;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

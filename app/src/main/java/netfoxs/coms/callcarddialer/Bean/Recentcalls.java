package netfoxs.coms.callcarddialer.Bean;

import com.orm.SugarRecord;

/**
 * Created by WIIS on 5/11/2016.
 */
public class Recentcalls extends SugarRecord {


    String displayName;
    String contactNumber;
    String dateTime;

    public Recentcalls() {
    }

    public Recentcalls(String displayName, String contactNumber, String dateTime) {
        this.displayName = displayName;
        this.contactNumber = contactNumber;
        this.dateTime = dateTime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}

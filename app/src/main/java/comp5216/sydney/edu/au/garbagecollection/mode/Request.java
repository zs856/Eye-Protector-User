package comp5216.sydney.edu.au.garbagecollection.mode;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;

/**
 * request Java bean
 */
public class Request implements Serializable {

    public static final String STATUS_COMPLETED = "completed";
    public static final String STATUS_UNCOMPLETED = "uncompleted";

    private String id;
    private String address;
    private Timestamp createdTime;
    private Timestamp scheduledTime;
    private Timestamp finishedTime;
    private String firstName;
    private String lastName;
    private String phoneName;
    private String status;
    private DocumentReference user;

    private String path;

    public Request() {

    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Timestamp scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Timestamp finishedTime) {
        this.finishedTime = finishedTime;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DocumentReference getUser() {
        return user;
    }

    public void setUser(DocumentReference user) {
        this.user = user;
    }

    public String getTime() {
        if (Request.STATUS_UNCOMPLETED.equalsIgnoreCase(getStatus()) && getScheduledTime() != null) {
            return getScheduledTime().toDate().toString();
        } else if (Request.STATUS_COMPLETED.equalsIgnoreCase(getStatus()) && getFinishedTime() != null) {
            return getFinishedTime().toDate().toString();
        } else if (getCreatedTime() != null) {
            return getCreatedTime().toDate().toString();
        } else {
            return "";
        }
    }

    public String getTimeTag() {
        if (Request.STATUS_UNCOMPLETED.equalsIgnoreCase(getStatus()) && getScheduledTime() != null) {
            return "Appointment time";
        } else if (Request.STATUS_COMPLETED.equalsIgnoreCase(getStatus()) && getFinishedTime() != null) {
            return "Finished time";
        } else if (getCreatedTime() != null) {
            return "Created time";
        } else {
            return "";
        }
    }
}

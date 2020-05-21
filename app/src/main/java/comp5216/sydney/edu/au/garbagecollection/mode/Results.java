package comp5216.sydney.edu.au.garbagecollection.mode;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.List;

/**
 * user Java bean
 */
public class Results implements Serializable {

    private String rid;
    private String severity;
    private Timestamp timestamp;
    private String path;
    private DocumentReference user;

    public Results() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getReId() {
        return rid;
    }

    public void setReId(String rid) {
        this.rid = rid;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public DocumentReference getUser() {
        return user;
    }

    public void setUser(DocumentReference user) {
        this.user = user;
    }

}

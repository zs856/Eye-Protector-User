package comp5216.sydney.edu.au.garbagecollection.util;

import android.text.TextUtils;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.garbagecollection.mode.Request;
import comp5216.sydney.edu.au.garbagecollection.mode.Results;
import comp5216.sydney.edu.au.garbagecollection.mode.User;

public class ConvertUtil {

    /**
     * convert from {@link DocumentSnapshot} to {@link User}
     * @param obj
     * @return
     */
    public static User convertToUser(DocumentSnapshot obj) {
        return convertToUser(obj, obj.getId());
    }

    /**
     * convert from {@link DocumentSnapshot} to {@link User}
     * @param obj
     * @param uid
     * @return
     */
    public static User convertToUser(DocumentSnapshot obj, String uid) {
        if (obj == null) {
            return null;
        }
        User user = new User();
        user.setUid(uid);
        user.setEmail((String) getValue(obj, "email"));
        user.setFirstName((String) getValue(obj, "first name"));
        user.setLastName((String) getValue(obj, "last name"));
        user.setUserType((String) getValue(obj, "user type"));
        user.setPhoneNumber((String) getValue(obj, "phone number"));
        user.setAddress((String) getValue(obj, "address"));
        user.setPath(obj.getReference().getPath());
        return user;
    }

    /**
     * convert from {@link DocumentSnapshot} to {@link Request}
     * @param obj
     * @return
     */
    public static Request convertToRequest(DocumentSnapshot obj) {
        if (obj == null) {
            return null;
        }
        Request request = new Request();
        request.setId(obj.getId());
        request.setAddress((String) getValue(obj, "address"));
        request.setCreatedTime(obj.getTimestamp("created time"));
        request.setScheduledTime(obj.getTimestamp("scheduled time"));
        request.setFinishedTime(obj.getTimestamp("finished time"));
        request.setFirstName((String) getValue(obj, "first name"));
        request.setLastName((String) getValue(obj, "last name"));
        request.setStatus((String) getValue(obj, "status"));
        request.setUser(obj.getDocumentReference("user id"));
        request.setPhoneName((String) getValue(obj, "phone number"));
        request.setPath(obj.getReference().getPath());
        return request;
    }

    /**
     * convert from {@link DocumentSnapshot} to {@link Request}
     * @param obj
     * @return
     */
    public static Results convertToResults(DocumentSnapshot obj) {
        if (obj == null) {
            return null;
        }
        Results results = new Results();
        results.setSeverity((String) getValue(obj, "severity"));
        results.setTimestamp(obj.getTimestamp("timestamp"));
        results.setImageUrl((String) getValue(obj,"imageUrl"));
        results.setUser(obj.getDocumentReference("user id"));
        results.setPath(obj.getReference().getPath());
        return results;
    }

    /**
     * convert from {@link DocumentSnapshot} to {@link List< Request >}
     * @param documents
     * @return
     */
    public static List<Request> convertToRequests(QuerySnapshot documents) {
        if (documents == null) {
            return null;
        }
        List<Request> requests = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            requests.add(convertToRequest(document));
        }
        return requests;
    }

    /**
     * convert from {@link DocumentSnapshot} to {@link List< Request >}
     * @param documents
     * @return
     */
    public static List<Results> convertToResults(QuerySnapshot documents) {
        if (documents == null) {
            return null;
        }
        List<Results> results = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            results.add(convertToResults(document));
        }
        return results;
    }
    /**
     * get value from {@link DocumentSnapshot} by key
     * @param obj
     * @param key
     * @param <T>
     * @return
     */
    private static <T> T getValue(DocumentSnapshot obj, String key) {
        try {
            if (TextUtils.isEmpty(key)) {
                return null;
            }
            Object value = obj.get(key);
            return value == null ? null : (T) value;
        } catch (Exception e) {
            return null;
        }
    }

}

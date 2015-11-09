package in.thelattice.gluconnect.libs.icenet;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anton on 10/9/14.
 */
public final class NetworkManager {
    private static final String TAG = NetworkManager.class.getSimpleName();

    public enum RESULT {
        JSONOBJECT,
        JSONARRAY,
        STRING
    }

    private final String baseUrl;
    private final NetworkHelper networkHelper;
    private final String pathUrl;
    private final int method;
    private final TypeToken<?> classTarget;
    private final RESULT resultType;
    private final HashMap<String, Object> bodyRequest;
    private final HashMap<String, String> headers;

    public NetworkManager(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.networkHelper = NetworkHelper.getInstance(builder.context);
        this.pathUrl = builder.pathUrl;
        this.method = builder.method;
        this.classTarget = builder.targetType;
        this.resultType = builder.resultType;
        this.bodyRequest = builder.bodyRequest;
        this.headers = builder.headers;
    }

    private String getUrlConnection(String pathUrl) {
        StringBuilder builder = new StringBuilder();
        builder.append(baseUrl)
                .append(pathUrl);

        return builder.toString();
    }

    private JSONObject createBodyRequest(HashMap<String, Object> bodyRequest) {
        return bodyRequest == null ? null : new JSONObject(bodyRequest);
    }

    private void fromJsonObject(final HashMap<String, String> headers, HashMap<String, Object> bodyRequest, String requestTag, final RequestCallback requestCallback) {
        JsonObjectRequest request = new JsonObjectRequest(method, getUrlConnection(pathUrl), createBodyRequest(bodyRequest), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Object t = new Gson().fromJson(jsonObject.toString(), classTarget.getType());
                if (requestCallback != null)
                    requestCallback.onRequestSuccess(t);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (requestCallback != null) {
                    NetworkResponse response = error.networkResponse;
                    if (response != null)
                        requestCallback.onRequestError(new RequestError(response));
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers != null ? headers : super.getHeaders();
            }
        };

        networkHelper.addToRequestQueue(request, requestTag);
    }

    private void fromJsonArray(final Map<String, String> headers, String requestTag, final RequestCallback requestCallback) {
        JsonArrayRequest request = new JsonArrayRequest(getUrlConnection(pathUrl), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Object t = new Gson().fromJson(jsonArray.toString(), classTarget.getType());
                if (requestCallback != null)
                    requestCallback.onRequestSuccess(t);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (requestCallback != null) {
                    NetworkResponse response = error.networkResponse;
                    if (response != null)
                        requestCallback.onRequestError(new RequestError(response));
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers != null ? headers : super.getHeaders();
            }
        };

        networkHelper.addToRequestQueue(request, requestTag);
    }

    private void fromString(final Map<String, String> headers, String requestTag, final RequestCallback requestCallback) {
        StringRequest request = new StringRequest(method,getUrlConnection(pathUrl), new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                requestCallback.onRequestSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (requestCallback != null) {
                    NetworkResponse response = error.networkResponse;
                    if (response != null)
                        requestCallback.onRequestError(new RequestError(response));
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers != null ? headers : super.getHeaders();
            }
        };

        networkHelper.addToRequestQueue(request, requestTag);
    }

    public void execute(String requestTag, RequestCallback callback) {
        if (resultType == null) {
            throw new IllegalArgumentException("result type must not be null.");
        }

        if (classTarget == null) {
            throw new IllegalArgumentException("class target must not be null.");
        }

        if (pathUrl == null) {
            throw new IllegalArgumentException("path url must not be null.");
        }

        switch (resultType) {
            case JSONARRAY:
                fromJsonArray(headers, requestTag, callback);
                break;
            case JSONOBJECT:
                if (method == Request.Method.POST)
                    if (bodyRequest == null)
                        throw new IllegalArgumentException("body request must not be null.");

                fromJsonObject(headers, bodyRequest, requestTag, callback);
                break;
            case STRING:
                fromString(headers, requestTag, callback);
                break;
            default:
                throw new IllegalArgumentException("response type not found");
        }
    }

    public static class Builder implements INetworkManagerBuilder {
        private String baseUrl;
        private Context context;
        private String pathUrl;
        private int method;
        private RESULT resultType;
        private TypeToken<?> targetType;
        private HashMap<String, Object> bodyRequest;
        private HashMap<String, String> headers;

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setMethod(int method) {
            this.method = method;
            return this;
        }

        public Builder setBodyRequest(@NonNull HashMap<String, Object> bodyRequest) {
            this.bodyRequest = bodyRequest;
            return this;
        }

        public Builder setHeaders(@NonNull HashMap<String, String> headers) {
            this.headers = headers;
            return this;
        }

        @Override
        public INetworkManagerBuilder pathUrl(@NonNull String pathUrl) {
            this.pathUrl = pathUrl;
            return this;
        }

        @Override
        public INetworkManagerBuilder fromJsonObject() {
            this.resultType = RESULT.JSONOBJECT;
            return this;
        }

        @Override
        public INetworkManagerBuilder fromJsonArray() {
            this.resultType = RESULT.JSONARRAY;
            return this;
        }

        @Override
        public NetworkManager fromString() {
            this.resultType = RESULT.STRING;
            this.targetType = TypeToken.get(String.class);
            return new NetworkManager(this);
        }

        @Override
        public NetworkManager mappingInto(@NonNull Class classTarget) {
            this.targetType = TypeToken.get(classTarget);
            return new NetworkManager(this);
        }

        @Override
        public NetworkManager mappingInto(@NonNull TypeToken typeToken) {
            this.targetType = typeToken;
            return new NetworkManager(this);
        }
    }

    public static interface INetworkManagerBuilder {
        /**
         * @param pathUrl
         * @return
         */
        public INetworkManagerBuilder pathUrl(@NonNull String pathUrl);

        public INetworkManagerBuilder fromJsonObject();

        public INetworkManagerBuilder fromJsonArray();

        public NetworkManager fromString();

        public NetworkManager mappingInto(@NonNull Class classTarget);

        public NetworkManager mappingInto(@NonNull TypeToken typeToken);
    }
}

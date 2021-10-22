package com.kamged.kmovies.services;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class OMDbService {
    private static OMDbService instance = null;
    private static final String APIKEY = ""; // APIKEY is needed, see README.md
    private String _url;
    private RequestQueue _queue;
    private static Context _context;

    private OMDbService(Context context)
    {
        _context = context;
        _queue = Volley.newRequestQueue(context.getApplicationContext());
        _url = "https://www.omdbapi.com/";
        MovieService.getInstance();
    }

    public static synchronized OMDbService getInstance(Context context) {
        if (instance == null) {
            instance = new OMDbService(context);
        }

        return instance;
    }

    public static synchronized OMDbService getInstance() {
        if (instance == null) {
            throw new IllegalStateException(OMDbService.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }

        return instance;
    }

    private void CreateJsonObjectReq(int method, String url, final I_ReqListener<JSONObject> listener)
    {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        Log.d("[OMDbService]", response.toString());

                        try {

                                listener.getResult(response);

                        } catch(Exception e) {
                            throw  new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("[OMDBService]", error.getMessage());
                    }
                });

        _queue.add(jsonObjectRequest);
    }


    public void MakeOMDBReq(String title, String year, final I_ReqListener<JSONObject> listener)
    {
        CreateJsonObjectReq(Request.Method.GET, BuildRESTUrl(title, year, APIKEY), listener);
    }

    private String BuildRESTUrl(String par1, String par2, String par3)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(_url);
        sb.append("?t=");
        sb.append(par1);
        sb.append("&y=");
        sb.append(par2);
        sb.append("&apikey=");
        sb.append(par3);

        return  sb.toString();
    }




    private RequestQueue getRequestQueue() {
        if (_queue == null){
            _queue = Volley.newRequestQueue(_context.getApplicationContext());
        }
        return _queue;
    }

}

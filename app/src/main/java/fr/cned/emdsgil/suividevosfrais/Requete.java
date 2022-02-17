package fr.cned.emdsgil.suividevosfrais;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Requete {
    Context context;
    public Requete(Context context) {
        this.context = context;
    }

    public interface loginI {
        void reponse(JSONObject object);
    }
    public void login(String login, String password, loginI loginI) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, "http://10.0.2.2:3000/auth/login",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loginI.reponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                Log.i("erreur", "ERREUR LORS DE L'ENVOIE DE LA REQUETE");
            }
        });

        queue.add(stringRequest);
    }
}

package fr.cned.emdsgil.suividevosfrais;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Requete {

    Context context;
    String baseUri = "http://10.0.2.2:3000";

    public Requete(Context context) {
        this.context = context;
    }

    /**
     * Vérifie si un utilisateur peut se login
     * @param login Identifiant du compte
     * @param password Mot de passe du compte
     * @return Vrai/Faux
     */
    public boolean login(String login, String password) throws JSONException {
        String loginUri = this.baseUri + "/auth/login";
        final Boolean[] isLogin = {false};

        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject obj = new JSONObject();

        obj.put("email",login);
        obj.put("password", password);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, loginUri, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            isLogin[0] = response.getBoolean("autorisation");
                        } catch (JSONException e) {
                            isLogin[0] = false;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                isLogin[0] = true;
            }
        });
        queue.add(stringRequest);

        return isLogin[0];
    }
}

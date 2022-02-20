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
    public void login(String login, String password, loginI loginI) throws JSONException {
        String loginUri = this.baseUri + "/auth/login";
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject obj = new JSONObject();

        obj.put("email",login);
        obj.put("password", password);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, loginUri, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                        public void onResponse(JSONObject response) {
                        try {
                            loginI.reponse(response.getBoolean("autorisation"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });
        queue.add(stringRequest);
    }
    public interface loginI {
        void reponse(boolean bool);
    }
}

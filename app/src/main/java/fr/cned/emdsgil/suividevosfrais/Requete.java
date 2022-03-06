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

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;

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
    public void login(String login, String password, requestReponseLogin loginI) throws JSONException {
        String loginUri = this.baseUri + "/auth/login";
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject obj = new JSONObject();

        obj.put("email",login);
        obj.put("password", password);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, loginUri, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                        public void onResponse(JSONObject response) {


                            CookieManager cookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
                            CookieStore cookieStore = cookieManager.getCookieStore();
                            CookieHandler.setDefault(cookieManager);

                        HttpCookie cookie;
                        try {
                            cookie = new HttpCookie("token", response.getString("AUTH_TOKEN"));
                            cookieStore.add(new URI(baseUri), cookie);

                        } catch (JSONException | URISyntaxException e) {
                            e.printStackTrace();
                        }

                        try {
                            loginI.reponseLogin(response.getBoolean("autorisation"));

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

    public void getFrais(String moisAnne, String typeFrais, requestReponseData requestReponse)  {
        String fraisUrl = this.baseUri + "/fichefrais/"+typeFrais+"/"+moisAnne;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, fraisUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            requestReponse.data(response);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });
        queue.add(stringRequest);

    }

    public interface requestReponseLogin {
        void reponseLogin(boolean bool);
    }
    public interface requestReponseData {
        void data(JSONObject object);
    }
}

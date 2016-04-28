package com.swe.zonein.zonein.Activities;import android.content.Intent;import android.os.Bundle;import android.support.design.widget.FloatingActionButton;import android.support.v7.app.AppCompatActivity;import android.util.Log;import android.view.View;import android.widget.Button;import android.widget.EditText;import android.widget.Toast;import com.android.volley.Request;import com.android.volley.Response;import com.android.volley.VolleyError;import com.android.volley.toolbox.StringRequest;import com.swe.zonein.zonein.Controllers.MainController;import com.swe.zonein.zonein.Controllers.VolleyController;import com.swe.zonein.zonein.Models.Place;import com.swe.zonein.zonein.Models.User;import com.swe.zonein.zonein.R;import org.json.JSONArray;import org.json.JSONException;import org.json.JSONObject;import java.util.ArrayList;import java.util.HashMap;public class SignInActivity extends AppCompatActivity {    public static ArrayList<Integer> followersID = new ArrayList<>();    private static String TAG = "LoginActivity";    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_sign_in);        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);        fab.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                final EditText nameET = (EditText) findViewById(R.id.nameET);                final EditText passET = (EditText) findViewById(R.id.passET);                Log.e(TAG, "INLOGIN");                final String urlfollowers = VolleyController.baseURL + "getFollowers";                final StringRequest requestfollowers = new StringRequest(Request.Method.POST, urlfollowers, new Response.Listener<String>() {                    @Override                    public void onResponse(String response) {                        try {                            JSONObject jsnObject = new JSONObject(response);                            JSONArray jsonArray = jsnObject.getJSONArray("followersList");                            Log.i(TAG, jsnObject.toString());                            if (jsonArray != null) {                                for (int i = 0; i < jsonArray.length(); i++) {                                    try {                                        User tempUser = new User(jsonArray.getJSONObject(i));                                        MainController.user.follow(tempUser.getID());                                    } catch (JSONException e) {                                        e.printStackTrace();                                    }                                }                            }                        } catch (JSONException e) {                            e.printStackTrace();                        }                    }                }, new Response.ErrorListener() {                    @Override                    public void onErrorResponse(VolleyError error) {                        System.out.println("ERROR!" + error.getMessage());                    }                }) {                    @Override                    protected HashMap<String, String> getParams() {                        HashMap<String, String> params = new HashMap<String, String>();                        params.put("userID", MainController.user.getID() + "");                        Log.e(TAG, params.toString());                        return params;                    }                };                final String urlfolloweing = VolleyController.baseURL + "getFollowedBy";                final StringRequest requestfolloweing = new StringRequest(Request.Method.POST, urlfolloweing, new Response.Listener<String>() {                    @Override                    public void onResponse(String response) {                        try {                            JSONObject jsnObject = new JSONObject(response);                            JSONArray jsonArray = jsnObject.getJSONArray("followedByUser");                            Log.i(TAG, jsnObject.toString());                            if (jsonArray != null) {                                for (int i = 0; i < jsonArray.length(); i++) {                                    try {                                        User tempUser = new User(jsonArray.getJSONObject(i));                                        MainController.user.Addfollower(tempUser.getID());                                    } catch (JSONException e) {                                        e.printStackTrace();                                    }                                }                            }                        } catch (JSONException e) {                            e.printStackTrace();                        }                    }                }, new Response.ErrorListener() {                    @Override                    public void onErrorResponse(VolleyError error) {                        System.out.println("ERROR!" + error.getMessage());                    }                }) {                    @Override                    protected HashMap<String, String> getParams() {                        HashMap<String, String> params = new HashMap<String, String>();                        params.put("userID", MainController.user.getID() + "");                        Log.e(TAG, params.toString());                        return params;                    }                };                final String urlSavedPlaces = VolleyController.baseURL + "getsavedplaces";                final StringRequest requestSavedPlace = new StringRequest(Request.Method.POST, urlSavedPlaces, new Response.Listener<String>() {                    @Override                    public void onResponse(String response) {                        try {                            JSONObject jsnObject = new JSONObject(response);                            JSONArray jsonArray = jsnObject.getJSONArray("SavedPlaces");                            Log.i(TAG, jsnObject.toString());                            if (jsonArray != null) {                                for (int i = 0; i < jsonArray.length(); i++) {                                    try {                                        Place tempPlace = new Place(jsonArray.getJSONObject(i));                                        MainController.user.SavePlace(tempPlace.getID());                                    } catch (JSONException e) {                                        e.printStackTrace();                                    }                                }                            } else {                            }                        } catch (Exception e) {                            e.printStackTrace();                            e.getMessage();                            System.out.println("ERROR Exception!");                        }                    }                }, new Response.ErrorListener() {                    @Override                    public void onErrorResponse(VolleyError error) {                        System.out.println("ERROR!" + error.getMessage());                    }                }) {                    @Override                    protected HashMap<String, String> getParams() {                        HashMap<String, String> params = new HashMap<String, String>();                        params.put("userID", MainController.user.getID() + "");                        Log.e(TAG, params.toString());                        return params;                    }                };                final String url = VolleyController.baseURL + "login";                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {                    @Override                    public void onResponse(String response) {                        try{                        JSONObject obj = new JSONObject(response);                            if(obj!=null){                                MainController.user = new User(obj);                                Log.e(TAG, "USER NAME before " + MainController.user.getName());                                VolleyController.getInstance().addToRequestQueue(requestfollowers, TAG);                                VolleyController.getInstance().addToRequestQueue(requestfolloweing, TAG);                                VolleyController.getInstance().addToRequestQueue(requestSavedPlace, TAG);                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);                                Log.e("LOGIN ACTIVITY", MainController.user.getName());                                Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_LONG).show();                                startActivity(intent);                                finish();                            } else {                                Toast.makeText(getApplicationContext(), "Wrong Email or Password!", Toast.LENGTH_LONG).show();                            }                        }catch (JSONException e){                            e.printStackTrace();                        }                    }                }, new Response.ErrorListener() {                    @Override                    public void onErrorResponse(VolleyError error) {                        System.out.println("ERROR!");                    }                }){                    @Override                    protected HashMap<String, String> getParams()                    {                        HashMap<String, String> params = new HashMap<String, String>();                        params.put("email", nameET.getText().toString());                        params.put("pass", passET.getText().toString());;                        return params;                    }                };                VolleyController.getInstance().addToRequestQueue(request, TAG);            }        });        Button singup = (Button) findViewById(R.id.signUpButton);        singup.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);                Log.e("LOGIN ACTIVITY", "New user ");                Toast.makeText(getApplicationContext(), "Please Create an account ", Toast.LENGTH_LONG).show();                startActivity(intent);                finish();            }        });        Button forgotPass = (Button) findViewById(R.id.forgotPasswordbutton);        forgotPass.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {            }        });    }}
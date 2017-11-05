package com.senac.franciscommarcos.navigationviewteste;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by franc on 05/11/2017.
 */

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "customerSession";
    private static final String SESSION_ID = "sessionID";
    private static final String SESSION_EMAIL = "sessionEmail";
    private static final String SESSION_NAME = "sessionName";
    private static final String SESSION_CPF = "sessionCPF";
    private static final String SESSION_CELL_PHONE = "sessionCellPhone";
    private static final String SESSION_RESIDENCIAL_PHONE = "sessionResidencialPhone";
    private static final String SESSION_NEWSLETTER = "sessionNewsletter";
    private static final String SESSION_IS_LOGGED = "sessionLogged";

    private static SharedPrefManager mInstance;
    private static Context mContext;

    private SharedPrefManager(Context context){
        mContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance == null){
            mInstance =  new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void customerLogin(Customer customer){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(SESSION_ID, customer.getId());
        editor.putString(SESSION_EMAIL, customer.getEmail());
        editor.putString(SESSION_NAME, customer.getName());
        editor.putString(SESSION_CPF, customer.getCpf());
        editor.putString(SESSION_CELL_PHONE, customer.getCell_phone());
        editor.putString(SESSION_RESIDENCIAL_PHONE, customer.getResidencial_phone());
        editor.putString(SESSION_NEWSLETTER, customer.getSend_newsletter());
        editor.putBoolean(SESSION_IS_LOGGED, true);
        editor.apply();
    }

    public boolean isLogged(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SESSION_NAME, null) != null;
    }

    public Customer getCustomer(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Customer(
                sharedPreferences.getLong(SESSION_ID, -1),
                sharedPreferences.getString(SESSION_EMAIL, null),
                sharedPreferences.getString(SESSION_NAME, null),
                sharedPreferences.getString(SESSION_CPF, null),
                sharedPreferences.getString(SESSION_CELL_PHONE, null),
                sharedPreferences.getString(SESSION_RESIDENCIAL_PHONE, null),
                sharedPreferences.getString(SESSION_NEWSLETTER, null)
        );
    }

}

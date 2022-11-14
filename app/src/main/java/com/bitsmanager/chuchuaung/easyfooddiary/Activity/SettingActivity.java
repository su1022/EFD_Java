package com.bitsmanager.chuchuaung.easyfooddiary.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bitsmanager.chuchuaung.easyfooddiary.BuildConfig;
import com.bitsmanager.chuchuaung.easyfooddiary.R;
import com.bitsmanager.chuchuaung.easyfooddiary.Utility.UtilitySetting;

import java.util.Locale;

/**
 * Created by KELVIN on 6/4/2019.
 */

public class SettingActivity extends AppCompatPreferenceActivity {

    private Preference userprofile,history,logout, contactus, appVersion;
    private ListPreference lpChangeLanguage;
    private String lang = "0";
    int REQ_USER_PROFILE,REQ_USER_Contact,REQ_USER_HISTORY;
    int REQ_USER_LOGIN;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId ();

        if (id == android.R.id.home) {
            finish ();
        }
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        logout = findPreference("logout");
        contactus = findPreference("contactus");
        userprofile=findPreference("UserProfile");
        history=findPreference("History");
        appVersion = findPreference("appVersion");

        lpChangeLanguage = (ListPreference) findPreference("changeLanguagePref");

        appVersion.setSummary(BuildConfig.VERSION_NAME);

        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = sp.edit();

        if (UtilitySetting.LANG.equals("0")) {
            lpChangeLanguage.setSummary(getString(R.string.lang_eng));
            lpChangeLanguage.setValueIndex(0);
        } else if (UtilitySetting.LANG.equals("1")) {
            lpChangeLanguage.setSummary(getString(R.string.lang_myan));
            lpChangeLanguage.setValueIndex(1);
        } else if (UtilitySetting.LANG.equals("2")) {
            lpChangeLanguage.setSummary(getString(R.string.lang_uni));
            lpChangeLanguage.setValueIndex(2);
        }

        appVersion.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(SettingActivity.this, "App Version " + BuildConfig.VERSION_NAME, Toast.LENGTH_SHORT).show();
                return true;
            }
        });



        lpChangeLanguage.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object value) {
                if (preference == lpChangeLanguage) {
                    int temp = Integer.valueOf((String) value);
                    Locale locale = null;
                    Configuration config = getResources().getConfiguration();
                    if (temp == 0) {
                        locale = new Locale("en_US");
                        config.setLocale(locale);
                    } else if (temp == 1) {
                        locale = new Locale("my");
                        config.setLocale(locale);
                    } else if (temp == 2) {
                        locale = new Locale("my", "MM");
                        config.setLocale(locale);
                    }
                    editor.putString("lang", value.toString());
                    editor.commit();
                    UtilitySetting.LANG = sp.getString("lang", "0");
                    getResources().updateConfiguration(config, null);
                    recreate();
                }
                return true;
            }
        });



        userprofile.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(SettingActivity.this, UserProfileActivity.class);
                startActivityForResult(intent, REQ_USER_PROFILE);
                return true;
            }
        });



        history.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(SettingActivity.this, HistroyActivity.class);
                startActivityForResult(intent, REQ_USER_HISTORY);
                return true;
            }
        });


        contactus.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(SettingActivity.this, ContactUsActivity.class);
                startActivityForResult(intent, REQ_USER_Contact);
                return true;
            }
        });

        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivityForResult(intent, REQ_USER_LOGIN);
                return true;
            }
        });
    }
}

package np.com.naxa.factsnepal;

import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.text.ParseException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function6;
import io.reactivex.subscribers.DisposableSubscriber;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.feed.list.FeedListActivity;
import np.com.naxa.factsnepal.utils.ActivityUtil;

public class UpdateProfileActivity extends BaseActivity implements View.OnClickListener {

    private EditText etWard;
    private AutoCompleteTextView etDistrict;
    private Button btnNext;
    private EditText etDob;
    private Spinner spinnerGender;


    private DisposableSubscriber<Boolean> disposableObserver = null;
    private Flowable<CharSequence> genderChangeObservable;
    private Flowable<CharSequence> gpsChangeObservable;
    private AutoCompleteTextView etProvience;
    private EditText etMunicipality;
    private EditText etStreet;
    private MaterialButton btnSkip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_information);
        initView();
        setupToolbar();
        setupDistrictAutoComplete();
        setupProvinceAutoComplete();
    }

    private void initView() {
        etDob = findViewById(R.id.et_dob);
        spinnerGender = findViewById(R.id.spinner_gender);
        etWard = findViewById(R.id.et_ward);
        etDistrict = findViewById(R.id.et_district);
        etProvience = findViewById(R.id.et_province);
        etMunicipality = findViewById(R.id.et_municipality);
        etStreet = findViewById(R.id.et_street);
        btnSkip = findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(this);
        btnNext = (MaterialButton) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);

    }

    private void setupDistrictAutoComplete() {

        String[] arr = getResources().getStringArray(R.array.districts);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, arr);

        etDistrict.setThreshold(1);
        etDistrict.setAdapter(adapter);
    }

    private void setupProvinceAutoComplete() {

        String[] arr = getResources().getStringArray(R.array.provinces);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, arr);

        etProvience.setThreshold(1);
        etProvience.setAdapter(adapter);
    }

    private void makeUIObservable() {
        Flowable<CharSequence> dobChangeObservable = RxTextView.textChanges(etDob).skip(1).toFlowable(BackpressureStrategy.LATEST);
        Flowable<CharSequence> wardChangeObservable = RxTextView.textChanges(etWard).skip(1).toFlowable(BackpressureStrategy.LATEST);
        Flowable<CharSequence> districtChangeObservable = RxTextView.textChanges(etDistrict).skip(1).toFlowable(BackpressureStrategy.LATEST);
        Flowable<CharSequence> provinceChangeObservable = RxTextView.textChanges(etProvience).skip(1).toFlowable(BackpressureStrategy.LATEST);
        Flowable<CharSequence> municipalityChangeObservable = RxTextView.textChanges(etMunicipality).skip(1).toFlowable(BackpressureStrategy.LATEST);
        Flowable<CharSequence> streetChangeObservable = RxTextView.textChanges(etStreet).skip(1).toFlowable(BackpressureStrategy.LATEST);

        disposableObserver = Flowable.combineLatest(dobChangeObservable, wardChangeObservable, districtChangeObservable, provinceChangeObservable, municipalityChangeObservable, streetChangeObservable,
                this::isValidForm)
                .subscribeWith(new DisposableSubscriber<Boolean>() {
                    @Override
                    public void onNext(Boolean isValid) {
                        updateButton(isValid);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private boolean isValidForm(CharSequence dob, CharSequence ward, CharSequence district, CharSequence province, CharSequence municipality, CharSequence street) {

        boolean validDob, validWard, validDistrict, validProvince, validMunicipality, validStreet;
        validDob = !dob.toString().isEmpty();
        validWard = !ward.toString().isEmpty();
        validDistrict = !district.toString().isEmpty();
        validProvince = !province.toString().isEmpty();
        validMunicipality = !municipality.toString().isEmpty();
        validStreet = !street.toString().isEmpty();

        if (!validDob) {
            showError(etDob, getString(R.string.msg_invalid_input, "year"));
        }


        if (!validWard) {
            showError(etWard, getString(R.string.msg_invalid_input, "ward"));
        }


        if (!validDistrict) {
            showError(etDistrict, getString(R.string.msg_invalid_input, "district"));
        }


        if (!validProvince) {
            showError(etProvience, getString(R.string.msg_invalid_input, "province"));
        }


        if (!validMunicipality) {
            showError(etMunicipality, getString(R.string.msg_invalid_input, "municipality"));
        }

        if (!validStreet) {
            showError(etStreet, getString(R.string.msg_invalid_input, "street"));
        }

        return validDob && validWard && validDistrict && validProvince && validMunicipality && validStreet;

    }


    private void showError(View view, String errorMessage) {
        if (view instanceof EditText) {
            ((EditText) view).setError(errorMessage);
        } else if (view instanceof AutoCompleteTextView) {
            ((AutoCompleteTextView) view).setError(errorMessage);

        }
    }


    private void showError(AutoCompleteTextView editText, String error_message) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposableObserver.dispose();
    }

    public void updateButton(boolean valid) {
        if (valid)
            btnNext.setEnabled(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                break;
            case R.id.btn_skip:
                ActivityUtil.openActivity(FeedListActivity.class, this, null, false);
                break;
            default:
                break;
        }
    }
}

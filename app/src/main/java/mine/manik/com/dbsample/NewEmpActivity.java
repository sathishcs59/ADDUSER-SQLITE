package mine.manik.com.dbsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import mine.manik.com.dbsample.DB.DBHandler;
import mine.manik.com.dbsample.Pojo.Emp;

public class NewEmpActivity extends AppCompatActivity {

    EditText etUsername,etMobile;
    Spinner spnDep;
    Button btnAdd;

    DBHandler mDBHandler;
    boolean isUpdate=false;
    Emp mEmpInfo;

    ArrayList<String> mDepList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_emp);

        Intent intent=getIntent();
        if (intent != null) {
            if(intent.hasExtra("empInfo")) {
                isUpdate=true;
                mEmpInfo= intent.getParcelableExtra("empInfo");
            }
        }

        initViews();

    }

    void initViews() {

        mDBHandler=new DBHandler(this);
        mDepList=new ArrayList<>();
        mDepList.add("Select");
        mDepList.add("One");
        mDepList.add("Two");
        mDepList.add("Three");

        etUsername= (EditText) findViewById(R.id.emp_name);
        etMobile= (EditText) findViewById(R.id.emp_mobile);
        spnDep= (Spinner) findViewById(R.id.spinner_department);
        btnAdd= (Button) findViewById(R.id.emp_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    if (isUpdate) {
                        mDBHandler.updateProfile(etUsername.getText().toString().trim(),mDepList.get(spnDep.getSelectedItemPosition()),etMobile.getText().toString().trim());
                        Toast.makeText(NewEmpActivity.this, "Details Updated Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent();
                        setResult(RESULT_OK,intent);
                        NewEmpActivity.this.finish();
                    }else {
                        mDBHandler.insertNewData(etUsername.getText().toString().trim(),
                                etMobile.getText().toString().trim(),mDepList.get(spnDep.getSelectedItemPosition()));
                        Toast.makeText(NewEmpActivity.this, "Details Saved Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent();
                        setResult(RESULT_OK,intent);
                        NewEmpActivity.this.finish();
                    }
                }
            }
        });

        if(isUpdate)
            etMobile.setEnabled(false);
        else
            etMobile.setEnabled(true);

        ArrayAdapter<String> counrtryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mDepList);
        counrtryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDep.setAdapter(counrtryAdapter);

        if (mEmpInfo != null) {
            etUsername.setText(mEmpInfo.getName());
            etMobile.setText(mEmpInfo.getMobile());
            spnDep.setSelection(mDepList.indexOf(mEmpInfo.getDep()));
        }

    }

    boolean validate() {

        boolean failFlag=false;

        if (etUsername.getText().toString().trim().isEmpty()) {
            etUsername.setError("Enter userName");
            failFlag=true;
            return failFlag;
        }

        if (etMobile.getText().toString().trim().isEmpty()) {
            etMobile.setError("Enter Mobile");
            failFlag=true;
            return failFlag;
        }

        if (spnDep.getSelectedItemPosition()==0) {
            Toast.makeText(this,"Select Profession",Toast.LENGTH_SHORT).show();
            failFlag=true;
            return failFlag;
        }

        if (etMobile.getText().toString().trim().length() < 10) {
            etMobile.setError("Invaild Mobile Number");
            failFlag=true;
            return failFlag;
        }

        if(!isUpdate) {
            if (mDBHandler.getNumberExists(etMobile.getText().toString().trim())) {
                Toast.makeText(this, "Mobile Number Exists", Toast.LENGTH_SHORT).show();
                failFlag = true;
                return failFlag;
            }
        }

        return failFlag;

    }
}
